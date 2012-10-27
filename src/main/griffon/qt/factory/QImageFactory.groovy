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

import com.trolltech.qt.gui.QImage

/**
 * @author Andres Almiray
 */
class QImageFactory extends QObjectFactory {
    QImageFactory() {
        super(QImage)
    }

    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
        if (value == null) {
            if (attributes.containsKey("image")) {
                value = attributes.remove("image")
                if (!(value instanceof QImage)) {
                    throw new RuntimeException("In $name image: attributes must be of type ${QImage.name}")
                }
            }
        } else if (value instanceof CharSequence) {
            value = value.toString()
        }

        if (value == null) {
            throw new RuntimeException("$name has neither a value argument or one of image: or pixmap:")
        }

        new QImage(value)
    }
}
