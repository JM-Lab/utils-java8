package kr.jm.utils.helper.object;

import kr.jm.utils.datastructure.JMArrays;

/**
 * The type Abcd objects.
 *
 * @param <A> the type parameter
 * @param <B> the type parameter
 * @param <C> the type parameter
 * @param <D> the type parameter
 */
public class ABCDObjects<A, B, C, D> extends ABCObjects<A, B, C> {
    /**
     * The D.
     */
    protected D d;

    /**
     * Instantiates a new Abcd objects.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     * @param d the d
     */
    public ABCDObjects(A a, B b, C c, D d) {
        super(a, b, c);
        this.d = d;
    }

    /**
     * Gets d.
     *
     * @return the d
     */
    public D getD() {
        return d;
    }

    @Override
    protected Object[] newObjects() {
        return JMArrays.buildObjectArray(a, b, c, d);
    }

    @Override
    public String toString() {
        return "ABCDObjects{" + "a=" + a + ", b=" + b + ", c=" + c + ", d=" +
                d + '}';
    }
}
