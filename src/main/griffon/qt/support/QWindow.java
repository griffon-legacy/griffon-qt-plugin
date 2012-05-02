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

import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QHideEvent;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QShowEvent;
import griffon.qt.support.event.QWidgetListener;
import griffon.qt.support.event.QWindowListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Andres Almiray
 */
public class QWindow extends QMainWindow {
    private final List<QWindowListener> windowListeners = new CopyOnWriteArrayList<QWindowListener>();
    private final List<QWidgetListener> widgetListeners = new CopyOnWriteArrayList<QWidgetListener>();

    public void addWindowListener(QWindowListener listener) {
        if (null == listener || windowListeners.contains(listener)) return;
        windowListeners.add(listener);
    }

    public void addWidgetListener(QWidgetListener listener) {
        if (null == listener || widgetListeners.contains(listener)) return;
        widgetListeners.add(listener);
    }

    public void removeWindowListener(QWindowListener listener) {
        if (null == listener || !windowListeners.contains(listener)) return;
        windowListeners.remove(listener);
    }

    public void removeWidgetListener(QWidgetListener listener) {
        if (null == listener || !widgetListeners.contains(listener)) return;
        widgetListeners.remove(listener);
    }

    private void fireCloseEvent(QCloseEvent event) {
        for (QWindowListener listener : windowListeners) {
            listener.windowClosed(event, this);
        }
    }

    private void fireShowEvent(QShowEvent event) {
        for (QWidgetListener listener : widgetListeners) {
            listener.widgetShown(event, this);
        }
    }

    private void fireHideEvent(QHideEvent event) {
        for (QWidgetListener listener : widgetListeners) {
            listener.widgetHidden(event, this);
        }
    }

    @Override
    protected void closeEvent(QCloseEvent arg__1) {
        super.closeEvent(arg__1);
        fireCloseEvent(arg__1);
    }

    @Override
    protected void hideEvent(QHideEvent arg__1) {
        super.hideEvent(arg__1);
        fireHideEvent(arg__1);
    }

    @Override
    protected void showEvent(QShowEvent arg__1) {
        super.showEvent(arg__1);
        fireShowEvent(arg__1);
    }
}
