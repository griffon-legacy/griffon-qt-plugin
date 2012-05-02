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

import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QHideEvent;
import com.trolltech.qt.gui.QShowEvent;
import com.trolltech.qt.gui.QWidget;
import griffon.core.ApplicationPhase;
import griffon.core.GriffonApplication;
import griffon.core.ShutdownHandler;
import griffon.qt.support.QWindow;
import griffon.qt.support.event.QWidgetAdapter;
import griffon.qt.support.event.QWindowAdapter;
import griffon.util.ConfigUtils;
import griffon.util.GriffonNameUtils;
import groovy.util.ConfigObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;

/**
 * Controls a set of windows that belong to the application.
 * <p/>
 * Windows that are controlled by a WindowManager can be shown/hidden using a custom strategy ({@code WindowDisplayHandler})
 *
 * @author Andres Almiray
 * @see griffon.qt.WindowDisplayHandler
 */
public final class WindowManager implements ShutdownHandler {
    private static final Logger LOG = LoggerFactory.getLogger(WindowManager.class);
    private final QtGriffonApplication app;
    private final WindowHelper windowHelper = new WindowHelper();
    private final WidgetHelper widgetHelper = new WidgetHelper();
    private final Map<String, QWindow> windows = new ConcurrentHashMap<String, QWindow>();

    /**
     * Creates a new WindowManager tied to an specific application.
     *
     * @param app an application
     */
    public WindowManager(QtGriffonApplication app) {
        this.app = app;
    }

    /**
     * Finds a Window by name.
     *
     * @param name the value of the name: property
     * @return a Window if a match is found, null otherwise.
     */
    public QWindow findWindow(String name) {
        if (!GriffonNameUtils.isBlank(name)) {
            return windows.get(name);
        }
        return null;
    }

