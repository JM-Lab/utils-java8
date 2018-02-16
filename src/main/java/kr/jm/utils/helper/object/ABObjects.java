package kr.jm.utils.helper.object;

import kr.jm.utils.datastructure.JMArrays;

/**
 * The type Ab objects.
 *
 * @param <A> the type parameter
 * @param <B> the type parameter
 */
public class ABObjects<A, B> extends AbstractObjects {
    /**
     * The A.
     */
    protected A a;
    /**
     * The B.
     */
    protected B b;

    /**
     * Instantiates a new Ab objects.
     *
     * @param a the a
     * @param b the b
     */
    public ABObjects(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Gets a.
     *
     * @return the a
     */
    public A getA() {
        return a;
    }

    /**
     * Gets b.
     *
     * @return the b
     */
    public B getB() {
        return b;
    }


    @Override
    protected Object[] newObjects() {
        return JMArrays.buildObjectArray(a, b);
    }

    @Override
    public String toString() {
        return "ABObjects{" + "a=" + a + ", b=" + b + '}';
    }
}
