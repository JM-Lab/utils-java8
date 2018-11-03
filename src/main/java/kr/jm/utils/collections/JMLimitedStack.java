package kr.jm.utils.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

import static kr.jm.utils.helper.JMOptional.getOptional;

/**
 * The type Jm limited stack.
 *
 * @param <E> the type parameter
 */
public class JMLimitedStack<E> implements Collection<E> {

	private int capacity;
	private LinkedBlockingDeque<E> linkedBlockingDeque;

    /**
     * Instantiates a new Jm limited stack.
     *
     * @param capacity the capacity
     */
    public JMLimitedStack(int capacity) {
		this.capacity = capacity;
		this.linkedBlockingDeque = new LinkedBlockingDeque<>(capacity);
	}

    /**
     * Pop optional.
     *
     * @return the optional
     */
    public Optional<E> pop() {
		return getOptional(linkedBlockingDeque)
				.map(LinkedBlockingDeque::removeLast);
	}

    /**
     * Peek optional.
     *
     * @return the optional
     */
    public Optional<E> peek() {
		return Optional.ofNullable(linkedBlockingDeque.peekLast());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		if (capacity <= linkedBlockingDeque.size())
			linkedBlockingDeque.removeFirst();
		return linkedBlockingDeque.add(e);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return linkedBlockingDeque.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return linkedBlockingDeque.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return linkedBlockingDeque.contains(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return linkedBlockingDeque.iterator();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#buildStringArray()
	 */
	@Override
	public Object[] toArray() {
		return linkedBlockingDeque.toArray();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#buildStringArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return linkedBlockingDeque.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return linkedBlockingDeque.remove(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return linkedBlockingDeque.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return linkedBlockingDeque.addAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return linkedBlockingDeque.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return linkedBlockingDeque.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		linkedBlockingDeque.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return linkedBlockingDeque.equals(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return linkedBlockingDeque.hashCode();
	}

}
