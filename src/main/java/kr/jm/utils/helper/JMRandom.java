package kr.jm.utils.helper;

import java.util.Random;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * The type Jm random.
 */
public class JMRandom {

    /**
     * Gets bounded number.
     *
     * @param inclusiveLowerBound the inclusive lower bound
     * @param exclusiveUpperBound the exclusive upper bound
     * @return the bounded number
     */
    public static int getBoundedNumber(int inclusiveLowerBound,
            int exclusiveUpperBound) {
        return getBoundedNumber(new Random(), inclusiveLowerBound,
                exclusiveUpperBound);
    }

    /**
     * Gets bounded number.
     *
     * @param seed                the seed
     * @param inclusiveLowerBound the inclusive lower bound
     * @param exclusiveUpperBound the exclusive upper bound
     * @return the bounded number
     */
    public static int getBoundedNumber(long seed, int inclusiveLowerBound,
            int exclusiveUpperBound) {
        return getBoundedNumber(new Random(seed), inclusiveLowerBound,
                exclusiveUpperBound);
    }

    /**
     * Gets bounded number.
     *
     * @param random              the random
     * @param inclusiveLowerBound the inclusive lower bound
     * @param exclusiveUpperBound the exclusive upper bound
     * @return the bounded number
     */
    public static int getBoundedNumber(Random random, int inclusiveLowerBound,
            int exclusiveUpperBound) {
        return random.nextInt(exclusiveUpperBound - inclusiveLowerBound) +
                inclusiveLowerBound;
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize          the stream size
     * @param inclusiveLowerBound the inclusive lower bound
     * @param exclusiveUpperBound the exclusive upper bound
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize,
            int inclusiveLowerBound, int exclusiveUpperBound) {
        return buildRandomIntStream(streamSize, new Random(),
                inclusiveLowerBound, exclusiveUpperBound);
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize          the stream size
     * @param seed                the seed
     * @param inclusiveLowerBound the inclusive lower bound
     * @param exclusiveUpperBound the exclusive upper bound
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize, long seed,
            int inclusiveLowerBound, int exclusiveUpperBound) {
        return buildRandomIntStream(streamSize, new Random(seed),
                inclusiveLowerBound, exclusiveUpperBound);
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize          the stream size
     * @param random              the random
     * @param inclusiveLowerBound the inclusive lower bound
     * @param exclusiveUpperBound the exclusive upper bound
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize, Random random,
            int inclusiveLowerBound, int exclusiveUpperBound) {
        return buildRandomIntStream(streamSize,
                () -> getBoundedNumber(random, inclusiveLowerBound,
                        exclusiveUpperBound));
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param inclusiveLowerBound   the inclusive lower bound
     * @param exclusiveUpperBound   the exclusive upper bound
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize, int inclusiveLowerBound,
            int exclusiveUpperBound, IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(), inclusiveLowerBound,
                exclusiveUpperBound, eachRandomIntConsumer);
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param seed                  the seed
     * @param inclusiveLowerBound   the inclusive lower bound
     * @param exclusiveUpperBound   the exclusive upper bound
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize, long seed,
            int inclusiveLowerBound, int exclusiveUpperBound,
            IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(seed), inclusiveLowerBound,
                exclusiveUpperBound, eachRandomIntConsumer);
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param random                the random
     * @param inclusiveLowerBound   the inclusive lower bound
     * @param exclusiveUpperBound   the exclusive upper bound
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize, Random random,
            int inclusiveLowerBound, int exclusiveUpperBound,
            IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize,
                () -> getBoundedNumber(random, inclusiveLowerBound,
                        exclusiveUpperBound), eachRandomIntConsumer);
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize     the stream size
     * @param exclusiveBound the exclusive bound
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize,
            int exclusiveBound) {
        return buildRandomIntStream(streamSize, new Random(),
                exclusiveBound);
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize     the stream size
     * @param seed           the seed
     * @param exclusiveBound the exclusive bound
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize,
            long seed, int exclusiveBound) {
        return buildRandomIntStream(streamSize, new Random(seed),
                exclusiveBound);
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize     the stream size
     * @param random         the random
     * @param exclusiveBound the exclusive bound
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize, Random random,
            int exclusiveBound) {
        return buildRandomIntStream(streamSize,
                () -> random.nextInt(exclusiveBound));
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param exclusiveBound        the exclusive bound
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize, int exclusiveBound,
            IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(), exclusiveBound,
                eachRandomIntConsumer);
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param seed                  the seed
     * @param exclusiveBound        the exclusive bound
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize, long seed,
            int exclusiveBound, IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(seed), exclusiveBound,
                eachRandomIntConsumer);
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param random                the random
     * @param exclusiveBound        the exclusive bound
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize, Random random,
            int exclusiveBound, IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, () -> random.nextInt(exclusiveBound),
                eachRandomIntConsumer);
    }

    /**
     * Build random int stream int stream.
     *
     * @param streamSize        the stream size
     * @param randomIntSupplier the random int supplier
     * @return the int stream
     */
    public static IntStream buildRandomIntStream(int streamSize,
            IntSupplier randomIntSupplier) {
        return JMStream.increaseRange(streamSize)
                .map(i -> randomIntSupplier.getAsInt());
    }

    /**
     * Foreach random int.
     *
     * @param streamSize            the stream size
     * @param randomIntSupplier     the random int supplier
     * @param eachRandomIntConsumer the each random int consumer
     */
    public static void foreachRandomInt(int streamSize,
            IntSupplier randomIntSupplier, IntConsumer eachRandomIntConsumer) {
        buildRandomIntStream(streamSize, randomIntSupplier)
                .forEach(eachRandomIntConsumer);
    }
}
