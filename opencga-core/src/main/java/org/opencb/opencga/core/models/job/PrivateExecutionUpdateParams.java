package org.opencb.opencga.core.models.job;

import org.opencb.opencga.core.models.file.File;

public class PrivateExecutionUpdateParams extends ExecutionUpdateParams {

    private ExecutionInternal internal;

    public PrivateExecutionUpdateParams() {
    }

    public PrivateExecutionUpdateParams(Pipeline pipeline, Boolean visited, File outDir, ExecutionInternal internal) {
        super(pipeline, visited, outDir);
        this.internal = internal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrivateExecutionUpdateParams{");
        sb.append("pipeline=").append(pipeline);
        sb.append(", visited=").append(visited);
        sb.append(", outDir=").append(outDir);
        sb.append(", internal=").append(internal);
        sb.append('}');
        return sb.toString();
    }

    public ExecutionInternal getInternal() {
        return internal;
    }

    public PrivateExecutionUpdateParams setInternal(ExecutionInternal internal) {
        this.internal = internal;
        return this;
    }

    @Override
    public PrivateExecutionUpdateParams setPipeline(Pipeline pipeline) {
        super.setPipeline(pipeline);
        return this;
    }

    @Override
    public PrivateExecutionUpdateParams setVisited(Boolean visited) {
        super.setVisited(visited);
        return this;
    }

    @Override
    public PrivateExecutionUpdateParams setOutDir(File outDir) {
        super.setOutDir(outDir);
        return this;
    }
}