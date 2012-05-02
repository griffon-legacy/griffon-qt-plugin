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

import com.trolltech.qt.QSignalEmitter;
import groovy.lang.Closure;

/**
 * @author Andres Almiray
 */
public class Signals {
    private Signals() {
    }

    public static void connect(QSignalEmitter.AbstractSignal qsignal, Closure closure) {
        Signal signal = signalFor(closure);
        if (qsignal instanceof QSignalEmitter.Signal0) {
            signal = new Signal0(closure);
        }
        qsignal.connect(signal, signal.methodName());
    }

    private static Signal signalFor(Closure closure) {
        switch (closure.getMaximumNumberOfParameters()) {
            case 0:
                return new Signal0(closure);
            case 1:
                return new Signal1(closure);
            case 2:
                return new Signal2(closure);
            case 3:
                return new Signal3(closure);
            case 4:
                return new Signal4(closure);
            case 5:
                return new Signal5(closure);
            case 6:
                return new Signal6(closure);
            case 7:
                return new Signal7(closure);
            case 8:
                return new Signal8(closure);
            case 9:
                return new Signal9(closure);
            default:
                throw new IllegalArgumentException("Can't build a signal with " + closure.getMaximumNumberOfParameters() + "parameters");
        }
    }

    private static abstract class Signal {
        protected abstract int argCount();

        protected String methodName() {
            StringBuilder b = new StringBuilder("call(");
            boolean first = true;

            for (int i = 0; i < argCount(); i++) {
                if (first) {
                    first = false;
                } else {
                    b.append(",");
                }
                b.append("Object");
            }
            b.append(")");

            return b.toString();
        }
    }

    public static final class Signal0 extends Signal {
        private final Closure closure;

        public Signal0(Closure closure) {
            this.closure = closure;
        }

        public void call() {
            closure.call();
        }

        protected int argCount() {
            return 0;
        }
    }

    public static final class Signal1<A> extends Signal {
        private final Closure closure;

        public Signal1(Closure closure) {
            this.closure = closure;
        }

        public void call(A a) {
            closure.call(a);
        }

        protected int argCount() {
            return 1;
        }
    }

    public static final class Signal2<A, B> extends Signal {
        private final Closure closure;

        public Signal2(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b) {
            closure.call(a, b);
        }

        protected int argCount() {
            return 2;
        }
    }

    public static final class Signal3<A, B, C> extends Signal {
        private final Closure closure;

        public Signal3(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c) {
            closure.call(a, b, c);
        }

        protected int argCount() {
            return 3;
        }
    }

    public static final class Signal4<A, B, C, D> extends Signal {
        private final Closure closure;

        public Signal4(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c, D d) {
            closure.call(a, b, c, d);
        }

        protected int argCount() {
            return 4;
        }
    }

    public static final class Signal5<A, B, C, D, E> extends Signal {
        private final Closure closure;

        public Signal5(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c, D d, E e) {
            closure.call(a, b, c, d, e);
        }

        protected int argCount() {
            return 5;
        }
    }

    public static final class Signal6<A, B, C, D, E, F> extends Signal {
        private final Closure closure;

        public Signal6(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c, D d, E e, F f) {
            closure.call(a, b, c, d, e, f);
        }

        protected int argCount() {
            return 6;
        }
    }

    public static final class Signal7<A, B, C, D, E, F, G> extends Signal {
        private final Closure closure;

        public Signal7(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c, D d, E e, F f, G g) {
            closure.call(a, b, c, d, e, f, g);
        }

        protected int argCount() {
            return 7;
        }
    }

    public static final class Signal8<A, B, C, D, E, F, G, H> extends Signal {
        private final Closure closure;

        public Signal8(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c, D d, E e, F f, G g, H h) {
            closure.call(a, b, c, d, e, f, g, h);
        }

        protected int argCount() {
            return 8;
        }
    }

    public static final class Signal9<A, B, C, D, E, F, G, H, I> extends Signal {
        private final Closure closure;

        public Signal9(Closure closure) {
            this.closure = closure;
        }

        public void call(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            closure.call(a, b, c, d, e, f, g, h, i);
        }

        protected int argCount() {
            return 9;
        }
    }
}
