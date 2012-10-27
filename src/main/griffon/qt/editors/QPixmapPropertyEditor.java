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

package griffon.qt.editors;

import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QPixmap;
import griffon.core.resources.editors.AbstractPropertyEditor;

/**
 * @author Andres Almiray
 */
public class QPixmapPropertyEditor extends AbstractPropertyEditor {
    public void setAsText(String value) throws IllegalArgumentException {
        setValue(value);
    }

    public void setValue(Object value) {
        if (null == value) return;
        if (value instanceof CharSequence) {
            handleAsString(String.valueOf(value));
        } else if (value instanceof QPixmap) {
            super.setValue(value);
        } else {
            throw illegalValue(value, QPixmap.class);
        }
    }

    private void handleAsString(String str) {
        super.setValue(new QIcon(str));
    }
}
