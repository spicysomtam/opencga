package org.opencb.opencga.app.cli.main.options;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import java.util.List;

import org.opencb.opencga.app.cli.main.parent.ParentUsersCommandOptions;

import static org.opencb.opencga.app.cli.GeneralCliOptions.*;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-02-25
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Users command line.
 *    OpenCGA version: 2.2.0-rc2-SNAPSHOT
 *    PATH: /{apiVersion}/users
 */
@Parameters(commandNames = {"users"}, commandDescription = "Users commands")
public class UsersCommandOptions extends ParentUsersCommandOptions {


        public CreateCommandOptions createCommandOptions;
        public LoginCommandOptions loginCommandOptions;
        public PasswordCommandOptions passwordCommandOptions;
        public InfoCommandOptions infoCommandOptions;
        public ConfigsCommandOptions configsCommandOptions;
        public UpdateConfigsCommandOptions updateConfigsCommandOptions;
        public FiltersCommandOptions filtersCommandOptions;
        public ResetPasswordCommandOptions resetPasswordCommandOptions;
        public ProjectsCommandOptions projectsCommandOptions;
        public UpdateCommandOptions updateCommandOptions;


    public UsersCommandOptions(CommonCommandOptions commonCommandOptions, JCommander jCommander) {
    
        super(commonCommandOptions,jCommander);
        this.createCommandOptions = new CreateCommandOptions();
        this.loginCommandOptions = new LoginCommandOptions();
        this.passwordCommandOptions = new PasswordCommandOptions();
        this.infoCommandOptions = new InfoCommandOptions();
        this.configsCommandOptions = new ConfigsCommandOptions();
        this.updateConfigsCommandOptions = new UpdateConfigsCommandOptions();
        this.filtersCommandOptions = new FiltersCommandOptions();
        this.resetPasswordCommandOptions = new ResetPasswordCommandOptions();
        this.projectsCommandOptions = new ProjectsCommandOptions();
        this.updateCommandOptions = new UpdateCommandOptions();
    
    }
    
    @Parameters(commandNames = {"create"}, commandDescription ="Create a new user")
    public class CreateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = true, arity = 1)
        public String id;
    
        @Parameter(names = {"--name", "-n"}, description = "The body web service name parameter", required = false, arity = 1)
        public String name;
    
        @Parameter(names = {"--email"}, description = "The body web service email parameter", required = false, arity = 1)
        public String email;
    
        @Parameter(names = {"--password"}, description = "The body web service password parameter", required = false, arity = 1)
        public String password;
    
        @Parameter(names = {"--organization"}, description = "The body web service organization parameter", required = false, arity = 1)
        public String organization;
    
  }
    @Parameters(commandNames = {"password"}, commandDescription ="Change the password of a user")
    public class PasswordCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--user", "-u"}, description = "The body web service user parameter", required = false, arity = 1)
        public String user;
    
        @Parameter(names = {"--password"}, description = "The body web service password parameter", required = false, arity = 1)
        public String password;
    
        @Parameter(names = {"--new-password"}, description = "The body web service newPassword parameter", required = false, arity = 1)
        public String newPassword;
    
        @Parameter(names = {"--reset"}, description = "The body web service reset parameter", required = false, arity = 1)
        public String reset;
    
  }
    @Parameters(commandNames = {"info"}, commandDescription ="Return the user information including its projects and studies")
    public class InfoCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--users"}, description = "Comma separated list of user IDs", required = true, arity = 1)
        public String users; 
    
  }
    @Parameters(commandNames = {"configs"}, commandDescription ="Fetch a user configuration")
    public class ConfigsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--user", "-u"}, description = "User ID", required = true, arity = 1)
        public String user; 
    
        @Parameter(names = {"--name", "-n"}, description = "Unique name (typically the name of the application).", required = false, arity = 1)
        public String name; 
    
  }
    @Parameters(commandNames = {"configs-update"}, commandDescription ="Add or remove a custom user configuration")
    public class UpdateConfigsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--user", "-u"}, description = "User ID", required = true, arity = 1)
        public String user; 
    
        @Parameter(names = {"--action"}, description = "Action to be performed: ADD or REMOVE a group", required = false, arity = 1)
        public String action; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String id;
    
  }
    @Parameters(commandNames = {"filters"}, commandDescription ="Fetch user filters")
    public class FiltersCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--user", "-u"}, description = "User ID", required = true, arity = 1)
        public String user; 
    
        @Parameter(names = {"--id"}, description = "Filter id. If provided, it will only fetch the specified filter", required = false, arity = 1)
        public String id; 
    
  }
    @Parameters(commandNames = {"password-reset"}, commandDescription ="Reset password")
    public class ResetPasswordCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--user", "-u"}, description = "User ID", required = true, arity = 1)
        public String user; 
    
  }
    @Parameters(commandNames = {"projects"}, commandDescription ="Retrieve the projects of the user")
    public class ProjectsCommandOptions {
    
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
    
        @Parameter(names = {"--user", "-u"}, description = "User ID", required = true, arity = 1)
        public String user; 
    
  }
    @Parameters(commandNames = {"update"}, commandDescription ="Update some user attributes")
    public class UpdateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--user", "-u"}, description = "User ID", required = true, arity = 1)
        public String user; 
    
        @Parameter(names = {"--include-result"}, description = "Flag indicating to include the created or updated document result in the response", required = false, arity = 1)
        public Boolean includeResult; 
    
        @Parameter(names = {"--name", "-n"}, description = "The body web service name parameter", required = false, arity = 1)
        public String name;
    
        @Parameter(names = {"--email"}, description = "The body web service email parameter", required = false, arity = 1)
        public String email;
    
        @Parameter(names = {"--organization"}, description = "The body web service organization parameter", required = false, arity = 1)
        public String organization;
    
  }
}