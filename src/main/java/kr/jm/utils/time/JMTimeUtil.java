
package kr.jm.utils.time;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import kr.jm.utils.datastructure.JMMap;

/**
 * The Class JMTimeUtil.
 */
public class JMTimeUtil {

	/** The Constant DefaultTimeZoneId. */
	public static final String DefaultTimeZoneId =
			ZoneId.systemDefault().getId();

	/** The Constant UTC. */
	public static final String UTC = "UTC";

	/** The Constant LONG_FORMAT_WITH_Z. */
	public static final String LONG_FORMAT_WITH_Z =
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // 2014-03-21T18:31:23.000Z

	/** The Constant LONG_FORMAT3_WITH_PLUS_TIMEZONE. */
	public static final String LONG_FORMAT3_WITH_PLUS_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ"; // 2014-03-21T18:31:23.000+0900

	/** The Constant LONG_FORMAT2_WITH_PLUS_TIMEZONE. */
	public static final String LONG_FORMAT2_WITH_PLUS_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.SSZ"; // 2014-03-21T18:31:23.00+0900

	/** The Constant LONG_FORMAT1_WITH_PLUS_TIMEZONE. */
	public static final String LONG_FORMAT1_WITH_PLUS_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.SZ"; // 2014-03-21T18:31:23.0+0900

	/** The Constant LONG_FORMAT_WITH_TIMEZONE. */
	public static final String LONG_FORMAT_WITH_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.SSSz"; // 2014-03-21T18:31:23.000KST

	/** The Constant LONG_FORMAT3_WITHOUT_TIMEZONE. */
	public static final String LONG_FORMAT3_WITHOUT_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.SSS"; // 2014-03-21T18:31:23.000

	/** The Constant LONG_FORMAT2_WITHOUT_TIMEZONE. */
	public static final String LONG_FORMAT2_WITHOUT_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.SS"; // 2014-03-21T18:31:23.00

	/** The Constant LONG_FORMAT1_WITHOUT_TIMEZONE. */
	public static final String LONG_FORMAT1_WITHOUT_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss.S"; // 2014-03-21T18:31:23.0

	/** The Constant SHORT_FORMAT_WITH_PLUS_TIMEZONE. */
	public static final String SHORT_FORMAT_WITH_PLUS_TIMEZONE =
			"yyyyMMddHHmmss.SSSZ"; // 20140321183123.000+0900

	/** The Constant SHORT_FORMAT_WITH_TIMEZONE. */
	public static final String SHORT_FORMAT_WITH_TIMEZONE =
			"yyyyMMddHHmmss.SSSz"; // 20140321183123.000KST

	/** The Constant SHORT_FORMAT_WITHOUT_TIMEZONE. */
	public static final String SHORT_FORMAT_WITHOUT_TIMEZONE =
			"yyyyMMddHHmmss.SSS"; // 20140321183123.000

	/** The Constant DATETIME_FORMAT_WITH_PLUS_TIMEZONE. */
	public static final String DATETIME_FORMAT_WITH_PLUS_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ssZ"; // 20140321183123+0900

	/** The Constant DATETIME_FORMAT_WITH_Z. */
	public static final String DATETIME_FORMAT_WITH_Z =
			"yyyy-MM-dd'T'HH:mm:ss'Z'"; // 2014-03-21T18:31:23Z

	/** The Constant DATETIME_FORMAT_WITHOUT_TIMEZONE. */
	public static final String DATETIME_FORMAT_WITHOUT_TIMEZONE =
			"yyyy-MM-dd'T'HH:mm:ss"; // 2014-03-21T18:31:23

	private static final String UTC_0000 = "+0000";
	private static final Pattern isoTimestampZoneInfoPattern =
			Pattern.compile("[\\+|\\-][0-9]{4}$");
	private static Map<String, SimpleDateFormat> simpleDateFormatMap =
			new HashMap<String, SimpleDateFormat>();
	private static BiFunction<String, String, Supplier<SimpleDateFormat>> newSimpleDateFormatBuilder =
			(dateFormat, timeZoneId) -> () -> setIfTimeZoneId(
					new SimpleDateFormat(dateFormat, Locale.US), timeZoneId);

	/**
	 * Change iso timestamp to iso local date time.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @return the string
	 */
	public static String
			changeIsoTimestampToIsoLocalDateTime(String isoTimestamp) {
		return changeFormatAndTimeZone(isoTimestamp,
				DATETIME_FORMAT_WITHOUT_TIMEZONE, UTC);
	}

	/**
	 * Change iso timestamp to iso instant.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @return the string
	 */
	public static String changeIsoTimestampToIsoInstant(String isoTimestamp) {
		return changeFormatAndTimeZone(isoTimestamp, DATETIME_FORMAT_WITH_Z,
				UTC);
	}

