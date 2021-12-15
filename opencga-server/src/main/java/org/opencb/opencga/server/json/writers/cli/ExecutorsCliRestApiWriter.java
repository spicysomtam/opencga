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

package org.opencb.opencga.server.json.writers.cli;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.opencb.opencga.server.json.beans.Category;
import org.opencb.opencga.server.json.beans.Endpoint;
import org.opencb.opencga.server.json.beans.Parameter;
import org.opencb.opencga.server.json.beans.RestApi;
import org.opencb.opencga.server.json.config.CategoryConfig;
import org.opencb.opencga.server.json.config.CommandLineConfiguration;
import org.opencb.opencga.server.json.utils.CommandLineUtils;
import org.opencb.opencga.server.json.writers.ParentClientRestApiWriter;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExecutorsCliRestApiWriter extends ParentClientRestApiWriter {

    public ExecutorsCliRestApiWriter(RestApi restApi, CommandLineConfiguration config) {
        super(restApi, config);
    }

    @Override
    protected String getClassImports(String key) {
        StringBuilder sb = new StringBuilder();
        Category category = availableCategories.get(key);
        CategoryConfig categoryConfig = availableCategoryConfigs.get(key);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("package ").append(config.getOptions().getExecutorsPackage()).append(";\n\n");
        sb.append("import org.opencb.opencga.app.cli.session.CliSessionManager;\n");
        sb.append("import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;\n");
        sb.append("import org.opencb.opencga.app.cli.main.*;\n");
        sb.append("import org.opencb.opencga.core.response.RestResponse;\n");
        sb.append("import org.opencb.opencga.client.exceptions.ClientException;\n");
        sb.append("import org.opencb.commons.datastore.core.ObjectMap;\n\n");
        sb.append("import org.opencb.opencga.app.cli.main.CommandLineUtils;\n\n");
        sb.append("import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;\n\n");

        sb.append("import java.util.List;\n\n");

        sb.append("import " + config.getOptions().getOptionsPackage() + "." + getAsClassName(category.getName()) + "CommandOptions;\n\n");
        if (categoryConfig.isExecutorExtended()) {
            sb.append("import org.opencb.opencga.app.cli.main.parent."
                    + getExtendedClass(getAsClassName(category.getName()), categoryConfig) + ";\n\n");
        }
        Set<String> imports = new HashSet<>();
        for (Endpoint endpoint : category.getEndpoints()) {
            if (isValidImport(endpoint.getResponseClass())) {
                imports.add(endpoint.getResponseClass().replaceAll("\\$", "\\."));
            }
            for (Parameter parameter : endpoint.getParameters()) {
                if (isValidImport(parameter.getTypeClass())) {
                    imports.add(parameter.getTypeClass().replaceAll("\\$", "\\."));
                }
                if (parameter.getData() != null && !parameter.getData().isEmpty()) {
                    for (Parameter bodyParam : parameter.getData()) {
                        if (bodyParam.isComplex() && !bodyParam.isCollection()) {
                            if (bodyParam.getTypeClass() != null && !bodyParam.getTypeClass().contains("$")) {
                                imports.add(bodyParam.getTypeClass().replaceAll("\\$", "\\."));
                            }
                        }
                    }
                }
            }
        }

        for (String string : imports) {
            sb.append("import ").append(string).append("\n");
        }

        sb.append("\n");
        sb.append("\n");
        sb.append("/*\n");
        sb.append("* WARNING: AUTOGENERATED CODE\n");
        sb.append("*\n");
        sb.append("* This code was generated by a tool.\n");
        sb.append("* Autogenerated on: ").append(sdf.format(new Date())).append("\n");
        sb.append("*\n");
        sb.append("* Manual changes to this file may cause unexpected behavior in your application.\n");
        sb.append("* Manual changes to this file will be overwritten if the code is regenerated.\n");
        sb.append("*/\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("/**\n");
        sb.append(" * This class contains methods for the " + category.getName() + " command line.\n");
        sb.append(" *    OpenCGA version: " + restApi.getVersion() + "\n");
        sb.append(" *    PATH: " + category.getPath() + "\n");
        sb.append(" */\n");
        return sb.toString();
    }

    public boolean isValidImport(String string) {
        if (string.endsWith(";")) {
            string = string.substring(0, string.length() - 1);
        }
        if (CommandLineUtils.isPrimitiveType(string)) {
            return false;
        }
        String[] excluded = new String[]{"java.lang.Object", "java.lang."};

        if (Arrays.asList(excluded).contains(string)) {
            return false;
        }

        return true;
    }

    @Override
    protected String getClassHeader(String key) {
        StringBuilder sb = new StringBuilder();
        Category category = availableCategories.get(key);
        CategoryConfig config = availableCategoryConfigs.get(key);
        sb.append("public class " + getAsClassName(category.getName()) + "CommandExecutor extends "
                + getExtendedClass(getAsClassName(category.getName()), config) + " {\n\n");
        sb.append("    private " + getAsClassName(category.getName()) + "CommandOptions "
                + getAsVariableName(getAsCamelCase(category.getName())) + "CommandOptions;\n\n");
        sb.append("    public " + getAsClassName(category.getName()) + "CommandExecutor(" + getAsClassName(category.getName())
                + "CommandOptions " + getAsVariableName(getAsCamelCase(category.getName()))
                + "CommandOptions) throws CatalogAuthenticationException {\n");
        if (config.isExecutorExtended()) {
            sb.append("        super(" + getAsVariableName(getAsCamelCase(category.getName())) + "CommandOptions.commonCommandOptions, "
                    + "getParsedSubCommand(" + getAsVariableName(getAsCamelCase(category.getName()))
                    + "CommandOptions.jCommander).startsWith(\"log\")," + getAsVariableName(getAsCamelCase(category.getName())) +
                    "CommandOptions);\n");
        } else {
            sb.append("        super(" + getAsVariableName(getAsCamelCase(category.getName())) + "CommandOptions.commonCommandOptions);\n");
        }
        sb.append("        this." + getAsVariableName(getAsCamelCase(category.getName())) + "CommandOptions = "
                + getAsVariableName(getAsCamelCase(category.getName())) + "CommandOptions;\n");
        sb.append("    }\n\n");

        return sb.toString();
    }

    private String methodExecute(Category category, CategoryConfig config) {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public void execute() throws Exception {\n\n");
        sb.append("        logger.debug(\"Executing " + category.getName() + " command line\");\n\n");

        sb.append("        String subCommandString = getParsedSubCommand(" + getAsVariableName(getAsCamelCase(category.getName()))
                + "CommandOptions.jCommander);\n\n");
        sb.append("        RestResponse queryResponse = null;\n\n");
        sb.append("        switch (subCommandString) {\n");
        for (Endpoint endpoint : category.getEndpoints()) {
            //If it is post, it must have parameters in the body,
            // therefore it must be different from post or have some primitive value in the body so that we can generate the method
            String commandName = getMethodName(category, endpoint).replaceAll("_", "-");
            if ((!"POST".equals(endpoint.getMethod()) || endpoint.hasPrimitiveBodyParams(config, commandName)) && endpoint.hasParameters()) {
                if (config.isAvailableCommand(commandName)) {
                    sb.append("            case \"" + commandName + "\":\n");
                    sb.append("                queryResponse = " + getAsCamelCase(commandName) + "();\n");
                    sb.append("                break;\n");
                }
            }
        }
        if (CollectionUtils.isNotEmpty(config.getAddedMethods())) {
            for (String methodName : config.getAddedMethods()) {
                sb.append("            case \"" + methodName + "\":\n");
                sb.append("                queryResponse = " + getAsCamelCase(methodName) + "();\n");
                sb.append("                break;\n");
            }
        }
        sb.append("            default:\n");
        sb.append("                logger.error(\"Subcommand not valid\");\n");
        sb.append("                break;\n");
        sb.append("        }\n\n");
        sb.append("        createOutput(queryResponse);\n\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String getExtendedClass(String name, CategoryConfig config) {
        String res = "OpencgaCommandExecutor";
        if (config.isExecutorExtended()) {
            res = "Parent" + name + "CommandExecutor";
        }
        return res;
    }

    @Override
    protected String getClassMethods(String key) {
        StringBuilder sb = new StringBuilder();
        Category category = availableCategories.get(key);
        CategoryConfig config = availableCategoryConfigs.get(key);
        sb.append(methodExecute(category, config));
        for (Endpoint endpoint : category.getEndpoints()) {
            //If it is post, it must have parameters in the body,
            // therefore it must be different from post or have some primitive value in the body so that we can generate the method
            String commandName = getMethodName(category, endpoint).replaceAll("_", "-");
            if ((!"POST".equals(endpoint.getMethod()) || endpoint.hasPrimitiveBodyParams(config, commandName)) && endpoint.hasParameters()) {
                if (config.isAvailableCommand(commandName)) {
                    sb.append("\n");
                    sb.append("    " + (config.isExecutorExtendedCommand(commandName) ? "protected" :
                            "private") + " RestResponse<" + getValidResponseNames(endpoint.getResponse()) + "> "
                            + getAsCamelCase(commandName) + "() throws Exception {\n\n");
                    sb.append("        logger.debug(\"Executing " + getAsCamelCase(commandName) + " in "
                            + category.getName() + " command line\");\n\n");
                    if (config.isExecutorExtendedCommand(commandName)) {
                        sb.append("        return super." + getAsCamelCase(commandName) + "();\n\n");
                    } else {
                        sb.append("        " + getAsClassName(category.getName()) + "CommandOptions." + getAsClassName(getAsCamelCase(commandName))
                                + "CommandOptions commandOptions = " + getAsVariableName(getAsCamelCase(category.getName())) +
                                "CommandOptions."
                                + getAsCamelCase(commandName) + "CommandOptions;\n");
                        sb.append(getQueryParams(endpoint, config, getAsCamelCase(commandName)));
                        sb.append(getBodyParams(endpoint, config, commandName));
                        sb.append(getReturn(category, endpoint, config, commandName));
                    }
                    sb.append("    }\n");
                }
            }
        }

        return sb.toString();
    }

    private String getReturn(Category category, Endpoint endpoint, CategoryConfig config, String commandName) {
        String res =
                "        return openCGAClient.get" + getAsClassName(config.getKey()) + "Client()."
                        + getAsCamelCase(commandName) + "(";
        res += endpoint.getPathParams();
        res += endpoint.getMandatoryQueryParams(config, commandName);
        if (endpoint.hasPrimitiveBodyParams(config, commandName)) {
            res += getAsVariableName(endpoint.getBodyParamsObject()) + ", ";
        }
        if (endpoint.hasQueryParams()) {
            res += "queryParams";
        }
        if (res.trim().endsWith(",")) {
            res = res.substring(0, res.lastIndexOf(","));
        }
        res += ");\n";
        return res;
    }

    private String getBodyParams(Endpoint endpoint, CategoryConfig config, String commandName) {
        StringBuilder sb = new StringBuilder();
        if (endpoint.hasPrimitiveBodyParams(config, commandName)) {

            for (Parameter parameter : endpoint.getParameters()) {
                if (parameter.getData() != null && !parameter.getData().isEmpty()) {
                    sb.append(generateBeans(parameter.getData()));
                }
            }
            String bodyParamsObject = endpoint.getBodyParamsObject();
            sb.append("\n        " + bodyParamsObject + " " + getAsVariableName(bodyParamsObject) + " = (" + bodyParamsObject + ") new " + bodyParamsObject + "()");
            Set<String> variables = new HashSet<>();
            for (Parameter parameter : endpoint.getParameters()) {
                if (parameter.getData() != null && !parameter.getData().isEmpty()) {
                    for (Parameter bodyParam : parameter.getData()) {
                        if (config.isAvailableSubCommand(bodyParam.getName(), commandName)) {
                            if (!bodyParam.isComplex() && !bodyParam.isInnerParam()) {
                                //sometimes the name of the parameter has the prefix "body" so as not to coincide with another parameter
                                // with the same name, but the setter does not have this prefix, so it must be removed
                                sb.append("\n            .set" + getAsClassName(bodyParam.getName().replaceAll("body_", "")) +
                                        "(commandOptions."
                                        + normaliceNames(getAsCamelCase(bodyParam.getName())) + ")");
                            } else {
                                if (bodyParam.isStringList()) {
                                    sb.append("\n            .set" + getAsClassName(bodyParam.getName().replaceAll("body_", "")) +
                                            "(CommandLineUtils.getListValues(commandOptions."
                                            + normaliceNames(getAsCamelCase(bodyParam.getName())) + "))");
                                } else {
                                    if (bodyParam.isInnerParam()) {
                                        if (!variables.contains(parameter.getParentParamName())) {
                                            sb.append("\n            .set" + getAsClassName(bodyParam.getParentParamName()) + "("
                                                    + CommandLineUtils.getAsVariableName(CommandLineUtils.getClassName(bodyParam.getGenericType()))
                                                    + ")");
                                            variables.add(parameter.getParentParamName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sb.append(";\n");
        }
        return sb.toString();
    }

    private String generateBeans(List<Parameter> parameters) {
        StringBuilder sb = new StringBuilder();

        Set<String> beans = new HashSet<>();
        for (Parameter parameter : parameters) {
            if (parameter.isInnerParam() && !parameter.isCollection()) {
                beans.add(parameter.getGenericType());
            }
        }

        for (String nameBean : beans) {
            sb.append("\n        " + CommandLineUtils.getClassName(nameBean) + " " + CommandLineUtils.getAsVariableName(CommandLineUtils.getClassName(nameBean)) +
                    "= new " + CommandLineUtils.getClassName(nameBean) + "();\n");
            for (Parameter parameter : parameters) {
                if (parameter.getGenericType() != null && parameter.getGenericType().equals(nameBean)) {
                    sb.append("        invokeSetter(" + CommandLineUtils.getAsVariableName(CommandLineUtils.getClassName(nameBean)) +
                            ", \"" + parameter.getName() +
                            "\", commandOptions." + normaliceNames(getAsCamelCase(parameter.getParentParamName() + " " + parameter.getName())) + ")" +
                            ";\n");
                }
            }
        }
        return sb.toString();
    }

    private String getQueryParams(Endpoint endpoint, CategoryConfig config, String commandName) {
        String res = "\n        ObjectMap queryParams = new ObjectMap();\n";
        boolean enc = false;
        boolean studyPresent = false;
        for (Parameter parameter : endpoint.getParameters()) {
            if (config.isAvailableSubCommand(parameter.getName(), commandName)) {
                if ("query".equals(parameter.getParam()) && !parameter.isRequired() && parameter.isAvailableType()) {
                    enc = true;
                    if (normaliceNames(parameter.getName()).equals("study")) {
                        studyPresent = true;
                    }
                    if (StringUtils.isNotEmpty(parameter.getType()) && "string".equalsIgnoreCase(parameter.getType())) {
                        res += "        queryParams.putIfNotEmpty(\"" + normaliceNames(parameter.getName()) + "\", commandOptions."
                                + normaliceNames(parameter.getName()) + ");\n";
                    } else {
                        if (parameter.isStringList()) {
                            res += "        queryParams.putIfNotNull(\"" + normaliceNames(parameter.getName()) + "\", CommandLineUtils" +
                                    ".getListValues(commandOptions."
                                    + normaliceNames(parameter.getName()) + "));\n";
                        } else {
                            res += "        queryParams.putIfNotNull(\"" + normaliceNames(parameter.getName()) + "\", commandOptions."
                                    + normaliceNames(parameter.getName()) + ");\n";
                        }
                    }
                }
            }
        }
        if (enc) {
            if (studyPresent) {
                res += "        if(queryParams.get(\"study\")==null && CliSessionManager.isShell()){\n";
                res += "                queryParams.putIfNotEmpty(\"study\", CliSessionManager.getCurrentStudy());\n";
                res += "        }\n";
            }
            return res + "\n";
        }
        return "";
    }

    private String getValidResponseNames(String responseClass) {
        Map<String, String> validResponse = new HashMap<>();
        validResponse.put("map", "ObjectMap");
        validResponse.put("Map", "ObjectMap");
        //  validResponse.put("Object", "ObjectMap");
        validResponse.put("", "ObjectMap");

        responseClass = responseClass.replace('$', '.');
        if (validResponse.containsKey(responseClass)) {
            return validResponse.get(responseClass);
        }
        return responseClass;
    }

    private String normaliceNames(String name) {
        name = getAsCamelCase(name, "\\.");
        if (invalidNames.containsKey(name)) {
            name = invalidNames.get(name);
        }
        return name;
    }

    @Override
    protected String getClassFileName(String key) {
        Category category = availableCategories.get(key);
        return config.getOptions().getExecutorsOutputDir() + "/" + getAsClassName(category.getName()) + "CommandExecutor.java";
        //  return "/tmp" + "/" + getAsClassName(category.getName()) + "CommandExecutor.java";
    }
}