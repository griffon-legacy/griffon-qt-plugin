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

package griffon.qt.factory

import com.trolltech.qt.gui.QLayout
import com.trolltech.qt.gui.QWidget

/**
 * @author Andres Almiray
 */
class QLayoutFactory extends QObjectFactory {
    public static final String DELEGATE_PROPERTY_CONSTRAINT = "_delegateProperty:Constrinat";
    public static final String DEFAULT_DELEGATE_PROPERTY_CONSTRAINT = "constraints";

    QLayoutFactory(Class layoutClass) {
        super(layoutClass)
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
        builder.context[DELEGATE_PROPERTY_CONSTRAINT] = attributes.remove("constraintsProperty") ?: DEFAULT_DELEGATE_PROPERTY_CONSTRAINT
        Object layout = super.newInstance(builder, name, value, attributes)
        layout
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        handleChildWidget(builder, parent, child)
    }

    static void handleChildWidget(FactoryBuilderSupport builder, QLayout layout, Object child) {
        if (!(child instanceof QWidget)) return
        try {
            def constraints = builder.context.constraints
            if (constraints != null) {
                if (constraints instanceof List) {
                    layout.addWidget(child, * constraints)
                } else {
                    layout.addWidget(child, constraints)
                }
                builder.context.remove('constraints')
            } else {
                layout.addWidget(child)
            }
        } catch (MissingPropertyException mpe) {
            layout.addWidget(child)
        }
    }

    static constraintsAttributeDelegate(def builder, def node, def attributes) {
        def constraintsAttr = builder?.context?.getAt(DELEGATE_PROPERTY_CONSTRAINT) ?: DEFAULT_DELEGATE_PROPERTY_CONSTRAINT
        if (attributes.containsKey(constraintsAttr)) {
            builder.context.constraints = attributes.remove(constraintsAttr)
        }
    }
}
