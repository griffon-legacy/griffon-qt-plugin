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
import com.trolltech.qt.gui.QWidget;
import griffon.application.StandaloneGriffonApplication;
import griffon.core.UIThreadManager;
import griffon.qt.support.QWindow;
import griffon.util.GriffonExceptionHandler;
import griffon.util.UIThreadHandler;
import org.codehaus.griffon.runtime.core.AbstractGriffonApplication;

import java.lang.reflect.InvocationTargetException;

import static griffon.util.GriffonExceptionHandler.sanitize;

/**
 * Basic implementation of {@code GriffonApplication} that runs in standalone mode using Qt.
 *
 * @author Andres Almiray
 */
public abstract class AbstractQtGriffonApplication extends AbstractGriffonApplication implements QtGriffonApplication,
    StandaloneGriffonApplication {
    private final WindowManager windowManager;
    private WindowDisplayHandler windowDisplayHandler;
    private final WindowDisplayHandler defaultWindowDisplayHandler = new ConfigurableWindowDisplayHandler();
    @SuppressWarnings("rawtypes")
    private static final Class[] CTOR_ARGS = new Class[]{String[].class};

    public AbstractQtGriffonApplication() {
        this(AbstractQtGriffonApplication.EMPTY_ARGS);
    }

    public AbstractQtGriffonApplication(String[] args) {
        super(args);
        windowManager = new WindowManager(this);
        UIThreadManager.getInstance().setUIThreadHandler(getUIThreadHandler());
        addShutdownHandler(windowManager);
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public WindowDisplayHandler getWindowDisplayHandler() {
        return windowDisplayHandler;
    }

    public void setWindowDisplayHandler(WindowDisplayHandler windowDisplayHandler) {
        this.windowDisplayHandler = windowDisplayHandler;
    }

    protected UIThreadHandler getUIThreadHandler() {
        return new QtUIThreadHandler();
    }

    public final WindowDisplayHandler resolveWindowDisplayHandler() {
        return windowDisplayHandler != null ? windowDisplayHandler : defaultWindowDisplayHandler;
    }

    public void bootstrap() {
        initialize();
    }

    public void realize() {
        startup();
    }

    public void show() {
        QWindow startingWindow = windowManager.getStartingWindow();
        windowManager.show(startingWindow);
        callReady(startingWindow);
    }

    public boolean shutdown() {
        if (super.shutdown()) {
            exit();
        }
        return false;
    }

    public void exit() {
        System.exit(0);
    }

    public Object createApplicationContainer() {
        final QWidget[] window = new QWidget[1];
        UIThreadManager.getInstance().executeSync(new Runnable() {
            public void run() {
                window[0] = new QWindow();
            }
        });
        return window[0];
    }

    /**
     * Calls the ready lifecycle method after the UI thread calms down
     */
    protected void callReady(QWindow startingWindow) {
        ready();
        QApplication.exec();
        System.gc();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void run(Class applicationClass, String[] args) {
        GriffonExceptionHandler.registerExceptionHandler();
        StandaloneGriffonApplication app = null;
        try {
            app = (StandaloneGriffonApplication) applicationClass.getDeclaredConstructor(CTOR_ARGS).newInstance(new Object[]{args});
            app.bootstrap();
            app.realize();
            app.show();
        } catch (InstantiationException e) {
            sanitize(e).printStackTrace();
        } catch (IllegalAccessException e) {
            sanitize(e).printStackTrace();
        } catch (InvocationTargetException e) {
            sanitize(e).printStackTrace();
        } catch (NoSuchMethodException e) {
            sanitize(e).printStackTrace();
        }
    }
}
