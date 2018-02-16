package kr.jm.utils.helper.object;

import kr.jm.utils.datastructure.JMArrays;
import kr.jm.utils.helper.JMLambda;

import java.util.Arrays;
import java.util.List;

/**
 * The type Abstract objects.
 */
public abstract class AbstractObjects {

    /**
     * The Objects.
     */
    protected Object[] objects;

    /**
     * To array object [ ].
     *
     * @return the object [ ]
     */
    public Object[] toArray() {
        return JMLambda.supplierIfNull(objects, this::initObjects);
    }

    private Object[] initObjects() {
        return this.objects = newObjects();
    }

    /**
     * New objects object [ ].
     *
     * @return the object [ ]
     */
    abstract protected Object[] newObjects();

    /**
     * To string array string [ ].
     *
     * @return the string [ ]
     */
    public String[] toStringArray() {
        return JMArrays.buildStringArray(toArray());
    }

    /**
     * To list list.
     *
     * @return the list
     */
    public List<Object> toList() {
        return Arrays.asList(toArray());
    }

    /**
     * Gets with.
     *
     * @param index the index
     * @return the with
     */
    public Object getWith(int index) {
        return toArray()[index];
    }

}
