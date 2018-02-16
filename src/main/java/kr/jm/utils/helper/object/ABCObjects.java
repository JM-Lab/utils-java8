package kr.jm.utils.helper.object;

import kr.jm.utils.datastructure.JMArrays;

/**
 * The type Abc objects.
 *
 * @param <A> the type parameter
 * @param <B> the type parameter
 * @param <C> the type parameter
 */
public class ABCObjects<A, B, C> extends ABObjects<A, B> {
    /**
     * The C.
     */
    protected C c;

    /**
     * Instantiates a new Abc objects.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     */
    public ABCObjects(A a, B b, C c) {
        super(a, b);
        this.c = c;
    }

    /**
     * Gets c.
     *
     * @return the c
     */
    public C getC() {
        return c;
    }

    @Override
    protected Object[] newObjects() {
        return JMArrays.buildObjectArray(a, b, c);
    }

    @Override
    public String toString() {
        return "ABCObjects{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
    }
}
