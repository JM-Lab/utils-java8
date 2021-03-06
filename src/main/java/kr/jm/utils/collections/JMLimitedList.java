package kr.jm.utils.collections;

import java.util.*;

import static kr.jm.utils.helper.JMOptional.getOptional;
import static kr.jm.utils.helper.JMPredicate.*;

/**
 * The type Jm limited list.
 *
 * @param <E> the type parameter
 */
public class JMLimitedList<E> implements Collection<E> {

    private final LinkedList<E> linkedList;
    private int currentIndex;
    private int capacity;

    /**
     * Instantiates a new Jm limited list.
     *
     * @param capacity the capacity
     */
    public JMLimitedList(int capacity) {
        this.currentIndex = -1;
        this.capacity = capacity;
        this.linkedList = new LinkedList<>();
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets first.
     *
     * @return the first
     */
    public Optional<E> getFirst() {
        return getOptional(linkedList).map(LinkedList::getFirst);
    }

    /**
     * Gets last.
     *
     * @return the last
     */
    public Optional<E> getLast() {
        return getOptional(linkedList).map(LinkedList::getLast);
    }

    /**
     * Get optional.
     *
     * @param index the index
     * @return the optional
     */
    public Optional<E> get(int index) {
        return getOptional(linkedList)
                .filter(getGreaterSize(0).and(getGreaterSize(index)))
                .map(l -> l.get(index));
    }

    /**
     * Gets current.
     *
     * @return the current
     */
    public Optional<E> getCurrent() {
        return get(currentIndex);
    }

    /**
     * Sub list list.
     *
     * @param fromIndex the from index
     * @param toIndex   the to index
     * @return the list
     */
    public List<E> subList(int fromIndex, int toIndex) {
        return linkedList.subList(fromIndex, toIndex);
    }

    /**
     * Gets current index.
     *
     * @return the current index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Gets previous.
     *
     * @return the previous
     */
    public Optional<E> getPrevious() {
        return Optional.of(currentIndex).filter(getGreater(0))
                .map(i -> linkedList.get(--currentIndex));
    }

    /**
     * Gets next.
     *
     * @return the next
     */
    public Optional<E> getNext() {
        return Optional.of(currentIndex).filter(getLess(linkedList.size() - 1))
                .map(i -> linkedList.get(++currentIndex));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
        synchronized (linkedList) {
            if (capacity <= linkedList.size())
                linkedList.remove();
            return addAndChangeCurrentIndex(e);
        }
    }

    private boolean addAndChangeCurrentIndex(E e) {
        linkedList.add(e);
        initCurrentIndex();
        return true;
    }

    private void initCurrentIndex() {
        this.currentIndex = linkedList.size() - 1;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#size()
     */
    @Override
    public int size() {
        return linkedList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return linkedList.iterator();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#buildStringArray()
     */
    @Override
    public Object[] toArray() {
        return linkedList.toArray();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#buildStringArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return linkedList.toArray(a);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        synchronized (linkedList) {
            initCurrentIndex();
            return linkedList.remove(o);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return linkedList.containsAll(c);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return c.size() + this.size() <= capacity && this.addAll(c);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (linkedList) {
            initCurrentIndex();
            return linkedList.removeAll(c);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return linkedList.retainAll(c);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#clear()
     */
    @Override
    public void clear() {
        synchronized (linkedList) {
            initCurrentIndex();
            linkedList.clear();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        return linkedList.equals(o);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return linkedList.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return linkedList.toString();
    }
}
