package kr.jm.utils.collections;

import static kr.jm.utils.helper.JMLambda.changeInto;
import static kr.jm.utils.helper.JMOptional.getOptional;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Class JMLimitedQueue.
 *
 * @param <E> the element type
 */
public class JMLimitedQueue<E> implements Collection<E> {

	private int capacity;
	private LinkedBlockingQueue<E> linkedBlockingQueue;

	/**
	 * Instantiates a new JM limited queue.
	 *
	 * @param capacity the capacity
	 */
	public JMLimitedQueue(int capacity) {
		this.capacity = capacity;
		this.linkedBlockingQueue = new LinkedBlockingQueue<>(capacity);
	}

	/**
	 * Poll.
	 *
	 * @return the optional
	 */
	public Optional<E> poll() {
		return getOptional(linkedBlockingQueue).map(LinkedBlockingQueue::poll);
	}

	/**
	 * Peek.
	 *
	 * @return the optional
	 */
	public Optional<E> peek() {
		return Optional.ofNullable(linkedBlockingQueue.peek());
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		synchronized (linkedBlockingQueue) {
			return capacity > linkedBlockingQueue.size()
					? linkedBlockingQueue.add(e)
					: Optional.of(linkedBlockingQueue.remove())
							.map(changeInto(linkedBlockingQueue.add(e))).get();
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return linkedBlockingQueue.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return linkedBlockingQueue.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return linkedBlockingQueue.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return linkedBlockingQueue.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return linkedBlockingQueue.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return linkedBlockingQueue.toArray(a);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return linkedBlockingQueue.remove(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return linkedBlockingQueue.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return linkedBlockingQueue.addAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return linkedBlockingQueue.removeAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return linkedBlockingQueue.retainAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		linkedBlockingQueue.clear();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return linkedBlockingQueue.equals(o);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return linkedBlockingQueue.hashCode();
	}

}
