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

import com.trolltech.qt.gui.QWidget

/**
 * @author Andres Almiray
 */
class TextWidgetFactory extends QWidgetFactory {
    TextWidgetFactory(Class beanClass) {
        super(beanClass)
    }

    TextWidgetFactory(Class beanClass, boolean leaf) {
        super(beanClass, leaf)
    }

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
        String text = ''
        if (value instanceof CharSequence) {
            text = value.toString()
            value = null
        }
        QWidget widget = super.newInstance(builder, name, value, attributes)
        widget.text = text
        widget
    }
}
