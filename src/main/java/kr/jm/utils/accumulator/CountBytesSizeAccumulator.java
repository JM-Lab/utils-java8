package kr.jm.utils.accumulator;

import java.util.concurrent.atomic.LongAdder;

/**
 * The type Count bytes size accumulator.
 */
public class CountBytesSizeAccumulator {

    private LongAdder count;
    private LongAdder bytesSize;

    /**
     * Instantiates a new Count bytes size accumulator.
     */
    public CountBytesSizeAccumulator() {
        this.count = new LongAdder();
        this.bytesSize = new LongAdder();
    }

    /**
     * Reset.
     */
    synchronized public void reset() {
        this.count.reset();
        this.bytesSize.reset();
    }

    /**
     * Increase count.
     */
    public void increaseCount() {
        this.count.increment();
    }

    /**
     * Accumulate count.
     *
     * @param count the count
     */
    public void accumulateCount(int count) {
        this.count.add(count);
    }

    /**
     * Accumulate bytes size.
     *
     * @param bytesSize the bytes size
     */
    public void accumulateBytesSize(int bytesSize) {
        this.bytesSize.add(bytesSize);
    }

    /**
     * Accumulate.
     *
     * @param count     the count
     * @param bytesSize the bytes size
     */
    synchronized public void accumulate(int count, int bytesSize) {
        accumulateCount(count);
        accumulateBytesSize(bytesSize);
    }

    /**
     * Increase count accumulate bytes.
     *
     * @param bytesSize the bytes size
     */
    synchronized public void increaseCountAccumulateBytes(int bytesSize) {
        increaseCount();
        accumulateBytesSize(bytesSize);
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public long getCount() {
        return this.count.longValue();
    }

    /**
     * Gets bytes size.
     *
     * @return the bytes size
     */
    public long getBytesSize() {
        return this.bytesSize.longValue();
    }
}
