package kr.jm.utils.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static kr.jm.utils.datastructure.JMCollections.sort;
import static kr.jm.utils.helper.JMLambda.countBy;

/**
 * The type String counter.
 */
public class StringCounter {
    private final List<String> stringList;

    private StringCounter() {
        this.stringList = new ArrayList<>();
    }

    /**
     * Of string counter.
     *
     * @return the string counter
     */
    public static StringCounter of() {
        return new StringCounter();
    }

    /**
     * Gets count map.
     *
     * @param stringCollection the string collection
     * @return the count map
     */
    public static Map<String, Long> getCountMap(
            Collection<String> stringCollection) {
        return unmodifiableMap(countBy(stringCollection));
    }

    /**
     * Gets sorted string list.
     *
     * @param stringCollection the string collection
     * @return the sorted string list
     */
    public static List<String> getSortedStringList(
            Collection<String> stringCollection) {
        return unmodifiableList(sort(new ArrayList<>(stringCollection)));
    }

    /**
     * Merge string counter.
     *
     * @param other the other
     * @return the string counter
     */
    public StringCounter merge(StringCounter other) {
        return addAll(other.stringList);
    }

    /**
     * Add all string counter.
     *
     * @param stringCollection the string collection
     * @return the string counter
     */
    public StringCounter addAll(
            Collection<String> stringCollection) {
        synchronized (this.stringList) {
            this.stringList.addAll(stringCollection);
            return this;
        }
    }

    /**
     * Add string counter.
     *
     * @param string the string
     * @return the string counter
     */
    public StringCounter add(String string) {
        synchronized (this.stringList) {
            this.stringList.add(string);
            return this;
        }
    }

    /**
     * Gets count map.
     *
     * @return the count map
     */
    public Map<String, Long> getCountMap() {
        return getCountMap(this.stringList);
    }

    /**
     * Gets sorted string list.
     *
     * @return the sorted string list
     */
    public List<String> getSortedStringList() {
        return getSortedStringList(this.stringList);
    }

    /**
     * Gets string list.
     *
     * @return the string list
     */
    public List<String> getStringList() {
        return unmodifiableList(this.stringList);
    }

    /**
     * Gets total count.
     *
     * @return the total count
     */
    public long getTotalCount() {
        return this.stringList.size();
    }

    /**
     * Gets unique count.
     *
     * @return the unique count
     */
    public long getUniqueCount() {
        return this.stringList.stream().distinct().count();
    }

    @Override
    public String toString() {
        return "StringCounter{" + "stringList=" + stringList + '}';
    }

}
