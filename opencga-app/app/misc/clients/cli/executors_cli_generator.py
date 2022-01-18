#!/usr/bin/env python3
# importing date class from datetime module
import argparse
import os
import re
import sys
# importing date class from datetime module
from datetime import date

currentdir = os.path.dirname(os.path.realpath(__file__))
parentdir = os.path.dirname(currentdir)
print(parentdir)
sys.path.insert(0, parentdir)

import rest_client_generator


class ExecutorCliGenerator(rest_client_generator.RestClientGenerator):

    def __init__(self, output_dir):
        super().__init__(output_dir)

        self.emptyQueryParams = ['login', 'updateConfigs', 'updateFilters', 'genotypesFamily', 'updateGroups', 'updateUsers',
                                 'uploadTemplates', 'updatePermissionRules', 'updateVariableSets', 'updateVariables']
        self.normalized_objects_map = {
            'Map': 'ObjectMap',
            'Object': 'ObjectMap'
        }
        self.java_types = set()
        self.type_imports = {
            'java.util.Map;': 'org.opencb.commons.datastore.core.ObjectMap;'
        }
        self.param_types = {
            'string': 'String',
            'object': 'Object',
            'Object': 'Object',
            'integer': 'int',
            'int': 'int',
            'map': 'ObjectMap',
            'boolean': 'boolean',
            'enum': 'String',
            'long': 'Long',
            'java.lang.String': 'String',
            'java.lang.Boolean': 'boolean',
            'java.lang.Integer': 'int',
            'java.lang.Long': 'int',
            'java.lang.Short': 'int',
            'java.lang.Double': 'int',
            'java.lang.Float': 'int'
        }
        self.output_dir = self.executors_output_dir
        self.imports = []

    def get_imports(self):
        headers = []
        headers.append('/*')
        headers.append('* Copyright 2015-{} OpenCB'.format(date.today().year))
        headers.append('*')
        headers.append('* Licensed under the Apache License, Version 2.0 (the "License");')
        headers.append('* you may not use this file except in compliance with the License.')
        headers.append('* You may obtain a copy of the License at')
        headers.append('*')
        headers.append('*     http://www.apache.org/licenses/LICENSE-2.0')
        headers.append('*')
        headers.append('* Unless required by applicable law or agreed to in writing, software')
        headers.append('* distributed under the License is distributed on an "AS IS" BASIS,')
        headers.append('* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.')
        headers.append('* See the License for the specific language governing permissions and')
        headers.append('* limitations under the License.')
        headers.append('*/')
        headers.append('')
        headers.append('package ' + self.executors_package + ';')
        headers.append('')
        headers.append('import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;')
        headers.append('import org.opencb.opencga.app.cli.main.*;')
        headers.append('import org.opencb.opencga.core.response.RestResponse;')

        imports = set()
        imports.add("org.opencb.opencga.client.exceptions.ClientException;")
        imports.add("org.opencb.commons.datastore.core.ObjectMap;")
        for java_type in self.java_types:
            if java_type.replace(';', "") not in self.ignore_types:
                if '$' in java_type:
                    java_type = java_type.replace('$', '.')
                if java_type in self.type_imports:
                    java_type = self.type_imports[java_type]
                imports.add(java_type)

        imports = remove_redundant_imports(list(imports))
        imports.sort()

        autogenerated_message = ['/*']
        for text in self.get_autogenerated_message():
            if text == '':
                autogenerated_message.append('*')
            else:
                autogenerated_message.append('* ' + text)
        autogenerated_message.append('*/')

        return '\n'.join(headers) + '\n' + '\n'.join(['import ' + i for i in imports]) + '\n\n\n' + '\n'.join(autogenerated_message)

    def get_class_definition(self, category):
        self.java_types = set()
        self.imports = []
        class_attributes = {}
        for endpoint in category["endpoints"]:
            class_attributes[endpoint["path"]] = self.get_method_name(endpoint, category)

        text = []
        text.append('')
        text.append('public class {}CommandExecutor extends {} {{'.format(self.categories[category['name']],
                                                                          self.get_extended_class(self.categories[category['name']])))
        text.append('')
        text.append('    private {}CommandOptions {}CommandOptions;'.format(self.categories[category['name']], self.categories[category[
            'name']].lower()))
        if str(self.categories[category['name']]).lower() == 'user':
            text.append('')
            text.append('    public UserCommandExecutor(UserCommandOptions userCommandOptions) {')
            text.append(
                '        super(userCommandOptions.commonCommandOptions, getParsedSubCommand(userCommandOptions.jCommander).startsWith("log"),')
            text.append('                userCommandOptions);')
            text.append('        this.userCommandOptions = userCommandOptions;')
            text.append('    }')
        else:
            text.append('')
            text.append('    public {}CommandExecutor({}CommandOptions {}CommandOptions) {{'.format(self.categories[category['name']],
                                                                                                    self.categories[category['name']],
                                                                                                    self.categories[
                                                                                                        category['name']].lower()))
            text.append('        super({}CommandOptions.commonCommandOptions);'.format(self.categories[category['name']].lower()))
            text.append('        this.{}CommandOptions = {}CommandOptions;'.format(self.categories[category['name']].lower(),
                                                                                   self.categories[category['name']].lower()))
            text.append('    }')
        text.append('')
        text.append(self.get_method_execute(category))
        text.append('')

        return '\n'.join(text)

    def get_extended_class(self, category):
        res = 'OpencgaCommandExecutor'
        if category in self.extended_classes:
            res = 'Parent' + category + 'CommandExecutor'
        return res

    def get_class_end(self):
        return '}\n'

    def get_method_execute(self, category):
        text = []
        text.append('{}@Override'.format((' ' * 4)))
        text.append('{}public void execute() throws Exception {{'.format((' ' * 4)))
        text.append('{}logger.debug("Executing {} command line");'.format((' ' * 8), category["name"]))
        text.append(' ' * 8)
        text.append('{}String subCommandString = getParsedSubCommand({}CommandOptions.jCommander);'.format((' ' * 8), self.categories[
            category["name"]].lower()))
        text.append('{}RestResponse queryResponse = null;'.format((' ' * 8), category["name"]))
        text.append('{}switch (subCommandString) {{'.format((' ' * 8)))
        for endpoint in category["endpoints"]:
            method_name = self.get_method_name(endpoint, category)
            if self.check_not_ignored_command(category["name"], method_name):
                text.append('{}case "{}":'.format((' ' * 12), self.to_kebab_case(method_name)))
                text.append('{}queryResponse = {}();'.format((' ' * 16), method_name))
                text.append('{}break;'.format((' ' * 16)))

        '''
         String subCommandString = getParsedSubCommand(familyCommandOptions.jCommander);
                RestResponse queryResponse = null;
                switch (subCommandString) {
                
                
               
        '''
        text.append('{}default:'.format((' ' * 12)))
        text.append('{}logger.error("Subcommand not valid");'.format((' ' * 16)))
        text.append('{} break;'.format((' ' * 16)))

        text.append('{}}}'.format((' ' * 8)))
        text.append(' ' * 4)
        text.append('{}createOutput(queryResponse);'.format((' ' * 8)))
        text.append(' ' * 4)
        text.append('    }')
        text.append(' ' * 4)
        return '\n'.join(text)

    def get_method_definition(self, category, endpoint):
        method_name = self.get_method_name(endpoint, category)
        text = []
        if method_name in self.extended_methods:
            text.append('{}protected RestResponse<{}> {}() throws Exception {{'.format((' ' * 4),
                                                                                       self.normalize_object(endpoint['response'],
                                                                                                             self.categories[
                                                                                                                 category[
                                                                                                                     'name']]),
                                                                                       method_name))

            text.append('{}return super.{}();'.format((' ' * 8), method_name))
            text.append('{}}}'.format((' ' * 4)))
            self.java_types.add(endpoint["responseClass"])
        else:
            self.java_types.add(self.options_package + '.{}CommandOptions;'.format(self.categories[self.get_category_name(category)]))

            if self.check_not_ignored_command(category["name"], method_name):

                parameters = self.get_method_parameters(endpoint)
                for parameter in parameters:
                    self.java_types.add(parameter["typeClass"])
                self.java_types.add(endpoint["responseClass"])
                text.append('{}private RestResponse<{}> {}() throws ClientException {{'.format((' ' * 4),
                                                                                               self.normalize_object(endpoint['response'],
                                                                                                                     self.categories[
                                                                                                                         category[
                                                                                                                             'name']]),
                                                                                               method_name))
                text.append(
                    '        logger.debug("Executing {} in {} command line");'.format(method_name, self.categories[category['name']]))
                text.append('')
                text.append('        {}CommandOptions.{}CommandOptions commandOptions = {}CommandOptions.{}CommandOptions;'.format(
                    self.categories[self.get_category_name(category)], self.get_as_class_name(self.get_method_name(endpoint, category)),
                    self.categories[category['name']].lower(),
                    method_name))
                query_params_no_mandatory = self.get_query_params(endpoint, False)
                exists_query_params = False
                if query_params_no_mandatory:
                    exists_query_params = True
                    text.append('')
                    text.append('{}ObjectMap queryParams = new ObjectMap();'.format((' ' * 8)))
                    for parameter in query_params_no_mandatory:
                        if parameter["type"] == "string":
                            text.append(
                                '{}queryParams.putIfNotEmpty("{}", commandOptions.{});'.format((' ' * 8),
                                                                                               self.normalize_names(parameter["name"]),
                                                                                               self.normalize_names(parameter["name"])))
                        else:
                            self.java_types.add(parameter["typeClass"])
                            text.append(
                                '{}queryParams.putIfNotNull("{}", commandOptions.{});'.format((' ' * 8),
                                                                                              self.normalize_names(parameter["name"]),
                                                                                              self.normalize_names(parameter["name"])))
                    text.append('')


                elif method_name in self.emptyQueryParams:
                    text.append('{}ObjectMap queryParams = new ObjectMap();'.format((' ' * 8)))
                    exists_query_params = True
                exists_body_params, body_params, object_name = self.get_body_params(endpoint)

                if body_params or exists_body_params:
                    if self.isBodyParamsEmpty(body_params):
                        if object_name == "Map":
                            text.append(
                                '{}{} {} = new {}();'.format((' ' * 8), "ObjectMap", self.get_as_variable_name(object_name), "ObjectMap"))
                        else:
                            text.append(
                                '{}{} {} = new {}();'.format((' ' * 8), object_name, self.get_as_variable_name(object_name), object_name))
                    else:
                        text.append(
                            '{}{} {} = new {}()'.format((' ' * 8), object_name, self.get_as_variable_name(object_name), object_name))

                    new_body_params = []
                    for parameter in body_params:
                        if parameter["name"] not in self.excluded_parameters:
                            new_body_params.append(parameter)

                    for parameter in new_body_params:
                        if parameter["name"] not in self.excluded_parameters:
                            if parameter == new_body_params[-1]:
                                text.append(
                                    '{}.set{}(commandOptions.{});'.format((' ' * 16),
                                                                          self.get_as_class_name(self.replace_names(parameter["name"])),
                                                                          self.replace_names(parameter["name"])))
                            else:
                                text.append(
                                    '{}.set{}(commandOptions.{})'.format((' ' * 16),
                                                                         self.get_as_class_name(self.replace_names(parameter["name"])),
                                                                         self.replace_names(parameter["name"])))
                        self.java_types.add(parameter["typeClass"])

                    text.append('')
                text.append(
                    '{}return openCGAClient.get{}Client().{}({}{}{}{});'.format((' ' * 8), self.categories[category['name']],
                                                                                method_name,
                                                                                ','.join(
                                                                                    self.get_command_options_path_params(endpoint)).strip(),
                                                                                str(','.join(self.get_return_query_params(endpoint,
                                                                                                                          method_name,
                                                                                                                          category))).strip(

                                                                                ), self.get_return_body_params(exists_body_params,
                                                                                                               object_name, endpoint
                                                                                                               )
                                                                                ,
                                                                                self.get_return_query_params_no_mandatory(
                                                                                    exists_query_params, exists_body_params, endpoint
                                                                                )))
                text.append('    }')
        text.append(' ' * 4)
        return '\n'.join(text)

    def isBodyParamsEmpty(self, parameters):
        count = 0
        for excluded in self.excluded_parameters:
            for parameter in parameters:
                if excluded == parameter["name"]:
                    count = count + 1
        return (len(parameters) - count) == 0

    def replace_names(self, parameter):
        if parameter in self.normalized_objects_map.keys():
            return self.normalized_objects_map[parameter]
        return parameter

    def get_parameter_option(self, parameter, text, name, category):
        if self.check_ignore_subcommand(category, name):
            description = parameter["description"]
            required = parameter["required"]
            type_param = self.param_types[str(parameter['type'])]
            self.append_parameter(text, self.to_kebab_case(parameter["name"].replace('.', '-')), description,
                                  str(required).lower())
            append_text(text, '{}public {} {}; '.format((' ' * 8), type_param, name, parameter["name"]), 8)
            text.append('{}'.format(' ' * 4))
        else:
            text.append('// Exclusion ' + name)

    def check_not_ignored_command(self, param, method_name):
        res = True
        if param in self.exclude_commands.keys() and method_name in self.exclude_commands[param]:
            res = False
        return res

    def to_kebab_case(self, name):
        name = re.sub('(.)([A-Z][a-z]+)', r'\1-\2', name)
        name = re.sub('__([A-Z])', r'-\1', name)
        name = re.sub('([a-z0-9])([A-Z])', r'\1-\2', name)
        return name.lower()

    def get_file_name(self, category):
        return self.categories[self.get_category_name(category)] + "CommandExecutor.java"

    def get_method_name(self, endpoint, category):
        method_name = super().get_method_name(endpoint, category)
        # Convert to cammel case
        method_name = method_name.replace('_', ' ').title().replace(' ', '')
        return method_name[0].lower() + method_name[1:]

    def get_parameter_description(self, parameter):
        return "."

    def get_method_parameters(self, endpoint):
        return endpoint["parameters"]

    def get_parameter_type(self, parameter):
        return parameter["type"]

    def get_valid_parameter_type(self, my_type, my_import):
        if my_type.endswith(';'):
            my_type = my_type[:-1]

        if '$' in my_type:
            my_type = my_type.replace('$', '.')
        if '$' in my_import:
            my_import = my_import.replace('$', '.')

        if my_type == '' or my_type == 'Map':
            my_type = 'ObjectMap'
        else:
            self.type_imports[my_import] = my_import
        self.java_types.add(my_type)

        return my_type

    def normalize_names(self, name):
        res = name
        if name == "default":
            res = "defaultParam"
        if '_' in name:
            res = ''.join(word.title() for word in name.split('_'))
        if '.' in name:
            res = ''.join(word.title() for word in name.split('.'))
        res = res[0].lower() + res[1:]
        return res

    def get_as_class_name(self, attribute):
        return attribute[0].upper() + attribute[1:]

    def check_ignore_subcommand(self, param, method_name):
        res = True
        if param in self.exclude_command_params.keys() and method_name in self.exclude_command_params[param]:
            res = False
        return res

    def get_query_params(self, endpoint, mandatory):
        query_params_no_mandatory = []
        for parameter in endpoint["parameters"]:
            if (parameter["param"] == "query") and parameter["required"] == mandatory:
                if str(parameter['type']) in self.param_types.values() or str(parameter['type']) == 'string' or str(
                        parameter['typeClass']) == 'org.opencb.opencga.catalog.utils.ParamUtils$AclAction;' or str(
                    parameter['type']) == 'enum':
                    query_params_no_mandatory.append(parameter)
        return query_params_no_mandatory

    def normalize_object(self, param, category):
        if param in self.normalized_objects_map.keys():
            param = self.normalized_objects_map[param]
        elif param == "":
            if category.lower == "clinical":
                param = self.get_as_class_name("clinicalAnalysis");
            else:
                param = "ObjectMap";
        return param.replace("$", ".")

    def get_body_params(self, endpoint):
        parameters = self.get_method_parameters(endpoint)
        body_params = []
        object_name = ''
        exists_body_params = False
        for param in parameters:
            name = self.normalize_names(param["name"])
            if name == 'body':
                exists_body_params = True
                object_name = self.get_object_name(param['typeClass'])
                if "data" in param.keys():
                    data = param["data"]
                    for item in data:
                        if str(item['type']) in self.param_types.values():
                            body_params.append(item)

        return exists_body_params, body_params, object_name

    def get_object_name(self, typeClass):
        return typeClass[typeClass.rindex('.') + 1: len(typeClass) - 1:]

    def get_command_options_path_params(self, endpoint):
        path_params = self.get_path_params(endpoint)
        res = []
        for param in path_params:
            res.append("commandOptions." + param)

        return res

    def get_return_query_params(self, endpoint, method, category):
        res = []
        query_params = self.get_query_params(endpoint, True)
        if query_params:
            if self.get_path_params(endpoint):
                res.append(" ")
            for param in query_params:
                res.append("commandOptions.{}".format(param["name"]))
        return res

    def get_return_body_params(self, exists_body_params, object_name, endpoint):
        res = ""

        if exists_body_params:
            if self.get_query_params(endpoint, True) or self.get_path_params(endpoint):
                res = ", " + self.get_as_variable_name(object_name)
            else:
                res = self.get_as_variable_name(object_name)
        return res

    def get_return_query_params_no_mandatory(self, exists_query_params, exists_body_params, endpoint):
        res = ""

        if exists_query_params:
            if self.get_query_params(endpoint, True) or self.get_path_params(endpoint) or exists_body_params:
                res = ", queryParams"
            else:
                res = "queryParams"
        return res


