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
    String version = '0.1'
    String griffonVersion = '1.1.0 > *'
    Map dependsOn = [:]
    List pluginIncludes = []
    String license = 'Apache Software License 2.0'
    List toolkits = ['qt']
    List platforms = ['linux', 'linux64', 'windows', 'windows64', 'macosx64']
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

Qt is the de facto standard C++ framework for high performance cross-platform software development.
Qt Jambi is the Qt library made available to Java. It is an open source technology aimed at all desktop
programmers wanting to write rich GUI clients using the Java language, while at the same time taking
advantage of Qt’s power and efficiency.

Usage
-----

The following nodes will become available on a View script upon installing this plugin

| *Name*         | *Type*                                 |
| -------------- | -------------------------------------- |
| action         | `com.trolltech.qt.gui.QAction`         |
| application    | `com.trolltech.qt.gui.QMainWindow`     |
| bean           | `java.lang.Object`                     |
| buttonGroup    | `com.trolltech.qt.gui.QButtonGroup`    |
| calendarWidget | `com.trolltech.qt.gui.QCalendarWidget` |
| checkBox       | `com.trolltech.qt.gui.QCheckBox`       |
| dateEdit       | `com.trolltech.qt.gui.QDateEdit`       |
| dateTimeEdit   | `com.trolltech.qt.gui.QDateTimeEdit`   |
| dial           | `com.trolltech.qt.gui.QDial`           |
| doubleSpinBox  | `com.trolltech.qt.gui.QDoubleSpinBox`  |
| gridBox        | `com.trolltech.qt.gui.QGridBox`        |
| groupBox       | `com.trolltech.qt.gui.QGroupBox`       |
| hbox           | `com.trolltech.qt.gui.QHBox`           |
| hboxLayout     | `com.trolltech.qt.gui.QHBoxLayout`     |
| icon           | `com.trolltech.qt.gui.QIcon`           |
| image          | `com.trolltech.qt.gui.QImage`          |
| label          | `com.trolltech.qt.gui.QLabel`          |
| lineEdit       | `com.trolltech.qt.gui.QLineEdit`       |
| list           | `java.util.Collection`                 |
| map            | `java.util.Map`                        |
| menu           | `com.trolltech.qt.gui.QMenu`           |
| menuBar        | `com.trolltech.qt.gui.QMenuBar`        |
| noparent       | `java.util.Collection`                 |
| pixmap         | `com.trolltech.qt.gui.QPixmap`         |
| pushButton     | `com.trolltech.qt.gui.QPushButton`     |
| radioButton    | `com.trolltech.qt.gui.QRadioButton`    |
| slider         | `com.trolltech.qt.gui.QSlider`         |
| spinBox        | `com.trolltech.qt.gui.QSpinBox`        |
| splitter       | `com.trolltech.qt.gui.QSplitter`       |
| stackedBox     | `com.trolltech.qt.gui.QStackedBox`     |
| stackedWidget  | `com.trolltech.qt.gui.QStackedWidget`  |
| statusBar      | `com.trolltech.qt.gui.QStatusBar`      |
| textEdit       | `com.trolltech.qt.gui.QTextEdit`       |
| timeEdit       | `com.trolltech.qt.gui.QTimeEdit`       |
| toolBar        | `com.trolltech.qt.gui.QToolBar`        |
| toolButton     | `com.trolltech.qt.gui.QToolButton`     |
| vbox           | `com.trolltech.qt.gui.QVBox`           |
| gridLayout     | `com.trolltech.qt.gui.QGridLayout`     |
| stackedLayout  | `com.trolltech.qt.gui.QStackedLayout`  |
| vboxLayout     | `com.trolltech.qt.gui.QVBoxLayout`     |
| webView        | `com.trolltech.qt.webkit.QWebView`     |
| widgetAction   | `com.trolltech.qt.gui.QWidgetAction`   |

