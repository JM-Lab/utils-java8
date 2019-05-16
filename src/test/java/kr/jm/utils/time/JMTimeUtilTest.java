package kr.jm.utils.time;

import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JMTimeUtilTest {

    private static final String ASIA_SEOUL = "Asia/Seoul";
    private static final String TIMESTAME1 = "2014-09-26T06:36:09.327Z";
    private static final String TIMESTAME2_1 = "2014-09-26T15:36:09.327";
    private static final String TIMESTAME2 = "2014-09-26T15:36:09.327Z";
    private static final String TIMESTAME3 = "2014-09-26T23:59:59.900Z";
    private static final String TIMESTAME4 = "2014-09-26T15:00:00.000Z";
    private static final String TIMESTAME5 = "2014-09-26T14:59:59.990Z";
    private static final String TIMESTAME6 = "2014-09-26T14:59:59Z";
    private static final String TIME_ZONE_ID = ASIA_SEOUL; // GMT, UTC
    private static final String INDEX_FORMAT = "yyyy.MM.dd";
    private static final String DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
    private static final String DATE_FORMAT2 = "dd/MMM/yyyy:HH:mm:ss";

    private final long timestamp = 1395394283524l;

    @Before
    public void setUp() {
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
                JMTimeUtil.ISO_OFFSET_DATE_TIME_MILLS, ASIA_SEOUL));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.ISO_INSTANT_MILLS_TIMEZONE_NAME, ASIA_SEOUL));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.ISO_LOCAL_DATE_TIME_MILLS, ASIA_SEOUL));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS_OFFSET, ASIA_SEOUL));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME,
                ASIA_SEOUL));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS, ASIA_SEOUL));

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(JMTimeUtil.getTime(currentTimeMillis,
                JMTimeUtil.ISO_OFFSET_DATE_TIME_MILLS));
        String timeAsDefaultUtcFormat =
                JMTimeUtil.getTimeAsDefaultUtcFormat(currentTimeMillis);
        System.out.println(timeAsDefaultUtcFormat);

        assertTrue(JMTimeUtil.changeIsoTimestampToLong(
                timeAsDefaultUtcFormat) == currentTimeMillis);

        System.out.println(
                JMTimeUtil.changeFormatAndTimeZone(timeAsDefaultUtcFormat,
                        JMTimeUtil.ISO_OFFSET_DATE_TIME_MILLS, ASIA_SEOUL));

    }

    @Test
    public void testChangeIsoTimestampInUTC() {
        String isoTimestampString = "2015-04-28T10:30:23.000+0900";
        System.out.println(
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));
        assertEquals("2015-04-28T01:30:23.000Z",
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

        isoTimestampString = "2014-03-21T18:31:23.000Z";
        System.out.println(
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));
        assertEquals("2014-03-21T18:31:23.000Z",
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

        isoTimestampString = "2015-04-28T10:30:23.000z";
        System.out.println(
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));
        assertEquals("2015-04-28T10:30:23.000Z",
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

        assertEquals("2015-04-28T10:30:23.000Z",
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

        assertEquals("2015-04-28T10:30:23.000Z",
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

        assertEquals("2015-04-28T10:30:23.000Z",
                JMTimeUtil.changeIsoTimestampInUTC(isoTimestampString));

    }

    @Test
    public void testGetTimeMillis() {
        long timeMillis =
                JMTimeUtil.getTimeMillis(2015, 4, 16, 00, 2, 00, "Asia/Seoul");
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
    public void testChangeTimestampToLong() {
        String defaultLogDateFormat = "dd/MMM/yyyy:HH:mm:ss";
        String dateString = "27/Oct/2000:09:27:09";
        long changeIsoTimestampToLong = JMTimeUtil
                .changeTimestampToLong(defaultLogDateFormat, dateString, "UTC");
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss";
        String timezoneId = "GMT+2";
        dateString = "27/8월/2000:09:27:09";

        changeIsoTimestampToLong = JMTimeUtil.changeTimestampToLong(
                defaultLogDateFormat, dateString, timezoneId);
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        changeIsoTimestampToLong = JMTimeUtil
                .changeTimestampToLong(defaultLogDateFormat, dateString, "UTC");
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        // zoneId null 이면 시스템 기본 timezoneId를 사용!!!
        changeIsoTimestampToLong = JMTimeUtil
                .changeTimestampToLong(defaultLogDateFormat, dateString, null);
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss Z";
        dateString = "27/8월/2000:09:27:09 -0400";

        changeIsoTimestampToLong = JMTimeUtil
                .changeTimestampToLong(defaultLogDateFormat, dateString, "UTC");
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));
        assertEquals("2000-08-27T13:27:09.000Z",
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        // format에 zone 정보가 있으면 zoneId를 셋팅해도 먹지 않음!!!
        changeIsoTimestampToLong = JMTimeUtil.changeTimestampToLong(
                defaultLogDateFormat, dateString, timezoneId);
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));
        assertEquals("2000-08-27T13:27:09.000Z",
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        defaultLogDateFormat = "dd/MM월/yyyy:HH:mm:ss";
        dateString = "27/8월/2000:09:27:09";

        // zone 정보가 없을때 시스템 기본 값으로 적용 됨!!! 한국 +0900 이라 00시가
        changeIsoTimestampToLong = JMTimeUtil
                .changeTimestampToLong(defaultLogDateFormat, dateString);
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));
        assertEquals("2000-08-27T00:27:09.000Z",
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

        changeIsoTimestampToLong = JMTimeUtil.changeTimestampToLong(
                defaultLogDateFormat, dateString, timezoneId);
        System.out.println(changeIsoTimestampToLong);
        System.out.println(
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));
        assertEquals("2000-08-27T07:27:09.000Z",
                JMTimeUtil.getTimeAsDefaultUtcFormat(changeIsoTimestampToLong));

    }

    @Test
    public void testChangeISOTimestampWithMillsToWithoutMills() {
        String isoTimestampString = "2015-04-28T10:30:23.032+0900";
        System.out.println(
                JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
        assertEquals("2015-04-28T01:30:23Z",
                JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
        System.out.println(JMTimeUtil
                .changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
        assertEquals("2015-04-28T01:30:23", JMTimeUtil
                .changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
        isoTimestampString = "2015-04-28T10:30:23.999Z";
        System.out.println(
                JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
        assertEquals("2015-04-28T10:30:23Z",
                JMTimeUtil.changeIsoTimestampToIsoInstant(isoTimestampString));
        System.out.println(JMTimeUtil
                .changeIsoTimestampToIsoLocalDateTime(isoTimestampString));
        assertEquals("2015-04-28T10:30:23", JMTimeUtil
                .changeIsoTimestampToIsoLocalDateTime(isoTimestampString));

    }

    @Test
    public void testChangeTimestampToNewFormat() {
        String simpleDateFormat = "yyyyMMddHHmmssSSS";
        System.out.println(JMTimeUtil.changeTimestampToNewFormat(
                simpleDateFormat, "20150925133631446",
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS));
        assertEquals("2015-09-25T13:36:31.446+0900",
                JMTimeUtil.changeTimestampToNewFormat(simpleDateFormat,
                        "20150925133631446",
                        JMTimeUtil.ISO_OFFSET_DATE_TIME_MILLS));

    }

    @Test
    public void testChangeTimestampToIsoInstant() {
        String simpleDateFormat = "yyyyMMddHHmmssSSS";
        System.out.println(JMTimeUtil.changeTimestampToIsoInstant(
                simpleDateFormat, "20150925133631446"));
        assertEquals("2015-09-25T04:36:31Z",
                JMTimeUtil.changeTimestampToIsoInstant(simpleDateFormat,
                        "20150925133631446"));
    }

    @Test
    public void testChangeIsoTimestampToLong() {
        long timeMillis = JMTimeUtil
                .changeIsoTimestampToLong("2015-09-25T04:36:31", ASIA_SEOUL);
        System.out.println(JMTimeUtil.getTimeAsDefaultUtcFormat(timeMillis));
        assertEquals("2015-09-24T19:36:31.000Z",
                JMTimeUtil.getTimeAsDefaultUtcFormat(timeMillis));
        timeMillis = JMTimeUtil.changeIsoTimestampToLong("2015-09-25T04:36:31Z",
                ASIA_SEOUL);
        System.out.println(JMTimeUtil.getTimeAsDefaultUtcFormat(timeMillis));
        assertEquals("2015-09-25T04:36:31.000Z",
                JMTimeUtil.getTimeAsDefaultUtcFormat(timeMillis));

    }

    @Test
    public void testGetZonedDataTime() {
        long currentTimeMillis = 1483064372217l;
        ZonedDateTime zonedDataTime = JMTimeUtil.getZonedDataTime(
                currentTimeMillis,
                ZoneId.getAvailableZoneIds().stream().findFirst().get());
        System.out.println(zonedDataTime);
        System.out.println(zonedDataTime.toOffsetDateTime());
        System.out.println(zonedDataTime.toLocalDate());
        System.out.println(zonedDataTime.toLocalDateTime());

        ZonedDateTime zonedDataTime2 = JMTimeUtil.getZonedDataTime(
                currentTimeMillis, "America/Indiana/Indianapolis");
        System.out.println(zonedDataTime2);
        System.out.println(zonedDataTime2.toOffsetDateTime());
        System.out.println(zonedDataTime2.toLocalDate());
        System.out.println(zonedDataTime2.toLocalDateTime());

        assertEquals(
                zonedDataTime.withZoneSameInstant(ZoneId.of(ASIA_SEOUL))
                        .toLocalDateTime().toString(),
                zonedDataTime2.withZoneSameInstant(ZoneId.of(ASIA_SEOUL))
                        .toLocalDateTime().toString());
    }

    @Test
    public void testGetOffsetDataTime() {
        long currentTimeMillis = 1483064372217l;
        System.out.println(ZoneOffset.getAvailableZoneIds());
        OffsetDateTime offsetDataTime =
                JMTimeUtil.getOffsetDateTime(currentTimeMillis, "+09:00");
        System.out.println(offsetDataTime);
        System.out.println(offsetDataTime.toZonedDateTime());
        System.out.println(offsetDataTime.toLocalDate());
        System.out.println(offsetDataTime.toLocalDateTime());

        OffsetDateTime offsetDataTime2 =
                JMTimeUtil.getOffsetDateTime(currentTimeMillis, "-1800");
        System.out.println(offsetDataTime2);
        System.out.println(offsetDataTime2.toZonedDateTime());
        System.out.println(offsetDataTime2.toLocalDate());
        System.out.println(offsetDataTime2.toLocalDateTime());

        assertEquals(
                offsetDataTime.withOffsetSameInstant(ZoneOffset.of("+0900"))
                        .toLocalDateTime().toString(),
                offsetDataTime2.withOffsetSameInstant(ZoneOffset.of("+0900"))
                        .toLocalDateTime().toString());
    }

    @Test
    public void testChangeTimestampToNewFormatStringZoneIdDateTimeFormatter() {
        System.out.println(JMTimeUtil.changeTimestampToNewFormat(TIMESTAME3,
                ZoneId.of(TIME_ZONE_ID), DateTimeFormatter
                        .ofPattern(JMTimeUtil.ISO_INSTANT_TIMEZONE_NAME)));
        assertTrue(JMTimeUtil
                .changeTimestampToNewFormat(TIMESTAME3, ZoneId.of(TIME_ZONE_ID),
                        DateTimeFormatter.ofPattern(
                                JMTimeUtil.ISO_INSTANT_TIMEZONE_NAME))
                .startsWith("2014-09-27"));
        System.out.println(JMTimeUtil.changeTimestampToNewFormat(TIMESTAME3,
                DateTimeFormatter
                        .ofPattern(JMTimeUtil.ISO_INSTANT_TIMEZONE_NAME)));
        assertTrue(JMTimeUtil
                .changeTimestampToNewFormat(TIMESTAME3,
                        DateTimeFormatter.ofPattern(
                                JMTimeUtil.ISO_INSTANT_TIMEZONE_NAME))
                .startsWith("2014-09-26"));
        assertTrue(JMTimeUtil
                .changeTimestampToNewFormatWithDefaultZoneId(TIMESTAME3,
                        DateTimeFormatter.ofPattern(
                                JMTimeUtil.ISO_INSTANT_TIMEZONE_NAME))
                .startsWith("2014-09-27"));
    }

    @Test
    public void testChangeFormatAndTimeZoneToDefaultUtcFormat() {
        System.out.println(JMTimeUtil.getCurrentTimestamp(DATE_FORMAT,
                TIME_ZONE_ID));
        System.out.println(JMTimeUtil
                .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                        ZoneId.of("UTC").toString(), "03/Jan/2018:18:44:19"));
        assertEquals(JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                TIME_ZONE_ID, "03/Jan/2018:18:44:19")
                , JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                null, "03/Jan/2018:18:44:19"));
        assertEquals(JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                TIME_ZONE_ID, "03/Jan/2018:18:44:19")
                , JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                "03/Jan/2018:18:44:19"));
    }

    @Test
    public void testChangeFormatAndTimeZoneToOffsetDateTime() {
        System.out.println(JMTimeUtil.getCurrentTimestamp(DATE_FORMAT,
                TIME_ZONE_ID));
        System.out.println(JMTimeUtil.DEFAULT_ZONE_ID);
        System.out.println(JMTimeUtil.DEFAULT_ZONE_OFFSET_ID);
        System.out.println(JMTimeUtil
                .changeFormatAndTimeZoneToOffsetDateTime(DATE_FORMAT2,
                        JMTimeUtil.DEFAULT_ZONE_OFFSET_ID,
                        "03/Jan/2018:18:44:19"));
        assertEquals("2018-01-05T04:53:37.000Z", JMTimeUtil
                .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT,
                        "05/Jan/2018:13:53:37 +0900"));
        assertEquals(JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                TIME_ZONE_ID, "03/Jan/2018:18:44:19")
                , JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                null, "03/Jan/2018:18:44:19"));
        assertEquals(JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                TIME_ZONE_ID, "03/Jan/2018:18:44:19")
                , JMTimeUtil
                        .changeFormatAndTimeZoneToDefaultUtcFormat(DATE_FORMAT2,
                                "03/Jan/2018:18:44:19"));
    }
}
