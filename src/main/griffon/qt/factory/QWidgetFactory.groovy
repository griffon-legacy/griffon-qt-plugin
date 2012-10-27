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

package griffon.qt.factory

import griffon.qt.support.QActionWrapper
import com.trolltech.qt.gui.*

/**
 * @author Andres Almiray
 */
class QWidgetFactory extends QObjectFactory {
    QWidgetFactory(Class beanClass) {
        this(beanClass, false)
    }

    QWidgetFactory(Class beanClass, boolean leaf) {
        super(beanClass, leaf)
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map attributes) {
        if (attributes.containsKey('action')) {
            if (node instanceof QWidget) {
                def action = attributes.remove('action')
                switch (action) {
                    case QAction:
                        node.addAction action
                        break
                    case Closure:
                        node.addAction new QActionWrapper(node, action)
                        break
                    default:
                        throw new IllegalArgumentException("Can't set action: property, value mus be a ${QAction.name} or a ${Closure.name}")
                }
            } else {
                throw new IllegalArgumentException("Can't set action: on a non-widget node")
            }
        }

        return super.onHandleNodeAttributes(builder, node, attributes)
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof QMainWindow) {
            switch (child) {
                case QToolBar:
                    parent.addToolBar child
                    break
                case QMenuBar:
                    parent.menuBar = child
                    break
                case QStatusBar:
                    parent.statusBar = child
                    break
                case QLayout:
                    if (null == parent.centralWidget()) {
                        parent.centralWidget = new QGroupBox()
                    }
                    parent.centralWidget().layout = child
                    break
                case QWidget:
                    parent.centralWidget = child
            }
        } else if (child instanceof QLayout) {
            parent.layout = child
        } else if (child instanceof QAction) {
            parent.addAction child
        } else if (child instanceof QWidget) {
            if (parent instanceof QSplitter || parent instanceof QStatusBar
                || parent instanceof QStackedWidget || parent instanceof QToolBar) {
                parent.addWidget child
            } else {
                QLayoutFactory.handleChildWidget(builder, parent.layout(), child)
            }
        } else {
            super.setChild(builder, parent, child)
        }
    }
}
