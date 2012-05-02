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


import griffon.qt.support.QWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@code WindowDisplayHandler} that simply makes the window
 * visible on show() and disposes it on hide().
 *
 * @author Andres Almiray
 */
public class DefaultWindowDisplayHandler implements WindowDisplayHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultWindowDisplayHandler.class);

    public void show(QWindow window, QtGriffonApplication application) {
        if (window != null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Showing window '" + window.windowTitle() + "' " + window);
            }
            window.show();
            window.raise();
        }
    }

    public void hide(QWindow window, QtGriffonApplication application) {
        if (window != null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Hiding window '" + window.windowTitle() + "' " + window);
            }
            window.hide();
        }
    }
}