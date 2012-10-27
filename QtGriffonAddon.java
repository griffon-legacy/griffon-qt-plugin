/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.webkit.QWebView;
import griffon.qt.factory.*;
import griffon.qt.support.QGridBox;
import griffon.qt.support.QHBox;
import griffon.qt.support.QStackedBox;
import griffon.qt.support.QVBox;
import griffon.util.ApplicationHolder;
import griffon.util.RunnableWithArgs;
import griffon.util.RunnableWithArgsClosure;
import groovy.util.FactoryBuilderSupport;
import org.codehaus.griffon.runtime.core.AbstractGriffonAddon;
import org.codehaus.groovy.runtime.MethodClosure;

import java.util.Map;

/**
 * @author Andres Almiray
 */
public class QtGriffonAddon extends AbstractGriffonAddon {
    public QtGriffonAddon() {
        super(ApplicationHolder.getApplication());
        // -- support
        factories.put("bean", new QWidgetFactory(Object.class, false));
        factories.put("list", new CollectionFactory());
        factories.put("noparent", new CollectionFactory());
        factories.put("map", new MapFactory());

        factories.put("action", new QActionFactory());
        factories.put("widgetAction", new RichWidgetFactory(QWidgetAction.class));

        // -- griffon
        factories.put("application", new ApplicationFactory());

        // -- containers
        factories.put("groupBox", new QWidgetFactory(QGroupBox.class));
        factories.put("hbox", new QWidgetFactory(QHBox.class));
        factories.put("vbox", new QWidgetFactory(QVBox.class));
        factories.put("gridBox", new QWidgetFactory(QGridBox.class));
        factories.put("stackedBox", new QWidgetFactory(QStackedBox.class));

        // -- Layouts
        factories.put("hboxLayout", new QLayoutFactory(QHBoxLayout.class));
        factories.put("vboxLayout", new QLayoutFactory(QVBoxLayout.class));
        factories.put("gridLayout", new QLayoutFactory(QGridLayout.class));
        factories.put("stackedLayout", new QLayoutFactory(QStackedLayout.class));

        // -- widgets
        factories.put("calendarWidget", new QWidgetFactory(QCalendarWidget.class));
        factories.put("dateEdit", new QWidgetFactory(QDateEdit.class));
        factories.put("dateTimeEdit", new QWidgetFactory(QDateTimeEdit.class));
        factories.put("timeEdit", new QWidgetFactory(QTimeEdit.class));
        factories.put("dial", new QWidgetFactory(QDial.class));
        factories.put("slider", new QWidgetFactory(QSlider.class));
        factories.put("spinBox", new QWidgetFactory(QSpinBox.class));
        factories.put("doubleSpinBox", new QWidgetFactory(QDoubleSpinBox.class));
        factories.put("splitter", new QWidgetFactory(QSplitter.class));
        factories.put("statusBar", new QWidgetFactory(QStatusBar.class));
        factories.put("stackedWidget", new QWidgetFactory(QStackedWidget.class));
        factories.put("toolBar", new QToolBarFactory());
        factories.put("icon", new QIconFactory());
        factories.put("image", new QImageFactory());
        factories.put("pixmap", new QPixmapFactory());
        factories.put("label", new TextWidgetFactory(QLabel.class));
        factories.put("lineEdit", new TextWidgetFactory(QLineEdit.class));
        factories.put("textEdit", new TextWidgetFactory(QTextEdit.class));
        factories.put("menuBar", new QMenuBarFactory());
        factories.put("menu", new QMenuFactory());

        // -- buttons
        factories.put("buttonGroup", new QButtonGroupFactory());
        factories.put("checkBox", new RichWidgetFactory(QCheckBox.class));
        factories.put("pushButton", new RichWidgetFactory(QPushButton.class));
        factories.put("radioButton", new RichWidgetFactory(QRadioButton.class));
        factories.put("toolButton", new RichWidgetFactory(QToolButton.class));

        // -- web
        factories.put("webView", new QWidgetFactory(QWebView.class));

        attributeDelegates.add(new RunnableWithArgsClosure(new RunnableWithArgs() {
            public void run(Object[] args) {
                FactoryBuilderSupport builder = (FactoryBuilderSupport) args[0];
                Object node = args[1];
                Map attributes = (Map) args[2];
                if (attributes.containsKey("id")) {
                    String id = attributes.remove("id").toString();
                    builder.setVariable(id, node);
                    if (node instanceof QObject) {
                        QObject object = (QObject) node;
                        object.setObjectName(id);
                    }
                }
            }
        }));

        attributeDelegates.add(new MethodClosure(QButtonGroupFactory.class, "buttonGroupAttributeDelegate"));
        attributeDelegates.add(new MethodClosure(QLayoutFactory.class, "constraintsAttributeDelegate"));
    }
}
