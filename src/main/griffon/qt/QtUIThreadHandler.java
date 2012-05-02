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
import org.codehaus.griffon.runtime.util.AbstractUIThreadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes code honoring Qt's threading model.
 *
 * @author Andres Almiray
 */
public class QtUIThreadHandler extends AbstractUIThreadHandler {
    private static final Logger LOG = LoggerFactory.getLogger(QtUIThreadHandler.class);

    public boolean isUIThread() {
        boolean isUIThread = QApplication.instance().thread() == Thread.currentThread();
        if(LOG.isTraceEnabled()) {
            LOG.trace("isUIThread? "+isUIThread);
        }
        return isUIThread;
    }

    public void executeAsync(Runnable runnable) {
        QApplication.invokeLater(runnable);
    }

    public void executeSync(Runnable runnable) {
        if (isUIThread()) {
            runnable.run();
        } else {
            QApplication.invokeAndWait(runnable);
        }
    }
}
