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
package org.codehaus.griffon.runtime.qt;

import com.trolltech.qt.gui.QIcon;
import griffon.core.GriffonController;
import griffon.core.UIThreadManager;
import griffon.qt.support.QActionWrapper;
import griffon.util.RunnableWithArgs;
import griffon.util.RunnableWithArgsClosure;
import org.codehaus.griffon.runtime.core.controller.AbstractGriffonControllerAction;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static griffon.util.GriffonNameUtils.isBlank;

/**
 * @author Andres Almiray
 */
public class QtGriffonControllerAction extends AbstractGriffonControllerAction {
    public static final String KEY_AUTO_REPEAT = "autoRepeat";
    public static final String KEY_CHECKABLE = "checkable";
    public static final String KEY_CHECKED = "checked";
    public static final String KEY_ICON = "icon";
    public static final String KEY_ICON_TEXT = "iconText";
    public static final String KEY_ICON_VISIBLE_IN_MENU = "iconVisibleInMenu";
    public static final String KEY_SHORTCUT = "shortcut";
    public static final String KEY_STATUS_TIP = "statusTip";
    public static final String KEY_TEXT = "text";
    public static final String KEY_TOOLTIP = "toolTip";
    public static final String KEY_VISIBLE = "visible";
    public static final String KEY_WHATS_THIS = "whatsThis";

    private boolean autoRepeat;
    private boolean checkable;
    private boolean checked;
    private String icon;
    private String iconText;
    private boolean iconVisibleInMenu = true;
    private String shortcut;
    private String statusTip;
    private String text;
    private String toolTip;
    private boolean visible = true;
    private String whatsThis;

    private final QActionWrapper toolkitAction;

    public QtGriffonControllerAction(GriffonController controller, final String actionName) {
        super(controller, actionName);
        final org.codehaus.griffon.runtime.qt.QtGriffonControllerAction self = this;
        toolkitAction = new QActionWrapper(null, new RunnableWithArgsClosure(new RunnableWithArgs() {
            public void run(Object[] args) {
                InvokerHelper.invokeMethod(self.getController(), actionName, args);
            }
        }));
        addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent evt) {
                UIThreadManager.getInstance().executeAsync(new Runnable() {
                    public void run() {
                        if (KEY_AUTO_REPEAT.equals(evt.getPropertyName())) {
                            toolkitAction.setAutoRepeat((Boolean) evt.getNewValue());
                        } else if (KEY_CHECKABLE.equals(evt.getPropertyName())) {
                            toolkitAction.setCheckable((Boolean) evt.getNewValue());
                        } else if (KEY_CHECKED.equals(evt.getPropertyName()) && toolkitAction.isCheckable()) {
                            toolkitAction.setChecked((Boolean) evt.getNewValue());
                        } else if (KEY_ENABLED.equals(evt.getPropertyName())) {
                            toolkitAction.setEnabled((Boolean) evt.getNewValue());
                        } else if (KEY_ICON.equals(evt.getPropertyName())) {
                            String _icon = (String) evt.getNewValue();
                            toolkitAction.setIcon(!isBlank(_icon) ? new QIcon(_icon) : null);
                        } else if (KEY_ICON_TEXT.equals(evt.getPropertyName())) {
                            toolkitAction.setIconText((String) evt.getNewValue());
                        } else if (KEY_ICON_VISIBLE_IN_MENU.equals(evt.getPropertyName())) {
                            toolkitAction.setIconVisibleInMenu((Boolean) evt.getNewValue());
                        } else if (KEY_SHORTCUT.equals(evt.getPropertyName())) {
                            toolkitAction.setShortcut((String) evt.getNewValue());
                        } else if (KEY_STATUS_TIP.equals(evt.getPropertyName())) {
                            toolkitAction.setStatusTip((String) evt.getNewValue());
                        } else if (KEY_TEXT.equals(evt.getPropertyName())) {
                            toolkitAction.setText((String) evt.getNewValue());
                        } else if (KEY_TOOLTIP.equals(evt.getPropertyName())) {
                            toolkitAction.setToolTip((String) evt.getNewValue());
                        } else if (KEY_VISIBLE.equals(evt.getPropertyName())) {
                            toolkitAction.setVisible((Boolean) evt.getNewValue());
                        } else if (KEY_WHATS_THIS.equals(evt.getPropertyName())) {
                            toolkitAction.setWhatsThis((String) evt.getNewValue());
                        }
                    }
                });
            }
        });
    }

    public Object getToolkitAction() {
        return toolkitAction;
    }

    protected void doExecute(Object... args) {
        toolkitAction.handleTrigger();
    }

    public boolean isAutoRepeat() {
        return autoRepeat;
    }

    public void setAutoRepeat(boolean autoRepeat) {
        firePropertyChange(KEY_AUTO_REPEAT, this.autoRepeat, this.autoRepeat = autoRepeat);
    }

    public boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(boolean checkable) {
        firePropertyChange(KEY_CHECKABLE, this.checkable, this.checkable = checkable);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        firePropertyChange(KEY_CHECKED, this.checked, this.checked = checked);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        firePropertyChange(KEY_ICON, this.icon, this.icon = icon);
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        firePropertyChange(KEY_ICON_TEXT, this.iconText, this.iconText = iconText);
    }

    public boolean getIconVisibleInMenu() {
        return iconVisibleInMenu;
    }

    public void setIconVisibleInMenu(boolean iconVisibleInMenu) {
        firePropertyChange(KEY_ICON_VISIBLE_IN_MENU, this.iconVisibleInMenu, this.iconVisibleInMenu = iconVisibleInMenu);
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        firePropertyChange(KEY_SHORTCUT, this.shortcut, this.shortcut = shortcut);
    }

    public String getStatusTip() {
        return statusTip;
    }

    public void setStatusTip(String statusTip) {
        firePropertyChange(KEY_STATUS_TIP, this.statusTip, this.statusTip = statusTip);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        firePropertyChange(KEY_TEXT, this.text, this.text = text);
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        firePropertyChange(KEY_TOOLTIP, this.toolTip, this.toolTip = toolTip);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        firePropertyChange(KEY_VISIBLE, this.visible, this.visible = visible);
    }

    public String getWhatsThis() {
        return whatsThis;
    }

    public void setWhatsThis(String whatsThis) {
        firePropertyChange(KEY_WHATS_THIS, this.whatsThis, this.whatsThis = whatsThis);
    }

}
