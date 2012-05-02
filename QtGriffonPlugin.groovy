/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Andres Almiray
 */
class QtGriffonPlugin {
    String version = '0.1-SNAPSHOT'
    String griffonVersion = '0.9.6 > *'
    Map dependsOn = [:]
    List pluginIncludes = []
    String license = 'Apache Software License 2.0'
    List toolkits = ['qt']
    List platforms = []
    String documentation = ''
    String source = 'https://github.com/griffon/griffon-qt-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'Enables Qt support'
    String description = '''
Enables Qt as UI toolkit using [qt-jambi][1].

Usage
----
TBD

Configuration
-------------
There's no special configuration for this plugin.

[1]: http://qt-jambi.org
'''
}