	/**
	 * Gets the current timestamp in utc.
	 *
	 * @return the current timestamp in utc
	 */
	public static String getCurrentTimestampInUtc() {
		return getTimeAsDefaultUtcFormat(System.currentTimeMillis());
	}

	/**
	 * Gets the current timestamp.
	 *
	 * @return the current timestamp
	 */
	public static String getCurrentTimestamp() {
		return getTime(System.currentTimeMillis());
	}

	/**
	 * Gets the current timestamp.
	 *
	 * @param timeFormat
	 *            the time format
	 * @return the current timestamp
	 */
	public static String getCurrentTimestamp(String timeFormat) {
		return getTimeInUTC(System.currentTimeMillis(), timeFormat);
	}

	/**
	 * Gets the current timestamp.
	 *
	 * @param timeFormat
	 *            the time format
	 * @param timeZoneId
	 *            the time zone id
	 * @return the current timestamp
	 */
	public static String getCurrentTimestamp(String timeFormat,
			String timeZoneId) {
		return getTime(System.currentTimeMillis(), timeFormat, timeZoneId);
	}

	/**
	 * Gets the time.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time
	 */
	public static String getTime(long epochTimestamp) {
		return getTimeAsLongFormatWithPlusTimezone(epochTimestamp);
	}

	/**
	 * Gets the time as default utc format.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as default utc format
	 */
	public static String getTimeAsDefaultUtcFormat(long epochTimestamp) {
		return getTimeInUTC(epochTimestamp, LONG_FORMAT_WITH_Z);
	}

	/**
	 * Gets the time as long format with plus timezone.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as long format with plus timezone
	 */
	public static String
			getTimeAsLongFormatWithPlusTimezone(long epochTimestamp) {
		return getTime(epochTimestamp, LONG_FORMAT3_WITH_PLUS_TIMEZONE);
	}

	/**
	 * Gets the time as long format with timezone.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as long format with timezone
	 */
	public static String getTimeAsLongFormatWithTimezone(long epochTimestamp) {
		return getTime(epochTimestamp, LONG_FORMAT_WITH_TIMEZONE);
	}

	/**
	 * Gets the time as long format without timezone.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as long format without timezone
	 */
	public static String
			getTimeAsLongFormatWithoutTimezone(long epochTimestamp) {
		return getTime(epochTimestamp, LONG_FORMAT3_WITHOUT_TIMEZONE);
	}

	/**
	 * Gets the time as short format with plus timezone.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as short format with plus timezone
	 */
	public static String
			getTimeAsShortFormatWithPlusTimezone(long epochTimestamp) {
		return getTime(epochTimestamp, SHORT_FORMAT_WITH_PLUS_TIMEZONE);
	}

	/**
	 * Gets the time as short format with timezone.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as short format with timezone
	 */
	public static String getTimeAsShortFormatWithTimezone(long epochTimestamp) {
		return getTime(epochTimestamp, SHORT_FORMAT_WITH_TIMEZONE);
	}

	/**
	 * Gets the time as short format without timezone.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @return the time as short format without timezone
	 */
	public static String
			getTimeAsShortFormatWithoutTimezone(long epochTimestamp) {
		return getTime(epochTimestamp, SHORT_FORMAT_WITHOUT_TIMEZONE);
	}

	/**
	 * Gets the time in UTC.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @param timeFormat
	 *            the time format
	 * @return the time in UTC
	 */
	public static String getTimeInUTC(long epochTimestamp, String timeFormat) {
		return getTime(epochTimestamp, timeFormat, UTC);
	}

	/**
	 * Gets the time.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @param timeFormat
	 *            the time format
	 * @return the time
	 */
	public static String getTime(long epochTimestamp, String timeFormat) {
		return getTime(epochTimestamp, timeFormat, ZoneId.systemDefault());
	}

	/**
	 * Gets the time.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @param timeFormat
	 *            the time format
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time
	 */
	public static String getTime(long epochTimestamp, String timeFormat,
			String timeZoneId) {
		return getTime(epochTimestamp, timeFormat, ZoneId.of(timeZoneId));
	}

	/**
	 * Gets the time.
	 *
	 * @param epochTimestamp
	 *            the epoch timestamp
	 * @param timeFormat
	 *            the time format
	 * @param zoneId
	 *            the zone id
	 * @return the time
	 */
	public static String getTime(long epochTimestamp, String timeFormat,
			ZoneId zoneId) {
		return ZonedDateTime
				.ofInstant(Instant.ofEpochMilli(epochTimestamp), zoneId)
				.format(DateTimeFormatter.ofPattern(timeFormat));
	}

