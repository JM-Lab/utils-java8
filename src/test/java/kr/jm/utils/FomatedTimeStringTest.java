package kr.jm.utils;

import org.junit.Test;

import kr.jm.utils.time.JMTimeUtil;

public class FomatedTimeStringTest {

	private final long timestamp = 1395394283524l;

	@Test
	public void testFomatedTimeString() {

		System.out.println(JMTimeUtil.getTime(timestamp,
				JMTimeUtil.LONG_FORMAT3_WITH_PLUS_TIMEZONE, "Asia/Seoul"));
		System.out.println(JMTimeUtil.getTime(timestamp,
				JMTimeUtil.LONG_FORMAT_WITH_TIMEZONE, "Asia/Seoul"));
		System.out.println(JMTimeUtil.getTime(timestamp,
				JMTimeUtil.LONG_FORMAT3_WITHOUT_TIMEZONE, "Asia/Seoul"));
		System.out.println(JMTimeUtil.getTime(timestamp,
				JMTimeUtil.SHORT_FORMAT_WITH_PLUS_TIMEZONE, "Asia/Seoul"));
		System.out.println(JMTimeUtil.getTime(timestamp,
				JMTimeUtil.SHORT_FORMAT_WITH_TIMEZONE, "Asia/Seoul"));
		System.out.println(JMTimeUtil.getTime(timestamp,
				JMTimeUtil.SHORT_FORMAT_WITHOUT_TIMEZONE, "Asia/Seoul"));
	}

}
