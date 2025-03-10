package org.opencb.opencga.app.cli.main.options;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import java.util.List;

import org.opencb.opencga.app.cli.main.parent.ParentJobsCommandOptions;

import static org.opencb.opencga.app.cli.GeneralCliOptions.*;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-05-16
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Jobs command line.
 *    OpenCGA version: 2.3.0-SNAPSHOT
 *    PATH: /{apiVersion}/jobs
 */
@Parameters(commandNames = {"jobs"}, commandDescription = "Jobs commands")
public class JobsCommandOptions extends ParentJobsCommandOptions {


        public UpdateAclCommandOptions updateAclCommandOptions;
        public AggregationStatsCommandOptions aggregationStatsCommandOptions;
        public CreateCommandOptions createCommandOptions;
        public DistinctCommandOptions distinctCommandOptions;
        public RetryCommandOptions retryCommandOptions;
        public SearchCommandOptions searchCommandOptions;
        public TopCommandOptions topCommandOptions;
        public AclCommandOptions aclCommandOptions;
        public DeleteCommandOptions deleteCommandOptions;
        public InfoCommandOptions infoCommandOptions;
        public UpdateCommandOptions updateCommandOptions;
        public HeadLogCommandOptions headLogCommandOptions;
        public TailLogCommandOptions tailLogCommandOptions;


    public JobsCommandOptions(CommonCommandOptions commonCommandOptions, JCommander jCommander) {
    
        super(commonCommandOptions,jCommander);
        this.updateAclCommandOptions = new UpdateAclCommandOptions();
        this.aggregationStatsCommandOptions = new AggregationStatsCommandOptions();
        this.createCommandOptions = new CreateCommandOptions();
        this.distinctCommandOptions = new DistinctCommandOptions();
        this.retryCommandOptions = new RetryCommandOptions();
        this.searchCommandOptions = new SearchCommandOptions();
        this.topCommandOptions = new TopCommandOptions();
        this.aclCommandOptions = new AclCommandOptions();
        this.deleteCommandOptions = new DeleteCommandOptions();
        this.infoCommandOptions = new InfoCommandOptions();
        this.updateCommandOptions = new UpdateCommandOptions();
        this.headLogCommandOptions = new HeadLogCommandOptions();
        this.tailLogCommandOptions = new TailLogCommandOptions();
    
    }
    
    @Parameters(commandNames = {"acl-update"}, commandDescription ="Update the set of permissions granted for the member")
    public class UpdateAclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--json-file"}, description = "File with the body data in JSON format. Note, that using this parameter will ignore all the other parameters.", required = false, arity = 1)
        public String jsonFile;
    
        @Parameter(names = {"--json-data-model"}, description = "Show example of file structure for body data.", help = true, arity = 0)
        public Boolean jsonDataModel = false;
    
        @Parameter(names = {"--members"}, description = "Comma separated list of user or group ids", required = true, arity = 1)
        public String members; 
    
        @Parameter(names = {"--action"}, description = "Action to be performed [ADD, SET, REMOVE or RESET].", required = true, arity = 1)
        public String action; 
    
        @Parameter(names = {"--job"}, description = "The body web service job parameter", required = false, arity = 1)
        public String job;
    
