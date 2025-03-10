/*
 * Copyright 2015-2020 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.opencga.analysis.variant.genomePlot;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.opencb.biodata.models.clinical.qc.GenomePlotConfig;
import org.opencb.biodata.models.clinical.qc.GenomePlotTrack;
import org.opencb.biodata.models.variant.StudyEntry;
import org.opencb.biodata.models.variant.Variant;
import org.opencb.biodata.models.variant.avro.BreakendMate;
import org.opencb.biodata.models.variant.avro.FileEntry;
import org.opencb.biodata.models.variant.avro.StructuralVariation;
import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryOptions;
import org.opencb.commons.utils.DockerUtils;
import org.opencb.opencga.analysis.StorageToolExecutor;
import org.opencb.opencga.analysis.variant.manager.VariantStorageManager;
import org.opencb.opencga.core.common.GitRepositoryState;
import org.opencb.opencga.core.common.JacksonUtils;
import org.opencb.opencga.core.common.TimeUtils;
import org.opencb.opencga.core.exceptions.ToolException;
import org.opencb.opencga.core.tools.annotations.ToolExecutor;
import org.opencb.opencga.core.tools.variant.GenomePlotAnalysisExecutor;
import org.opencb.opencga.storage.core.variant.adaptors.VariantQueryParam;
import org.opencb.opencga.storage.core.variant.adaptors.iterators.VariantDBIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.opencb.opencga.analysis.wrappers.executors.DockerWrapperAnalysisExecutor.DOCKER_INPUT_PATH;
import static org.opencb.opencga.analysis.wrappers.executors.DockerWrapperAnalysisExecutor.DOCKER_OUTPUT_PATH;
import static org.opencb.opencga.storage.core.variant.adaptors.VariantQueryParam.STUDY;

@ToolExecutor(id="opencga-local", tool = GenomePlotAnalysis.ID,
        framework = ToolExecutor.Framework.LOCAL, source = ToolExecutor.Source.STORAGE)
public class GenomePlotLocalAnalysisExecutor extends GenomePlotAnalysisExecutor implements StorageToolExecutor {

    public final static String R_DOCKER_IMAGE = "opencb/opencga-ext-tools:"
            + GitRepositoryState.get().getBuildVersion();

    private GenomePlotConfig plotConfig;

    private File snvsFile;
    private File rearrsFile;
    private File indelsFile;
    private File cnvsFile;

    private boolean plotCopynumber = false;
    private boolean plotIndels = false;
    private boolean plotRearrangements = false;

    private ConcurrentHashMap<String, Exception> errors;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() throws ToolException, IOException {

        plotConfig = JacksonUtils.getDefaultObjectMapper().readerFor(GenomePlotConfig.class).readValue(getConfigFile());

        // Create query
        Query query = new Query();
        if (MapUtils.isNotEmpty(plotConfig.getGeneralQuery())) {
            query.putAll(plotConfig.getGeneralQuery());
        }
        query.put(STUDY.key(), getStudy());

        // Launch a thread per query
        VariantStorageManager storageManager = getVariantStorageManager();

        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        errors = new ConcurrentHashMap<>();
        List<Future<Boolean>> futureList = new ArrayList<>(4);
        futureList.add(threadPool.submit(getNamedThread("SNV", () -> snvQuery(query, storageManager))));
        futureList.add(threadPool.submit(getNamedThread("COPY_NUMBER", () -> copyNumberQuery(query, storageManager))));
        futureList.add(threadPool.submit(getNamedThread("INDEL", () -> indelQuery(query, storageManager))));
        futureList.add(threadPool.submit(getNamedThread("REARRANGEMENT", () -> rearrangementQuery(query,
                storageManager))));

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(2, TimeUnit.MINUTES);
            if (!threadPool.isTerminated()) {
                for (Future<Boolean> future : futureList) {
                    future.cancel(true);
                }
            }
        } catch (InterruptedException e) {
            throw new ToolException("Error launching threads when executing the Circos analysis", e);
        }


        if (MapUtils.isEmpty(errors)) {
            // Execute R script
            // circos.R ./snvs.tsv ./indels.tsv ./cnvs.tsv ./rearrs.tsv SampleId
            String rScriptPath = getExecutorParams().getString("opencgaHome") + "/analysis/R/" + getToolId();
            List<AbstractMap.SimpleEntry<String, String>> inputBindings = new ArrayList<>();
            inputBindings.add(new AbstractMap.SimpleEntry<>(rScriptPath, DOCKER_INPUT_PATH));
            AbstractMap.SimpleEntry<String, String> outputBinding = new AbstractMap.SimpleEntry<>(getOutDir()
                    .toAbsolutePath().toString(), DOCKER_OUTPUT_PATH);
            String scriptParams = "R CMD Rscript --vanilla " + DOCKER_INPUT_PATH + "/circos.R"
                    + (plotCopynumber ? "" : " --no_copynumber")
                    + (plotIndels ? "" : " --no_indels")
                    + (plotRearrangements ? "" : " --no_rearrangements")
                    + " --out_path " + DOCKER_OUTPUT_PATH
                    + " " + DOCKER_OUTPUT_PATH + "/" + snvsFile.getName()
                    + " " + DOCKER_OUTPUT_PATH + "/" + indelsFile.getName()
                    + " " + DOCKER_OUTPUT_PATH + "/" + cnvsFile.getName()
                    + " " + DOCKER_OUTPUT_PATH + "/" + rearrsFile.getName()
                    + " " + plotConfig.getTitle();

            StopWatch stopWatch = StopWatch.createStarted();
            String cmdline = DockerUtils.run(R_DOCKER_IMAGE, inputBindings, outputBinding, scriptParams, null);
            logger.info("Docker command line: " + cmdline);
            logger.info("Execution time: " + TimeUtils.durationToString(stopWatch));
        } else {
            StringBuilder msg = new StringBuilder();
            for (Map.Entry<String, Exception> error : errors.entrySet()) {
                msg.append("Error on track ").append(error.getKey()).append(": ").append(error.getValue()
                        .getMessage()).append(". ");
            }
            ToolException exception = new ToolException(msg.toString());
            if (errors.size() == 1) {
                exception.initCause(errors.values().iterator().next());
            } else {
                errors.values().forEach(exception::addSuppressed);
            }
            throw exception;
        }
    }

    /**
     * Create file with SNV variants.
     *
     * @param query General query
     * @param storageManager    Variant storage manager
     * @return True or false depending on successs
     */
    private boolean snvQuery(Query query, VariantStorageManager storageManager) {
        PrintWriter pw = null;
        PrintWriter pwOut = null;
        try {
            snvsFile = getOutDir().resolve("snvs.tsv").toFile();
            pw = new PrintWriter(snvsFile);
            pw.println("Chromosome\tchromStart\tchromEnd\tref\talt\tlogDistPrev");

            pwOut = new PrintWriter(getOutDir().resolve("snvs.discarded").toFile());

            int threshold;

            switch (plotConfig.getDensity()) {
                case "HIGH":
                    threshold = Integer.MAX_VALUE;
                    break;
                case "MEDIUM":
                    threshold = 250000;
                    break;
                case "LOW":
                default:
                    threshold = 100000;
                    break;
            }

            GenomePlotTrack track = plotConfig.getTrack("SNV");
            Query snvQuery = new Query(query);
            if (track != null && MapUtils.isNotEmpty(track.getQuery())) {
                snvQuery.putAll(track.getQuery());
            }
            snvQuery.put("type", "SNV");

            QueryOptions queryOptions = new QueryOptions()
                    .append(QueryOptions.INCLUDE, "id")
                    .append(QueryOptions.SORT, true);

            logger.info("SNV track, query: " + snvQuery.toJson());
            logger.info("SNV track, query options: " + queryOptions.toJson());

            VariantDBIterator iterator = storageManager.iterator(snvQuery, queryOptions, getToken());

            int prevStart = 0;
            String currentChrom = "";
            while (iterator.hasNext()) {
                Variant v = iterator.next();
                if (v.getStart() > v.getEnd()) {
                    // Sanity check
                    pwOut.println(v.toString() + "\tStart  (" + v.getStart() + ") is bigger than end (" + v.getEnd()
                            + ")");
                } else {
                    if (!v.getChromosome().equals(currentChrom)) {
                        prevStart = 0;
                        currentChrom = v.getChromosome();
                    }
                    int dist = v.getStart() - prevStart;
                    if (dist < threshold) {
                        pw.println("chr" + v.getChromosome() + "\t" + v.getStart() + "\t" + v.getEnd() + "\t"
                                + v.getReference() + "\t"
                                + v.getAlternate() + "\t" + Math.log10(dist));
                    }
                    prevStart = v.getStart();
                }
            }
        } catch(Exception e) {
            errors.put("SNV", e);
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (pwOut != null) {
                pwOut.close();
            }
        }
        return true;
    }

    /**
     * Create file with copy-number variants.
     *
     * @param query General query
     * @param storageManager    Variant storage manager
     * @return True or false depending on successs
     */
    private boolean copyNumberQuery(Query query, VariantStorageManager storageManager) {
        PrintWriter pw = null;
        PrintWriter pwOut = null;
        try {
            cnvsFile = getOutDir().resolve("cnvs.tsv").toFile();

            pw = new PrintWriter(cnvsFile);
            pw.println("Chromosome\tchromStart\tchromEnd\tlabel\tmajorCopyNumber\tminorCopyNumber");

            pwOut = new PrintWriter(getOutDir().resolve("cnvs.discarded").toFile());


            GenomePlotTrack track = plotConfig.getTrack("COPY-NUMBER");
            if (track != null) {
                plotCopynumber = true;

                Query copyNumberQuery = new Query(query);
                copyNumberQuery.putAll(track.getQuery());
                copyNumberQuery.put("type", "CNV");

                QueryOptions queryOptions = new QueryOptions(QueryOptions.INCLUDE, "id,studies");

                logger.info("COPY-NUMBER track, query: " + copyNumberQuery.toJson());
                logger.info("COPY-NUMBER track, query options: " + queryOptions.toJson());

                VariantDBIterator iterator = storageManager.iterator(copyNumberQuery, queryOptions, getToken());

                while (iterator.hasNext()) {
                    Variant v = iterator.next();

                    if (CollectionUtils.isEmpty(v.getStudies())) {
                        pwOut.println(v.toString() + "\tStudies is empty");
                    } else {
                        StudyEntry studyEntry = v.getStudies().get(0);
                        String strTcn = studyEntry.getSampleData(query.getString(VariantQueryParam.SAMPLE.key()),
                                "TCN");
                        String strMcn = studyEntry.getSampleData(query.getString(VariantQueryParam.SAMPLE.key()),
                                "MCN");

                        if (StringUtils.isEmpty(strTcn)) {
                            pwOut.println(v.toString() + "\tTCN format field is empty");
                        } else {
                            if (StringUtils.isEmpty(strMcn)) {
                                pwOut.println(v.toString() + "\tMCN format field is empty");
                            } else {
                                try {
                                    int tcn = Integer.parseInt(strTcn);
                                    int mcn = Integer.parseInt(strMcn);
                                    pw.println("chr" + v.getChromosome() + "\t" + v.getStart() + "\t" + v.getEnd()
                                            + "\tNONE\t" + (tcn - mcn) + "\t" + mcn);
                                } catch (NumberFormatException e){
                                    pwOut.println(v.toString() + "\tError parsing TCN/MCN: " + e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            errors.put("COPY-NUMBER", e);
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (pwOut != null) {
                pwOut.close();
            }
        }
        return true;
    }

    /**
     * Create file with INDEL variants.
     *
     * @param query General query
     * @param storageManager    Variant storage manager
     * @return True or false depending on successs
     */
    private boolean indelQuery(Query query, VariantStorageManager storageManager) {
        PrintWriter pw = null;
        PrintWriter pwOut = null;
        try {
            indelsFile = getOutDir().resolve("indels.tsv").toFile();
            pw = new PrintWriter(indelsFile);
            pw.println("Chromosome\tchromStart\tchromEnd\ttype\tclassification");

            pwOut = new PrintWriter(getOutDir().resolve("indels.discarded").toFile());

            GenomePlotTrack track = plotConfig.getTrack("INDEL");
            if (track != null) {
                plotIndels = true;

                Query indelQuery = new Query(query);
                indelQuery.putAll(track.getQuery());
                indelQuery.put("type", "INSERTION,DELETION,INDEL");

                QueryOptions queryOptions = new QueryOptions(QueryOptions.INCLUDE, "id");

                logger.info("INDEL track, query: " + indelQuery.toJson());
                logger.info("INDEL track, query options: " + queryOptions.toJson());

                VariantDBIterator iterator = storageManager.iterator(indelQuery, queryOptions, getToken());

                while (iterator.hasNext()) {
                    Variant v = iterator.next();
                    if (StringUtils.isEmpty(v.getReference())) {
                        pw.println("chr" + v.getChromosome() + "\t" + v.getStart() + "\t" + v.getEnd() + "\tI\tNone");
                    } else if (StringUtils.isEmpty(v.getAlternate())){
                        pw.println("chr" + v.getChromosome() + "\t" + v.getStart() + "\t" + v.getEnd() + "\tD\tNone");
                    } else {
                        pw.println("chr" + v.getChromosome() + "\t" + v.getStart() + "\t" + v.getEnd() + "\tDI\tNone");
                    }
                }
            }
        } catch(Exception e){
            errors.put("INDEL", e);
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (pwOut != null) {
                pwOut.close();
            }
        }
        return true;
    }

    /**
     * Create file with rearrangement variants.
     *
     * @param query General query
     * @param storageManager    Variant storage manager
     * @return True or false depending on successs
     */
    private boolean rearrangementQuery(Query query, VariantStorageManager storageManager) {
        PrintWriter pw = null;
        PrintWriter pwOut = null;
        try {
            rearrsFile = getOutDir().resolve("rearrs.tsv").toFile();
            pw = new PrintWriter(rearrsFile);
            pw.println("Chromosome\tchromStart\tchromEnd\tChromosome.1\tchromStart.1\tchromEnd.1\ttype");

            pwOut = new PrintWriter(getOutDir().resolve("rearrs.discarded").toFile());

            GenomePlotTrack track = plotConfig.getTrack("REARRANGEMENT");
            if (track != null) {
                plotRearrangements = true;

                Query rearrangementQuery = new Query(query);
                rearrangementQuery.putAll(track.getQuery());
                rearrangementQuery.put("type", "DELETION,TRANSLOCATION,INVERSION,DUPLICATION,TANDEM_DUPLICATION,"
                        + "BREAKEND");

                QueryOptions queryOptions = new QueryOptions(QueryOptions.INCLUDE, "id,sv,studies");

                logger.info("REARRANGEMENT track, query: " + rearrangementQuery.toJson());
                logger.info("REARRANGEMENT track, query options: " + queryOptions.toJson());

                VariantDBIterator iterator = storageManager.iterator(rearrangementQuery, queryOptions, getToken());

                while (iterator.hasNext()) {
                    Variant v = iterator.next();

                    String variantType = v.getType() != null ? v.getType().name() : "";
                    if (CollectionUtils.isNotEmpty(v.getStudies()) && CollectionUtils.isNotEmpty(v.getStudies()
                            .get(0).getFiles())) {
                        for (FileEntry file : v.getStudies().get(0).getFiles()) {
                            if (file.getData() != null && file.getData().containsKey("EXT_SVTYPE")) {
                                variantType = file.getData().get("EXT_SVTYPE");
                                break;
                            }
                        }
                    }

                    // Check structural variation
                    StructuralVariation sv = v.getSv();
                    if (sv != null) {
                        if (sv.getBreakend() != null) {
                            if (sv.getBreakend().getMate() != null) {
                                BreakendMate mate = sv.getBreakend().getMate();
                                pw.println("chr" + v.getChromosome() + "\t" + v.getStart() + "\t" + v.getEnd() + "\tchr"
                                        + mate.getChromosome() + "\t" + mate.getPosition() + "\t" + mate.getPosition()
                                        + "\t" + variantType);
                            } else {
                                pwOut.println(v.toString() + "\tBreakend mate is empty (variant type: " + variantType
                                        + ")");
                            }
                        } else {
                            pwOut.println(v.toString() + "\tBreakend is empty (variant type: " + variantType + ")");
                        }
                    } else {
                        pwOut.println(v.toString() + "\tSV is empty (variant type: " + variantType + ")");
                    }
                }
            }
        } catch (Exception e) {
            errors.put("REARRANGEMENT", e);
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (pwOut != null) {
                pwOut.close();
            }
        }
        return true;
    }

    private <T> Callable<T> getNamedThread(String name, Callable<T> c) {
        String parentThreadName = Thread.currentThread().getName();
        return () -> {
            Thread.currentThread().setName(parentThreadName + "-" + name);
            return c.call();
        };
    }
}
