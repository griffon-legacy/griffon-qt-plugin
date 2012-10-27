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

import com.trolltech.qt.gui.QMenu

/**
 * @author Andres Almiray
 */
class QMenuFactory extends QWidgetFactory {
    QMenuFactory() {
        super(QMenu)
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
        if (value instanceof CharSequence) {
            new QMenu(value.toString())
        } else {
            super.newInstance(builder, name, value, attributes)
        }
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (child instanceof QMenu) {
            parent.addMenu(child)
        } else {
            super.setChild(builder, parent, child)
        }
    }
}