More to come in future releases.

Configuration
-------------

This plugin applies some changes to the runtime configuration when the application is run for the first time. It's possible that
this configuration is not honored the first time the application is started resulting in a frozen application. If so, then simply
quit and restart. The problem will not appear again.

This plugin provides custom `WindowManager` and `GriffonControllerActionManager` implementations.

The following actions properties can be configured using the ActionManager's external configuration convention:

{table}
| *Key*             | *Default Value* |
| autoRepeat        | false           |
| checkable         | false           |
| checked           | false           |
| icon              |                 |
| iconText          |                 |
| iconVisibleInMenu | true            |
| shortcut          |                 |
| statusTip         |                 |
| text              |                 |
| toolTip           |                 |
| visible           | true            |
| whatsThis         |                 |
{table}

Basic property editors are supplied for the following classes

 * com.trolltech.qt.gui.QColor
 * com.trolltech.qt.gui.QIcon
 * com.trolltech.qt.gui.QImage
 * com.trolltech.qt.gui.QPixmap

### Example

The following example demonstrates how a basic webbrowser can be built

__BrowserController.groovy__

        import griffon.transform.Threading
        import com.trolltech.qt.core.QUrl
        class BrowserController {
            def model
            def view

            void mvcGroupInit(Map<String, Object> args) {
                execInsideUIAsync {
                    view.mainWindow.statusBar().show()
                    view.urlField.text = 'http://griffon-framework.org'
                    openUrl()
                }
            }

            @Threading(Threading.Policy.SKIP)
            def openUrl = {
                String text = view.urlField.text()
                if (text.indexOf('://') < 0) text = 'http://' + text
                view.browser.load(new QUrl(text))
            }
        }

__BrowserView.groovy__

        updateStatus = { msg -> mainWindow.statusBar().showMessage(msg) }
        application(windowTitle: 'Qt Browser',
            id: 'mainWindow',
            windowIcon: icon('classpath:/griffon-icon-16x16.png'),
            minimumWidth: 800,
            fixedHeight: 600) {

            webView(id: 'browser',
                loadStarted:  { updateStatus("Starting to load: ${urlField.text()}") },
                loadProgress: { updateStatus("Loading: $it %") },
                loadFinished: { updateStatus('Loading done...') },
                urlChanged:   { urlField.text = it })

            noparent {
                action(id: 'backAction',
                    iconVisibleInMenu: false,
                    icon: icon('classpath:/back.png'),
                    iconText: 'Back',
                    shortcut: 'Ctrl+[',
                    closure: browser.&back)
                action(id: 'forwardAction',
                    iconVisibleInMenu: false,
                    icon: icon('classpath:/forward.png'),
                    iconText: 'Forward',
                    shortcut: 'Ctrl+]',
                    closure: browser.&forward)
                action(id: 'reloadAction',
                    iconVisibleInMenu: false,
                    icon: icon('classpath:/reload.png'),
                    iconText: 'Reload',
                    shortcut: 'Ctrl+R',
                    closure: browser.&reload)
                action(id: 'stopAction',
                    iconVisibleInMenu: false,
                    icon: icon('classpath:/stop.png'),
                    iconText: 'Stop',
                    shortcut: 'Ctrl+.',
                    closure: browser.&stop)
            }

            menuBar {
                menu('History') {
                    action(backAction)
                    action(forwardAction)
                    action(reloadAction)
                    action(stopAction)
                }
            }

            toolBar(floatable: false, movable: false) {
                action(backAction)
                action(forwardAction)
                action(reloadAction)
                action(stopAction)
                lineEdit(id: 'urlField', returnPressed: controller.openUrl)
            }
        }

Notice at the end of the View script there's a `lineEdit` component on which a signal is connected directly to a particular Controller
action. The plugin is smart enough to figure out that Groovy closures can be registered as signal handlers.

[1]: http://qt-jambi.org
'''
}
