package kr.jm.utils.enums;

import java.text.DecimalFormat;

import org.junit.Test;

public class SimpleSIUnitTest {

	@Test
	public void testConvertToStringToStringWithSIUnit() throws Exception {
		DecimalFormat df = new DecimalFormat("#.#");
		String suffix = "B";
		long kb = 1 * 1024;
		long Mb = kb * 1024;
		long Gb = Mb * 1024;
		long Tb = Gb * 1024;
		long Pb = Tb * 1024;
		long Eb = Pb * 1024;

		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(1, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(kb + 44, df, suffix));
		System.out.println(SimpleSIUnit
				.findSIUnitAndConvertToString(kb * 300 + 1, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Mb, df, suffix));
		System.out.println(SimpleSIUnit
				.findSIUnitAndConvertToString(Mb * 300 + 1, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Mb - 5, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Mb + 1, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Gb, df, suffix));
		System.out.println(SimpleSIUnit
				.findSIUnitAndConvertToString(Gb * 300 + 1, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Tb, df, suffix));
		System.out.println(new Double(Tb - 1) / Tb);
		System.out.println(df.format(new Double(Tb - 1) / Tb));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Tb - 1, df, suffix));
		System.out.println(SimpleSIUnit
				.findSIUnitAndConvertToString(Tb * 300 + 1, df, suffix));
		System.out.println(SimpleSIUnit
				.findSIUnitAndConvertToString(Tb - Gb + Mb * 10, df, suffix));
		System.out.println(
				SimpleSIUnit.findSIUnitAndConvertToString(Eb, df, suffix));
		System.out.println(SimpleSIUnit.findSIUnitAndConvertToString(Eb * 3 + 1,
				df, suffix));
		System.out.println(SimpleSIUnit
				.findSIUnitAndConvertToString(Long.MAX_VALUE, df, suffix));
		System.out.println(SimpleSIUnit.findSIUnitAndConvertToString(
				Long.MAX_VALUE - 3215467456546546005l, df, suffix));
		// assertEquals("1.04kB",
		// SimpleSIUnit.findSIUnitAndConvertToString(kb + 44, df, suffix));
		// assertEquals("1023.01GB", SimpleSIUnit
		// .findSIUnitAndConvertToString(Tb - Gb + Mb * 10, df, suffix));

	}

}
