package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMLambda;
import kr.jm.utils.helper.JMStream;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Index alias map.
 *
 * @param <V> the type parameter
 */
public class IndexAliasMap<V> implements Map<String, V> {

    private static final Logger log =
            org.slf4j.LoggerFactory.getLogger(IndexAliasMap.class);
    private final List<V> dataList;
    private Map<String, Integer> aliasIndexMap;

    /**
     * Instantiates a new Index alias map.
     *
     * @param dataList the data list
     */
    public IndexAliasMap(List<V> dataList) {
        this(null, dataList);
    }

    /**
     * Instantiates a new Index alias map.
     *
     * @param aliasList the alias list
     * @param dataList  the data list
     */
    public IndexAliasMap(List<String> aliasList, List<V> dataList) {
        this.dataList = new ArrayList<>(dataList);
        this.aliasIndexMap = new HashMap<>(size());
        if (aliasList != null) addAllWithAlias(aliasList, dataList);
    }

    /**
     * Add all with alias.
     *
     * @param aliasList the alias list
     * @param dataList  the data list
     */
    public void addAllWithAlias(List<String> aliasList, List<V> dataList) {
        synchronized (dataList) {
            JMLambda.runByBoolean(aliasList.size() == dataList.size(),
                    () -> JMStream.numberRangeWithCount(0, 1, dataList.size())
                            .forEach(i -> addWithAlias(aliasList.get(i),
                                    dataList.get(i))),
                    () -> JMExceptionManager.logRuntimeException(log,
                            "Wrong Key List Size !!! - " +
                                    "dataList Size = " + size() +
                                    " aliasList Size = " +
                                    aliasList.size(),
                            "setKeyIndexMap", aliasList));
        }
    }

    /**
     * Add with alias integer.
     *
     * @param alias the alias
     * @param value the value
     * @return the integer
     */
    public Integer addWithAlias(String alias, V value) {
        synchronized (dataList) {
            dataList.add(value);
            return aliasIndexMap.put(alias, dataList.size() - 1);
        }
    }

    /**
     * Put alias.
     *
     * @param alias the alias
     * @param index the index
     */
    synchronized public void putAlias(String alias, int index) {
        JMLambda.runByBoolean(index < size(),
                () -> aliasIndexMap.put(alias, index),
                () -> JMExceptionManager.logRuntimeException(log,
                        "Wrong Index !!! - " + "dataList Size = " + dataList,
                        "setKeyIndexMap", index));
    }

    @Override
    public V put(String key, V value) {
        return dataList.get(addWithAlias(key, value));
    }

    /**
     * Get v.
     *
     * @param index the index
     * @return the v
     */
    public V get(int index) {
        return dataList.get(index);
    }


    @Override
    public int size() {return dataList.size();}

    @Override
    public boolean isEmpty() {return dataList.isEmpty();}

    @Override
    public boolean containsKey(Object key) {
        return aliasIndexMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return dataList.contains(value);
    }

    @Override
    public V get(Object key) {return dataList.get(aliasIndexMap.get(key));}


    @Override
    public V remove(Object key) {
        return dataList.remove(aliasIndexMap.remove(key).intValue());
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        List<String> keyList = new ArrayList<>(m.keySet());
        addAllWithAlias(keyList,
                keyList.stream().map(m::get).collect(Collectors.toList()));
    }

    @Override
    public void clear() {
        synchronized (dataList) {
            dataList.clear();
            aliasIndexMap.clear();
        }
    }

    @Override
    public Set<String> keySet() {return aliasIndexMap.keySet();}

    @Override
    public Collection<V> values() {return new ArrayList<>(dataList);}

    @Override
    public Set<Entry<String, V>> entrySet() {
        return JMMap.newChangedValueMap(aliasIndexMap, dataList::get)
                .entrySet();
    }

}