    public String findWindowName(QWindow window) {
        if (window != null) {
            for (Map.Entry<String, QWindow> entry : windows.entrySet()) {
                if (entry.getValue() == window) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Finds the Window that should be displayed during the Ready phase of an application.
     * <p/>
     * The WindowManager expects a configuration flag <code>qt.windowManager.startingWindow</code> to be present in order to
     * determine which Window will be displayed during the Ready phase. If no configuration is found the WindowManmager will pick the
     * first Window found in the list of managed windows.
     * <p/>
     * The configuration flag accepts two value types:
     * <ul>
     * <li>a String that defines the name of the Window. You must make sure the Window has a matching name property.</li>
     * <li>a Number that defines the index of the Window in the list of managed windows.</li>
     * </ul>
     *
     * @return a Window that matches the given criteria or null if no match is found.
     */
    public QWindow getStartingWindow() {
        QWindow window = null;
        Object value = ConfigUtils.getConfigValue(app.getConfig(), "qt.windowManager.startingWindow");
        if (LOG.isDebugEnabled()) {
            LOG.debug("qt.windowManager.startingWindow configured to " + value);
        }
        if (value == null || value instanceof ConfigObject) {
            if (windows.size() > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No startingWindow configured, selecting the first one in the list of windows");
                }
                window = windows.values().iterator().next();
            }
        } else if (value instanceof String) {
            String windowName = (String) value;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Selecting window " + windowName + " as starting window");
            }
            window = findWindow(windowName);
        } else if (value instanceof Number) {
            int index = ((Number) value).intValue();
            if (index >= 0 && index < windows.size()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Selecting window at index " + index + " as starting window");
                }
                int i = 0;
                for (Iterator<QWindow> iter = windows.values().iterator(); iter.hasNext(); i++) {
                    if (i == index) {
                        window = iter.next();
                        break;
                    }
                }
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Starting window is " + window);
        }

        return window;
    }

    /**
     * Returns the list of windows managed by this manager.
     *
     * @return a List of currently managed windows
     */
    public Collection<QWindow> getWindows() {
        return Collections.<QWindow>unmodifiableCollection(windows.values());
    }

    /**
     * Registers a window on this manager if an only if the window is not null
     * and it's not registered already.
     *
     * @param window the window to be added to the list of managed windows
     */
    public void attach(String name, QWindow window) {
        if (window == null || windows.values().contains(window)) {
            return;
        }
        window.addWindowListener(windowHelper);
        window.addWidgetListener(widgetHelper);
        windows.put(name, window);
    }

    /**
     * Removes the window from the list of manages windows if and only if it
     * is registered with this manager.
     *
     * @param window the window to be removed
     */
    public void detach(QWindow window) {
        if (window == null) {
            return;
        }
        if (windows.values().contains(window)) {
            window.removeWindowListener(windowHelper);
            window.removeWidgetListener(widgetHelper);
            windows.remove(findWindowName(window));
        }
    }

    /**
     * Shows the window.
     * <p/>
     * This method is executed <b>SYNCHRONOUSLY</b> in the UI thread.
     *
     * @param window the window to show
     */
    public void show(final QWindow window) {
        if (window == null) {
            return;
        }
        app.execInsideUISync(new Runnable() {
            public void run() {
                app.resolveWindowDisplayHandler().show(window, app);
            }
        });
    }

    /**
     * Hides the window.
     * <p/>
     * This method is executed <b>SYNCHRONOUSLY</b> in the UI thread.
     *
     * @param window the window to hide
     */
    public void hide(final QWindow window) {
        if (window == null) {
            return;
        }
        app.execInsideUISync(new Runnable() {
            public void run() {
                app.resolveWindowDisplayHandler().hide(window, app);
            }
        });
    }

    public boolean canShutdown(GriffonApplication app) {
        return true;
    }

    /**
     * Hides all visible windows
     */
    public void onShutdown(GriffonApplication app) {
        for (QWindow window : windows.values()) {
            if (window.isVisible()) {
                hide(window);
            }
        }
    }

    public void handleClose(QWindow widget) {
        if (app.getPhase() == ApplicationPhase.SHUTDOWN) {
            return;
        }
        int visibleWindows = 0;
        for (QWindow window : windows.values()) {
            if (window.isVisible()) {
                visibleWindows++;
            }
        }

        Boolean autoShutdown = (Boolean) app.getConfig().flatten().get("application.autoShutdown");
        if (visibleWindows <= 1 && autoShutdown != null && autoShutdown.booleanValue()) {
            if (!app.shutdown())
                show(widget);
        }
    }

    /**
     * WindowAdapter that optionally invokes hide() when the window is about to be closed.
     *
     * @author Andres Almiray
     */
    private class WindowHelper extends QWindowAdapter {
        @Override
        public void windowClosed(QCloseEvent event, QWidget widget) {
            if (app.getPhase() == ApplicationPhase.SHUTDOWN) return;
            int visibleWindows = 0;
            for (QWindow window : windows.values()) {
                if (window.isVisible()) {
                    visibleWindows++;
                }
            }

            if (visibleWindows > 1) hide((QWindow) widget);

            Boolean autoShutdown = (Boolean) app.getConfig().flatten().get("application.autoShutdown");
            if (visibleWindows <= 1 && autoShutdown != null && autoShutdown) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Attempting to shutdown application");
                }
                if (!app.shutdown()) show((QWindow) widget);
            }
        }
    }

    /**
     * WidgetAdapter that triggers application events when a window is shown/hidden.
     *
     * @author Andres Almiray
     */
    private class WidgetHelper extends QWidgetAdapter {
        /**
         * Triggers a <tt>WindowShown</tt> event with the window as sole argument
         */
        public void widgetShown(QShowEvent e, QWidget widget) {
            app.event(GriffonApplication.Event.WINDOW_SHOWN.getName(), asList(widget));
        }

        /**
         * Triggers a <tt>WindowHidden</tt> event with the window as sole argument
         */
        public void widgetHidden(QHideEvent e, QWidget widget) {
            app.event(GriffonApplication.Event.WINDOW_HIDDEN.getName(), asList(widget));
        }
    }
}
