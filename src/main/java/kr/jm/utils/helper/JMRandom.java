package kr.jm.utils.helper;

import java.util.Random;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public class JMRandom {

    public static int getBoundedNumber(int inclusiveLowerBound,
            int exclusiveUpperBound) {
        return getBoundedNumber(new Random(), inclusiveLowerBound,
                exclusiveUpperBound);
    }

    public static int getBoundedNumber(long seed, int inclusiveLowerBound,
            int exclusiveUpperBound) {
        return getBoundedNumber(new Random(seed), inclusiveLowerBound,
                exclusiveUpperBound);
    }

    public static int getBoundedNumber(Random random, int inclusiveLowerBound,
            int exclusiveUpperBound) {
        return random.nextInt(exclusiveUpperBound - inclusiveLowerBound) +
                inclusiveLowerBound;
    }

    public static IntStream buildRandomIntStream(int streamSize,
            int inclusiveLowerBound, int exclusiveUpperBound) {
        return buildRandomIntStream(streamSize, new Random(),
                inclusiveLowerBound, exclusiveUpperBound);
    }

    public static IntStream buildRandomIntStream(int streamSize, long seed,
            int inclusiveLowerBound, int exclusiveUpperBound) {
        return buildRandomIntStream(streamSize, new Random(seed),
                inclusiveLowerBound, exclusiveUpperBound);
    }

    public static IntStream buildRandomIntStream(int streamSize, Random random,
            int inclusiveLowerBound, int exclusiveUpperBound) {
        return buildRandomIntStream(streamSize,
                () -> getBoundedNumber(random, inclusiveLowerBound,
                        exclusiveUpperBound));
    }

    public static void foreachRandomInt(int streamSize, int inclusiveLowerBound,
            int exclusiveUpperBound, IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(), inclusiveLowerBound,
                exclusiveUpperBound, eachRandomIntConsumer);
    }

    public static void foreachRandomInt(int streamSize, long seed,
            int inclusiveLowerBound, int exclusiveUpperBound,
            IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(seed), inclusiveLowerBound,
                exclusiveUpperBound, eachRandomIntConsumer);
    }

    public static void foreachRandomInt(int streamSize, Random random,
            int inclusiveLowerBound, int exclusiveUpperBound,
            IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize,
                () -> getBoundedNumber(random, inclusiveLowerBound,
                        exclusiveUpperBound), eachRandomIntConsumer);
    }

    public static IntStream buildRandomIntStream(int streamSize,
            int exclusiveBound) {
        return buildRandomIntStream(streamSize, new Random(),
                exclusiveBound);
    }

    public static IntStream buildRandomIntStream(int streamSize,
            long seed, int exclusiveBound) {
        return buildRandomIntStream(streamSize, new Random(seed),
                exclusiveBound);
    }

    public static IntStream buildRandomIntStream(int streamSize, Random random,
            int exclusiveBound) {
        return buildRandomIntStream(streamSize,
                () -> random.nextInt(exclusiveBound));
    }

    public static void foreachRandomInt(int streamSize, int exclusiveBound,
            IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(), exclusiveBound,
                eachRandomIntConsumer);
    }

    public static void foreachRandomInt(int streamSize, long seed,
            int exclusiveBound, IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, new Random(seed), exclusiveBound,
                eachRandomIntConsumer);
    }

    public static void foreachRandomInt(int streamSize, Random random,
            int exclusiveBound, IntConsumer eachRandomIntConsumer) {
        foreachRandomInt(streamSize, () -> random.nextInt(exclusiveBound),
                eachRandomIntConsumer);
    }

    public static IntStream buildRandomIntStream(int streamSize,
            IntSupplier randomIntSupplier) {
        return JMStream.increaseRange(streamSize)
                .map(i -> randomIntSupplier.getAsInt());
    }

    public static void foreachRandomInt(int streamSize,
            IntSupplier randomIntSupplier, IntConsumer eachRandomIntConsumer) {
        buildRandomIntStream(streamSize, randomIntSupplier)
                .forEach(eachRandomIntConsumer);
    }
}
