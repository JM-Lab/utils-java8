package kr.jm.utils.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class JMTimeUtilTest {

	private static final String ASIA_SEOUL = "Asia/Seoul";
	private static final String TIMESTAME1 = "2014-09-26T06:36:09.327Z";
	private static final String TIMESTAME2_1 = "2014-09-26T15:36:09.327";
	private static final String TIMESTAME2 = "2014-09-26T15:36:09.327Z";
	private static final String TIMESTAME3 = "2014-09-26T23:59:59.9Z";
	private static final String TIMESTAME4 = "2014-09-26T15:00:00.000Z";
	private static final String TIMESTAME5 = "2014-09-26T14:59:59.99Z";
	private static final String TIMESTAME6 = "2014-09-26T14:59:59Z";
	private static final String TIME_ZONE_ID = ASIA_SEOUL; // GMT, UTC
	private static final String INDEX_FORMAT = "yyyy.MM.dd";

	private final long timestamp = 1395394283524l;

	@Before
	public void setUp() throws Exception {
	}

	@Test
		public void testChangeFormatAndTimeZone() {
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME1,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.26".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME1, INDEX_FORMAT, TIME_ZONE_ID)));
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME2_1,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.26".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME2_1, INDEX_FORMAT, TIME_ZONE_ID)));
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME2,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.27".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME2, INDEX_FORMAT, TIME_ZONE_ID)));
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME3,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.27".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME3, INDEX_FORMAT, TIME_ZONE_ID)));
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME4,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.27".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME4, INDEX_FORMAT, TIME_ZONE_ID)));
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME5,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.26".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME5, INDEX_FORMAT, TIME_ZONE_ID)));
	
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(TIMESTAME6,
					INDEX_FORMAT, TIME_ZONE_ID));
			assertTrue("2014.09.26".equals(JMTimeUtil.changeFormatAndTimeZone(
					TIMESTAME5, INDEX_FORMAT, TIME_ZONE_ID)));
	
			System.out.println(JMTimeUtil.getTime(timestamp,
					JMTimeUtil.LONG_FORMAT3_WITH_PLUS_TIMEZONE, ASIA_SEOUL));
			System.out.println(JMTimeUtil.getTime(timestamp,
					JMTimeUtil.LONG_FORMAT_WITH_TIMEZONE, ASIA_SEOUL));
			System.out.println(JMTimeUtil.getTime(timestamp,
					JMTimeUtil.LONG_FORMAT3_WITHOUT_TIMEZONE, ASIA_SEOUL));
			System.out.println(JMTimeUtil.getTime(timestamp,
					JMTimeUtil.SHORT_FORMAT_WITH_PLUS_TIMEZONE, ASIA_SEOUL));
			System.out.println(JMTimeUtil.getTime(timestamp,
					JMTimeUtil.SHORT_FORMAT_WITH_TIMEZONE, ASIA_SEOUL));
			System.out.println(JMTimeUtil.getTime(timestamp,
					JMTimeUtil.SHORT_FORMAT_WITHOUT_TIMEZONE, ASIA_SEOUL));
	
			long currentTimeMillis = System.currentTimeMillis();
			System.out.println(JMTimeUtil.getTime(currentTimeMillis,
					JMTimeUtil.LONG_FORMAT3_WITH_PLUS_TIMEZONE));
			String timeAsDefaultUtcFormat = JMTimeUtil
					.getTimeAsDefaultUtcFormat(currentTimeMillis);
			System.out.println(timeAsDefaultUtcFormat);
	
			assertTrue(JMTimeUtil.changeIsoTimestampToLong(timeAsDefaultUtcFormat) == currentTimeMillis);
	
			System.out.println(JMTimeUtil.changeFormatAndTimeZone(
					timeAsDefaultUtcFormat,
					JMTimeUtil.LONG_FORMAT3_WITH_PLUS_TIMEZONE, ASIA_SEOUL));
	
		}

	@Test
	public void testChangeIsoTimestampInUTC() {
		String isoTimestampString = "2015-04-28T10:30:23.000+0900";
		System.out.println(JMTimeUtil
				.changeIsoTimestampInUTC(isoTimestampString));
		assertEquals("2015-04-28T01:30:23.000Z",
				JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

		isoTimestampString = "2014-03-21T18:31:23.000Z";
		System.out.println(JMTimeUtil
				.changeIsoTimestampInUTC(isoTimestampString));
		assertEquals("2014-03-21T18:31:23.000Z",
				JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

		isoTimestampString = "2015-04-28T10:30:23.000z";
		System.out.println(JMTimeUtil
				.changeIsoTimestampInUTC(isoTimestampString));
		assertEquals("2015-04-28T10:30:23.000Z",
				JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

		isoTimestampString = "2015-04-28T10:30:23.00z";
		System.out.println(JMTimeUtil
				.changeIsoTimestampInUTC(isoTimestampString));
		assertEquals("2015-04-28T10:30:23.000Z",
				JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

		isoTimestampString = "2015-04-28T10:30:23.0z";
		System.out.println(JMTimeUtil
				.changeIsoTimestampInUTC(isoTimestampString));
		assertEquals("2015-04-28T10:30:23.000Z",
				JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

		isoTimestampString = "2015-04-28T10:30:23.0Z";
		System.out.println(JMTimeUtil
				.changeIsoTimestampInUTC(isoTimestampString));
		assertEquals("2015-04-28T10:30:23.000Z",
				JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

	}

	@Test
	public void testGetTimeMillis() {
		long timeMillis = JMTimeUtil.getTimeMillis(2015, 4, 16, 00, 2, 00,
				"Asia/Seoul");
		System.out.println(timeMillis);
		System.out.println(JMTimeUtil.getTime(timeMillis));
		System.out.println(JMTimeUtil.getTimeAsDefaultUtcFormat(timeMillis));

		// Calender 에서는 Jan 이 0 부터 시작 함
		timeMillis = JMTimeUtil.getTimeMillis(2015, Calendar.APRIL, 16, 00, 2,
				00, "Asia/Seoul");
		System.out.println(timeMillis);
		System.out.println(JMTimeUtil.getTime(timeMillis));
		System.out.println(JMTimeUtil.getTimeAsDefaultUtcFormat(timeMillis));

	}

	@Test
	public void testChangeIsoTimestampToLong() throws Exception {
		String defaultLogDateFormat = "dd/MMM/yyyy:HH:mm:ss";
		String dateString = "27/Oct/2000:09:27:09";
		long changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, "UTC");
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss";
		String timezoneId = "GMT+2";
		dateString = "27/8월/2000:09:27:09";

		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, timezoneId);
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, "UTC");
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		// zoneId null 이면 시스템 기본 timezoneId를 사용!!!
		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, null);
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss Z";
		dateString = "27/8월/2000:09:27:09 -0400";

		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, "UTC");
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));
		assertEquals("2000-08-27T13:27:09.000Z",
				JMTimeUtil
						.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		// format에 zone 정보가 있으면 zoneId를 셋팅해도 먹지 않음!!!
		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, timezoneId);
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));
		assertEquals("2000-08-27T13:27:09.000Z",
				JMTimeUtil
						.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss";
		dateString = "27/8월/2000:09:27:09";

		// zone 정보가 없을때 시스템 기본 값으로 적용 됨!!! 한국 +0900 이라 00시가
		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString);
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));
		assertEquals("2000-08-27T00:27:09.000Z",
				JMTimeUtil
						.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

		changeTimestampStringToLong = JMTimeUtil.changeTimestampToLong(
				defaultLogDateFormat, dateString, timezoneId);
		System.out.println(changeTimestampStringToLong);
		System.out.println(JMTimeUtil
				.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));
		assertEquals("2000-08-27T07:27:09.000Z",
				JMTimeUtil
						.getTimeAsDefaultUtcFormat(changeTimestampStringToLong));

	}

	@Test
	public void testChangeISOTimestampWithMillsToWithoutMills() throws Exception {
		String isoTimestampString = "2015-04-28T10:30:23.032+0900";
		System.out.println(JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
		assertEquals("2015-04-28T01:30:23Z", JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
		System.out.println(JMTimeUtil.changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
		assertEquals("2015-04-28T01:30:23", JMTimeUtil.changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
		isoTimestampString = "2015-04-28T10:30:23.999Z";
		System.out.println(JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
		assertEquals("2015-04-28T10:30:23Z", JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
		System.out.println(JMTimeUtil.changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
		assertEquals("2015-04-28T10:30:23", JMTimeUtil.changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
		
	}


}
