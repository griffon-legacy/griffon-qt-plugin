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

import griffon.core.GriffonApplication;
import griffon.core.GriffonController;
import griffon.core.controller.GriffonControllerAction;
import griffon.core.i18n.NoSuchMessageException;
import org.codehaus.griffon.runtime.core.controller.AbstractGriffonControllerActionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static griffon.util.GriffonApplicationUtils.isMacOSX;
import static griffon.util.GriffonNameUtils.capitalize;
import static griffon.util.GriffonNameUtils.isBlank;
import static org.codehaus.griffon.runtime.qt.QtGriffonControllerAction.*;
import static org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation.castToBoolean;

/**
 * @author Andres Almiray
 */
public class QtGriffonControllerActionManager extends AbstractGriffonControllerActionManager {
    private static final Logger LOG = LoggerFactory.getLogger(QtGriffonControllerActionManager.class);

    protected QtGriffonControllerActionManager(GriffonApplication app) {
        super(app);
    }

    @Override
    protected GriffonControllerAction createControllerAction(GriffonController controller, String actionName) {
        return new QtGriffonControllerAction(controller, actionName);
    }

    @Override
    protected GriffonControllerAction createAndConfigureAction(GriffonController controller, String actionName) {
        QtGriffonControllerAction action = (QtGriffonControllerAction) createControllerAction(controller, actionName);

        String normalizeNamed = capitalize(normalizeName(actionName));
        String keyPrefix = controller.getClass().getName() + ".action.";

        String rsAutoRepeat = message(keyPrefix, normalizeNamed, KEY_AUTO_REPEAT, "false");
        if (!isBlank(rsAutoRepeat)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_AUTO_REPEAT + " = " + rsAutoRepeat);
            }
            action.setAutoRepeat(castToBoolean(rsAutoRepeat));
        }

        String rsCheckable = message(keyPrefix, normalizeNamed, KEY_CHECKABLE, "false");
        if (!isBlank(rsCheckable)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_CHECKABLE + " = " + rsCheckable);
            }
            action.setCheckable(castToBoolean(rsCheckable));
        }

        String rsChecked = message(keyPrefix, normalizeNamed, KEY_CHECKED, "false");
        if (!isBlank(rsChecked)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_CHECKED + " = " + rsChecked);
            }
            action.setChecked(castToBoolean(rsChecked));
        }

        String rsShortcut = message(keyPrefix, normalizeNamed, KEY_SHORTCUT, "").toUpperCase();
        if (!isBlank(rsShortcut)) {
            if (!isMacOSX() && rsShortcut.contains("META") && !rsShortcut.contains("CTRL")) {
                rsShortcut = rsShortcut.replace("META", "CTRL");
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_SHORTCUT + " = " + rsShortcut);
            }
            action.setShortcut(rsShortcut);
        }

        String rsEnabled = message(keyPrefix, normalizeNamed, KEY_ENABLED, "true");
        if (!isBlank(rsEnabled)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_ENABLED + " = " + rsEnabled);
            }
            action.setEnabled(castToBoolean(rsEnabled));
        }

        String rsIcon = message(keyPrefix, normalizeNamed, KEY_ICON, "").toUpperCase();
        if (!isBlank(rsIcon)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_ICON + " = " + rsIcon);
            }
            action.setIcon(rsIcon);
        }

        String rsIconText = message(keyPrefix, normalizeNamed, KEY_ICON_TEXT, "").toUpperCase();
        if (!isBlank(rsIconText)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_ICON_TEXT + " = " + rsIconText);
            }
            action.setIconText(rsIconText);
        }

        String rsIconVisibleInMenu = message(keyPrefix, normalizeNamed, KEY_ICON_VISIBLE_IN_MENU, "true");
        if (!isBlank(rsIconVisibleInMenu)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_ICON_VISIBLE_IN_MENU + " = " + rsIconVisibleInMenu);
            }
            action.setIconVisibleInMenu(castToBoolean(rsIconVisibleInMenu));
        }

        String rsStatusTip = message(keyPrefix, normalizeNamed, KEY_STATUS_TIP, "").toUpperCase();
        if (!isBlank(rsStatusTip)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_STATUS_TIP + " = " + rsStatusTip);
            }
            action.setStatusTip(rsStatusTip);
        }

        String rsText = message(keyPrefix, normalizeNamed, KEY_TEXT, "").toUpperCase();
        if (!isBlank(rsText)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_TEXT + " = " + rsText);
            }
            action.setIconText(rsText);
        }

        String rsToolTip = message(keyPrefix, normalizeNamed, KEY_TOOLTIP, "").toUpperCase();
        if (!isBlank(rsToolTip)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_TOOLTIP + " = " + rsToolTip);
            }
            action.setToolTip(rsToolTip);
        }

        String rsVisible = message(keyPrefix, normalizeNamed, KEY_VISIBLE, "true");
        if (!isBlank(rsVisible)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_VISIBLE + " = " + rsVisible);
            }
            action.setVisible(castToBoolean(rsVisible));
        }

        String rsWhatsThis = message(keyPrefix, normalizeNamed, KEY_WHATS_THIS, "").toUpperCase();
        if (!isBlank(rsWhatsThis)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(keyPrefix + normalizeNamed + "." + KEY_WHATS_THIS + " = " + rsWhatsThis);
            }
            action.setWhatsThis(rsWhatsThis);
        }

        return action;
    }

    private String message(String key, String actionName, String subkey, String defaultValue) {
        try {
            return getApp().getMessage(key + actionName + "." + subkey);
        } catch (NoSuchMessageException nsme) {
            return getApp().getMessage("application.action." + actionName + "." + subkey, defaultValue);
        }
    }
}