	/**
	 * Change iso timestamp in UTC.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @return the string
	 */
	public static String changeIsoTimestampInUTC(String isoTimestamp) {
		return getTimeAsDefaultUtcFormat(
				changeIsoTimestampToLong(isoTimestamp));
	}

	/**
	 * Change format and time zone.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @param toBeTimeFormat
	 *            the to be time format
	 * @param timeZoneId
	 *            the time zone id
	 * @return the string
	 */
	public static String changeFormatAndTimeZone(String isoTimestamp,
			String toBeTimeFormat, String timeZoneId) {
		return getTime(changeIsoTimestampToLong(isoTimestamp, timeZoneId),
				toBeTimeFormat, timeZoneId);
	}

	/**
	 * Change format and time zone.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @param toBeTimeFormat
	 *            the to be time format
	 * @return the string
	 */
	public static String changeFormatAndTimeZone(String isoTimestamp,
			String toBeTimeFormat) {
		return changeFormatAndTimeZone(isoTimestamp, toBeTimeFormat,
				DefaultTimeZoneId);
	}

	/**
	 * Change iso timestamp to long.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @return the long
	 */
	public static long changeIsoTimestampToLong(String isoTimestamp) {
		return changeIsoTimestampToLong(isoTimestamp, null);
	}

	/**
	 * Change iso timestamp to long.
	 *
	 * @param isoTimestamp
	 *            the iso timestamp
	 * @param timeZoneId
	 *            the time zone id
	 * @return the long
	 */
	public static long changeIsoTimestampToLong(String isoTimestamp,
			String timeZoneId) {
		isoTimestamp = changeZTo0000(isoTimestamp);
		return changeTimestampToLong(getTimeFormat(isoTimestamp), isoTimestamp,
				timeZoneId);
	}

	private static String changeZTo0000(String isoTimestamp) {
		int index = isoTimestamp.length() - 1;
		char lastChar = isoTimestamp.charAt(index);
		return lastChar == 'Z' || lastChar == 'z'
				? isoTimestamp.substring(0, index) + UTC_0000 : isoTimestamp;
	}

	private static String getTimeFormat(String isoTimestamp) {
		boolean isContainsDot = isoTimestamp.contains(".");
		boolean isContainsPlusOrMinus =
				isoTimestampZoneInfoPattern.matcher(isoTimestamp).find();
		int length = isoTimestamp.length();
		if (isContainsDot && isContainsPlusOrMinus) {
			if (length == 28)
				return LONG_FORMAT3_WITH_PLUS_TIMEZONE;
			else if (length == 27)
				return LONG_FORMAT2_WITH_PLUS_TIMEZONE;
			else if (length == 26)
				return LONG_FORMAT1_WITH_PLUS_TIMEZONE;
		} else if (isContainsDot && !isContainsPlusOrMinus) {
			if (length == 23)
				return LONG_FORMAT3_WITHOUT_TIMEZONE;
			else if (length == 22)
				return LONG_FORMAT2_WITHOUT_TIMEZONE;
			else if (length == 21)
				return LONG_FORMAT1_WITHOUT_TIMEZONE;
		} else if (!isContainsDot && !isContainsPlusOrMinus && length == 19) {
			return DATETIME_FORMAT_WITHOUT_TIMEZONE;
		} else if (!isContainsDot && isContainsPlusOrMinus && length == 24) {
			return DATETIME_FORMAT_WITH_PLUS_TIMEZONE;
		}
		throw new RuntimeException(
				"Don't Support Format ISO Timestamp!!! - " + isoTimestamp);
	}

	private static SimpleDateFormat getSimpleDateFormat(String dateFormat) {
		return getSimpleDateFormat(dateFormat, null);
	}

	private static SimpleDateFormat getSimpleDateFormat(String dateFormat,
			String timeZoneId) {
		return JMMap.getOrPutGetNew(simpleDateFormatMap,
				buildSimpleDateFormatKey(dateFormat, timeZoneId),
				newSimpleDateFormatBuilder.apply(dateFormat, timeZoneId));
	}

	private static String buildSimpleDateFormatKey(String dateFormat,
			String timeZoneId) {
		return dateFormat + timeZoneId;
	}

	/**
	 * Change timestamp to long.
	 *
	 * @param dateFormat
	 *            the date format
	 * @param timestamp
	 *            the timestamp
	 * @param timeZoneId
	 *            the time zone id
	 * @return the long
	 */
	public static long changeTimestampToLong(String dateFormat,
			String timestamp, String timeZoneId) {
		return changeTimestampToLong(
				getSimpleDateFormat(dateFormat, timeZoneId), timestamp);
	}

