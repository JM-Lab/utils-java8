
package kr.jm.utils.enums;

import kr.jm.utils.helper.JMString;

import java.text.DecimalFormat;
import java.util.stream.Stream;

/**
 * The Enum SimpleSIUnit.
 */
public enum SimpleSIUnit {

    /**
     * K simple si unit.
     */
    K(1), /**
     * M simple si unit.
     */
    M(2), /**
     * G simple si unit.
     */
    G(3), /**
     * T simple si unit.
     */
    T(4), /**
     * P simple si unit.
     */
    P(5), /**
     * E simple si unit.
     */
    E(6);

    /**
     * The Constant BASE_SIZE.
     */
    public static final int BASE_SIZE = 1024;
	private double min;
	private double max;

	SimpleSIUnit(int power) {
		this.min = Math.pow(BASE_SIZE, power);
		this.max = this.min * BASE_SIZE;
	}

    /**
     * Find SI unit and convert to string.
     *
     * @param size          the size
     * @param decimalFormat the decimal formatted
     * @param suffix        the suffix
     * @return the string
     */
    public static String findSIUnitAndConvertToString(long size,
			DecimalFormat decimalFormat, String suffix) {
		return Stream.of(SimpleSIUnit.values())
				.filter(si -> values()[0].min < size)
				.filter(si -> size < si.max)
				.map(si -> si.convertToString(size, decimalFormat, suffix))
				.findFirst()
				.orElseGet(() -> decimalFormat.format(size) + suffix);
	}

    /**
     * Convert.
     *
     * @param number the number
     * @return the double
     */
    public double convert(Number number) {
		return number.doubleValue() / min;
	}

    /**
     * Append SI unit.
     *
     * @param target the target
     * @param suffix the suffix
     * @return the string
     */
    public String appendSIUnit(String target, String suffix) {
		return target + JMString.SPACE + name() + suffix;
	}

    /**
     * Convert to string.
     *
     * @param size   the size
     * @param suffix the suffix
     * @return the string
     */
    public String convertToString(Number size, String suffix) {
		return appendSIUnit(Double.toString(convert(size)), suffix);
	}

    /**
     * Convert to string.
     *
     * @param size          the size
     * @param decimalFormat the decimal formatted
     * @param suffix        the suffix
     * @return the string
     */
    public String convertToString(Number size, DecimalFormat decimalFormat,
			String suffix) {
		return appendSIUnit(decimalFormat.format(convert(size)), suffix);
	}

}
