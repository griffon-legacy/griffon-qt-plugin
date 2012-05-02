/*
 * Copyright 2012 the original author or authors.
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

package griffon.qt.factory

import com.trolltech.qt.gui.QAction
import com.trolltech.qt.gui.QIcon
import com.trolltech.qt.gui.QWidget
import griffon.qt.support.QActionWrapper

/**
 * @author Andres Almiray
 */
class QActionFactory extends QObjectFactory {
    QActionFactory() {
        super(QAction)
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        String text = ''
        QIcon icon = null
        QWidget parent = attributes.remove('parent')
        if (null == parent && builder.current instanceof QWidget) {
            parent = builder.current
        }
        Closure closure = attributes.remove('closure')

        if (value instanceof CharSequence) {
            text = value.toString()
        } else if (value instanceof QIcon) {
            icon = value
        }

        if (null == text) text = attributes.remove('text')
        if (null == icon) icon = attributes.remove('icon')

        new QActionWrapper(icon, text, parent, closure)
    }
}