        @Parameter(names = {"--permissions"}, description = "The body web service permissions parameter", required = true, arity = 1)
        public String permissions;
    
  }
    @Parameters(commandNames = {"aggregationstats"}, commandDescription ="Fetch catalog job stats")
    public class AggregationStatsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--tool-id"}, description = "Tool id", required = false, arity = 1)
        public String toolId; 
    
        @Parameter(names = {"--tool-scope"}, description = "Tool scope", required = false, arity = 1)
        public String toolScope; 
    
        @Parameter(names = {"--tool-type"}, description = "Tool type", required = false, arity = 1)
        public String toolType; 
    
        @Parameter(names = {"--tool-resource"}, description = "Tool resource", required = false, arity = 1)
        public String toolResource; 
    
        @Parameter(names = {"--user-id"}, description = "User id", required = false, arity = 1)
        public String userId; 
    
        @Parameter(names = {"--priority"}, description = "Priority", required = false, arity = 1)
        public String priority; 
    
        @Parameter(names = {"--tags"}, description = "Tags", required = false, arity = 1)
        public String tags; 
    
        @Parameter(names = {"--executor-id"}, description = "Executor id", required = false, arity = 1)
        public String executorId; 
    
        @Parameter(names = {"--executor-framework"}, description = "Executor framework", required = false, arity = 1)
        public String executorFramework; 
    
        @Parameter(names = {"--creation-year"}, description = "Creation year", required = false, arity = 1)
        public String creationYear; 
    
        @Parameter(names = {"--creation-month"}, description = "Creation month (JANUARY, FEBRUARY...)", required = false, arity = 1)
        public String creationMonth; 
    
        @Parameter(names = {"--creation-day"}, description = "Creation day", required = false, arity = 1)
        public String creationDay; 
    
        @Parameter(names = {"--creation-day-of-week"}, description = "Creation day of week (MONDAY, TUESDAY...)", required = false, arity = 1)
        public String creationDayOfWeek; 
    
        @Parameter(names = {"--status"}, description = "Status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--release"}, description = "Release", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--default"}, description = "Calculate default stats", required = false, arity = 1)
        public Boolean default_values; 
    
        @Parameter(names = {"--field"}, description = "List of fields separated by semicolons, e.g.: studies;type. For nested fields use >>, e.g.: studies>>biotype;type;numSamples[0..10]:1", required = false, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"create"}, commandDescription ="Register an executed job with POST method")
    public class CreateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--json-file"}, description = "File with the body data in JSON format. Note, that using this parameter will ignore all the other parameters.", required = false, arity = 1)
        public String jsonFile;
    
        @Parameter(names = {"--json-data-model"}, description = "Show example of file structure for body data.", help = true, arity = 0)
        public Boolean jsonDataModel = false;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = true, arity = 1)
        public String id;
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description;
    
        @Parameter(names = {"--tool-id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String toolId;
    
        @Parameter(names = {"--tool-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String toolDescription;
    
        @Parameter(names = {"--priority"}, description = "Enum param allowed values: URGENT, HIGH, MEDIUM, LOW, UNKNOWN", required = false, arity = 1)
        public String priority;
    
        @Parameter(names = {"--command-line"}, description = "The body web service commandLine parameter", required = false, arity = 1)
        public String commandLine;
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate;
    
        @Parameter(names = {"--modification-date", "--md"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate;
    
        @Parameter(names = {"--tags"}, description = "The body web service tags parameter", required = false, arity = 1)
        public String tags;
    
        @Parameter(names = {"--result-id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String resultId;
    
  }
    @Parameters(commandNames = {"distinct"}, commandDescription ="Job distinct method")
    public class DistinctCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--other-studies"}, description = "Flag indicating the entries being queried can belong to any related study, not just the primary one.", required = false, arity = 1)
        public Boolean otherStudies; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list of job IDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--uuid"}, description = "Comma separated list of job UUIDs up to a maximum of 100", required = false, arity = 1)
        public String uuid; 
    
        @Parameter(names = {"--tool-id"}, description = "Tool ID executed by the job", required = false, arity = 1)
        public String toolId; 
    
        @Parameter(names = {"--tool-type"}, description = "Tool type executed by the job [OPERATION, ANALYSIS]", required = false, arity = 1)
        public String toolType; 
    
        @Parameter(names = {"--user-id"}, description = "User that created the job", required = false, arity = 1)
        public String userId; 
    
        @Parameter(names = {"--priority"}, description = "Priority of the job", required = false, arity = 1)
        public String priority; 
    
        @Parameter(names = {"--status"}, description = "Filter by status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--internal-status"}, description = "Filter by internal status", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--visited"}, description = "Visited status of job", required = false, arity = 1)
        public Boolean visited; 
    
        @Parameter(names = {"--tags"}, description = "Job tags", required = false, arity = 1)
        public String tags; 
    
        @Parameter(names = {"--input"}, description = "Comma separated list of file IDs used as input.", required = false, arity = 1)
        public String input; 
    
        @Parameter(names = {"--output"}, description = "Comma separated list of file IDs used as output.", required = false, arity = 1)
        public String output; 
    
        @Parameter(names = {"--acl"}, description = "Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example: acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS permissions. Only study owners or administrators can query by this field. ", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted entries", required = false, arity = 1)
        public Boolean deleted; 
    
        @Parameter(names = {"--field"}, description = "Field for which to obtain the distinct values", required = true, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"retry"}, commandDescription ="Relaunch a failed job")
    public class RetryCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--json-file"}, description = "File with the body data in JSON format. Note, that using this parameter will ignore all the other parameters.", required = false, arity = 1)
        public String jsonFile;
    
        @Parameter(names = {"--json-data-model"}, description = "Show example of file structure for body data.", help = true, arity = 0)
        public Boolean jsonDataModel = false;
    
        @Parameter(names = {"--job-id"}, description = "Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.", required = false, arity = 1)
        public String jobId; 
    
        @Parameter(names = {"--job-description"}, description = "Job description", required = false, arity = 1)
        public String jobDescription; 
    
        @Parameter(names = {"--job-depends-on"}, description = "Comma separated list of existing job IDs the job will depend on.", required = false, arity = 1)
        public String jobDependsOn; 
    
        @Parameter(names = {"--job-tags"}, description = "Job tags", required = false, arity = 1)
        public String jobTags; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--job"}, description = "The body web service job parameter", required = false, arity = 1)
        public String job;
    
        @Parameter(names = {"--force"}, description = "The body web service force parameter", required = false, arity = 1)
        public Boolean force;
    
  }
    @Parameters(commandNames = {"search"}, commandDescription ="Job search method")
    public class SearchCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--limit"}, description = "Number of results to be returned", required = false, arity = 1)
        public Integer limit; 
    
        @Parameter(names = {"--skip"}, description = "Number of results to skip", required = false, arity = 1)
        public Integer skip; 
    
        @Parameter(names = {"--count"}, description = "Get the total number of results matching the query. Deactivated by default.", required = false, arity = 1)
        public Boolean count; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--other-studies"}, description = "Flag indicating the entries being queried can belong to any related study, not just the primary one.", required = false, arity = 1)
        public Boolean otherStudies; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list of job IDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--uuid"}, description = "Comma separated list of job UUIDs up to a maximum of 100", required = false, arity = 1)
        public String uuid; 
    
        @Parameter(names = {"--tool-id"}, description = "Tool ID executed by the job", required = false, arity = 1)
        public String toolId; 
    
        @Parameter(names = {"--tool-type"}, description = "Tool type executed by the job [OPERATION, ANALYSIS]", required = false, arity = 1)
        public String toolType; 
    
        @Parameter(names = {"--user-id"}, description = "User that created the job", required = false, arity = 1)
        public String userId; 
    
        @Parameter(names = {"--priority"}, description = "Priority of the job", required = false, arity = 1)
        public String priority; 
    
        @Parameter(names = {"--status"}, description = "Filter by status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--internal-status"}, description = "Filter by internal status", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--visited"}, description = "Visited status of job", required = false, arity = 1)
        public Boolean visited; 
    
        @Parameter(names = {"--tags"}, description = "Job tags", required = false, arity = 1)
        public String tags; 
    
        @Parameter(names = {"--input"}, description = "Comma separated list of file IDs used as input.", required = false, arity = 1)
        public String input; 
    
        @Parameter(names = {"--output"}, description = "Comma separated list of file IDs used as output.", required = false, arity = 1)
        public String output; 
    
        @Parameter(names = {"--acl"}, description = "Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example: acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS permissions. Only study owners or administrators can query by this field. ", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted entries", required = false, arity = 1)
        public Boolean deleted; 
    
  }
    @Parameters(commandNames = {"acl"}, commandDescription ="Return the acl of the job. If member is provided, it will only return the acl for the member.")
    public class AclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--jobs"}, description = "Comma separated list of job IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String jobs; 
    
        @Parameter(names = {"--member"}, description = "User or group id", required = false, arity = 1)
        public String member; 
    
        @Parameter(names = {"--silent"}, description = "Boolean to retrieve all possible entries that are queried for, false to raise an exception whenever one of the entries looked for cannot be shown for whichever reason", required = false, arity = 1)
        public Boolean silent; 
    
  }
    @Parameters(commandNames = {"delete"}, commandDescription ="Delete existing jobs")
    public class DeleteCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--jobs"}, description = "Comma separated list of job ids", required = true, arity = 1)
        public String jobs; 
    
  }
    @Parameters(commandNames = {"info"}, commandDescription ="Get job information")
    public class InfoCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--jobs"}, description = "Comma separated list of job IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String jobs; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted jobs", required = false, arity = 1)
        public Boolean deleted; 
    
  }
    @Parameters(commandNames = {"update"}, commandDescription ="Update some job attributes")
    public class UpdateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--json-file"}, description = "File with the body data in JSON format. Note, that using this parameter will ignore all the other parameters.", required = false, arity = 1)
        public String jsonFile;
    
        @Parameter(names = {"--json-data-model"}, description = "Show example of file structure for body data.", help = true, arity = 0)
        public Boolean jsonDataModel = false;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--jobs"}, description = "Comma separated list of job IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String jobs; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--include-result"}, description = "Flag indicating to include the created or updated document result in the response", required = false, arity = 1)
        public Boolean includeResult; 
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description;
    
        @Parameter(names = {"--tags"}, description = "The body web service tags parameter", required = false, arity = 1)
        public String tags;
    
        @Parameter(names = {"--visited"}, description = "The body web service visited parameter", required = false, arity = 1)
        public Boolean visited;
    
  }
    @Parameters(commandNames = {"log-head"}, commandDescription ="Show the first lines of a log file (up to a limit)")
    public class HeadLogCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--job"}, description = "Job ID or UUID", required = true, arity = 1)
        public String job; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--offset"}, description = "Starting byte from which the file will be read", required = false, arity = 1)
        public Long offset; 
    
        @Parameter(names = {"--lines"}, description = "Maximum number of lines to be returned up to a maximum of 1000", required = false, arity = 1)
        public Integer lines; 
    
        @Parameter(names = {"--type"}, description = "Log file to be shown (stdout or stderr)", required = false, arity = 1)
        public String type; 
    
  }
    @Parameters(commandNames = {"log-tail"}, commandDescription ="Show the last lines of a log file (up to a limit)")
    public class TailLogCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--job"}, description = "Job ID or UUID", required = true, arity = 1)
        public String job; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--lines"}, description = "Maximum number of lines to be returned up to a maximum of 1000", required = false, arity = 1)
        public Integer lines; 
    
        @Parameter(names = {"--type"}, description = "Log file to be shown (stdout or stderr)", required = false, arity = 1)
        public String type; 
    
  }
}