	/**
	 * Change timestamp to long.
	 *
	 * @param dateFormat
	 *            the date format
	 * @param timestamp
	 *            the timestamp
	 * @return the long
	 */
	public static long changeTimestampToLong(String dateFormat,
			String timestamp) {
		return changeTimestampToLong(getSimpleDateFormat(dateFormat),
				timestamp);
	}

	/**
	 * Change timestamp to long.
	 *
	 * @param simpleDateFormat
	 *            the simple date format
	 * @param timestamp
	 *            the timestamp
	 * @return the long
	 */
	public static long changeTimestampToLong(SimpleDateFormat simpleDateFormat,
			String timestamp) {
		try {
			synchronized (simpleDateFormat) {
				return simpleDateFormat.parse(timestamp).getTime();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Change timestamp to new format.
	 *
	 * @param dateFormat
	 *            the date format
	 * @param timestamp
	 *            the timestamp
	 * @param newDateFormat
	 *            the new date format
	 * @return the string
	 */
	public static String changeTimestampToNewFormat(String dateFormat,
			String timestamp, String newDateFormat) {
		return getTime(changeTimestampToLong(getSimpleDateFormat(dateFormat),
				timestamp), newDateFormat);
	}

	/**
	 * Change timestamp to iso instant.
	 *
	 * @param dateFormat
	 *            the date format
	 * @param timestamp
	 *            the timestamp
	 * @return the string
	 */
	public static String changeTimestampToIsoInstant(String dateFormat,
			String timestamp) {
		return getTime(changeTimestampToLong(getSimpleDateFormat(dateFormat),
				timestamp), DATETIME_FORMAT_WITH_Z, UTC);
	}

	private static SimpleDateFormat setIfTimeZoneId(
			SimpleDateFormat simpleDateFormat, String timeZoneId) {
		synchronized (simpleDateFormat) {
			if (timeZoneId != null)
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			return simpleDateFormat;
		}
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param dayOfMonth
	 *            the day of month
	 * @param hour
	 *            the hour
	 * @param minute
	 *            the minute
	 * @param second
	 *            the second
	 * @param mills
	 *            the mills
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, int month, int dayOfMonth,
			int hour, int minute, int second, int mills, String timeZoneId) {
		return ZonedDateTime
				.of(year, month, dayOfMonth, hour, minute, second,
						new Long(TimeUnit.MILLISECONDS.toNanos(mills))
								.intValue(),
						ZoneId.of(timeZoneId))
				.toInstant().toEpochMilli();
	}

	/**
	 * Gets the time millis with nano.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param dayOfMonth
	 *            the day of month
	 * @param hour
	 *            the hour
	 * @param minute
	 *            the minute
	 * @param second
	 *            the second
	 * @param nanoOfSecond
	 *            the nano of second
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis with nano
	 */
	public static long getTimeMillisWithNano(int year, int month,
			int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
			String timeZoneId) {
		return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second,
				nanoOfSecond, ZoneId.of(timeZoneId)).toInstant().toEpochMilli();
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param dayOfMonth
	 *            the day of month
	 * @param hour
	 *            the hour
	 * @param minute
	 *            the minute
	 * @param second
	 *            the second
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, int month, int dayOfMonth,
			int hour, int minute, int second, String timeZoneId) {
		return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second,
				0, ZoneId.of(timeZoneId)).toInstant().toEpochMilli();
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param dayOfMonth
	 *            the day of month
	 * @param hour
	 *            the hour
	 * @param minute
	 *            the minute
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, int month, int dayOfMonth,
			int hour, int minute, String timeZoneId) {
		return getTimeMillis(year, month, dayOfMonth, hour, minute, 0,
				timeZoneId);
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param dayOfMonth
	 *            the day of month
	 * @param hour
	 *            the hour
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, int month, int dayOfMonth,
			int hour, String timeZoneId) {
		return getTimeMillis(year, month, dayOfMonth, hour, 0, 0, timeZoneId);
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param dayOfMonth
	 *            the day of month
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, int month, int dayOfMonth,
			String timeZoneId) {
		return getTimeMillis(year, month, dayOfMonth, 0, 0, 0, timeZoneId);
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, int month, String timeZoneId) {
		return getTimeMillis(year, month, 0, 0, 0, 0, timeZoneId);
	}

	/**
	 * Gets the time millis.
	 *
	 * @param year
	 *            the year
	 * @param timeZoneId
	 *            the time zone id
	 * @return the time millis
	 */
	public static long getTimeMillis(int year, String timeZoneId) {
		return getTimeMillis(year, 0, 0, 0, 0, 0, timeZoneId);
	}

}