def remove_redundant_imports(imports):
    to_remove = []
    for i in range(len(imports) - 1):
        for j in range(i + 1, len(imports)):
            if imports[i][:-1] + "." in imports[j][:-1]:
                to_remove.append(imports[j])
            elif imports[j][:-1] + "." in imports[i][:-1]:
                to_remove.append(imports[i])

    for my_import in to_remove:
        imports.remove(my_import)

    return imports


def append_text(array, string, sep):
    _append_text(array, string, sep, sep, False)


def append_comment_text(array, string, sep, sep2=None):
    _append_text(array, string, sep, sep if sep2 is None else sep2, True)


def _append_text(array, string, sep, sep2, comment):
    if len(string) <= 140:
        array.append(string)
    else:
        res = ''
        for word in string.split(' '):
            res += word + ' '
            if len(res) > 140:
                array.append(res + '"+')
                res = (' ' * 30) + ' "'
        array.append(res)


def _setup_argparse():
    desc = 'This script creates automatically all RestClients files'
    parser = argparse.ArgumentParser(description=desc)

    parser.add_argument('output_dir', help='output directory')
    args = parser.parse_args()
    return args


def main():
    # Getting arg parameters
    args = _setup_argparse()
    client_generator = ExecutorCliGenerator(args.output_dir)
    # client_generator = JavaClientGenerator(args.server_url, args.output_dir)
    client_generator.create_rest_clients()


if __name__ == '__main__':
    sys.exit(main())