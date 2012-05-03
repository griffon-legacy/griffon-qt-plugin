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

package griffon.qt;

import com.trolltech.qt.gui.QApplication;

/**
 * @author Andres Almiray
 */
public class QtApplication extends AbstractQtGriffonApplication {
    public QtApplication() {
        this(AbstractQtGriffonApplication.EMPTY_ARGS);
    }

    public QtApplication(String[] args) {
        super(args);
        QApplication.setApplicationName(System.getProperty("griffon.application.name"));
        QApplication.initialize(args);
    }

    public static void main(String[] args) {
        AbstractQtGriffonApplication.run(QtApplication.class, args);
    }
}
