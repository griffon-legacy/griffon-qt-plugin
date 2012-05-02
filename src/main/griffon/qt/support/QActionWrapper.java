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

package griffon.qt.support;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QIcon;
import groovy.lang.Closure;

/**
 * @author Andres Almiray
 */
public class QActionWrapper extends QAction {
    private final Closure closure;

    public QActionWrapper(QObject parent, Closure closure) {
        super(parent);
        this.closure = closure;
        triggered.connect(this, "handleTrigger()");
    }

    public QActionWrapper(QIcon icon, String text, QObject parent, Closure closure) {
        super(icon, text, parent);
        this.closure = closure;
        triggered.connect(this, "handleTrigger()");
    }

    public QActionWrapper(String text, QObject parent, Closure closure) {
        super(text, parent);
        this.closure = closure;
        triggered.connect(this, "handleTrigger()");
    }

    public Closure getClosure() {
        return closure;
    }

    public void handleTrigger() {
        closure.call();
    }
}
