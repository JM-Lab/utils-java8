package kr.jm.utils.enums;

import java.text.DecimalFormat;
import java.util.stream.Stream;

import kr.jm.utils.helper.JMString;

public enum SimpleSIUnit {

	k(1), M(2), G(3), T(4), P(5), E(6);

	public static final int BASE_SIZE = 1024;
	private double min;
	private double max;

	private SimpleSIUnit(int power) {
		this.min = Math.pow(BASE_SIZE, power);
		this.max = this.min * BASE_SIZE;
	}

	public static String findSIUnitAndConvertToString(long size,
			DecimalFormat decimalFormat, String suffix) {
		return Stream.of(SimpleSIUnit.values())
				.filter(si -> values()[0].min < size)
				.filter(si -> size < si.max)
				.map(si -> si.convertToString(size, decimalFormat, suffix))
				.findFirst()
				.orElseGet(() -> decimalFormat.format(size) + suffix);
	}

	public double convert(Number number) {
		return number.doubleValue() / min;
	}

	public String appendSIUnit(String target, String suffix) {
		return target + JMString.SPACE + name() + suffix;
	}

	public String convertToString(Number size, String suffix) {
		return appendSIUnit(Double.toString(convert(size)), suffix);
	}

	public String convertToString(Number size, DecimalFormat decimalFormat,
			String suffix) {
		return appendSIUnit(decimalFormat.format(convert(size)), suffix);
	}

}
