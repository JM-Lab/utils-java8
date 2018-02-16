
package kr.jm.utils.time;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.helper.JMOptional;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * The Class JMTimeUtil.
 */
public class JMTimeUtil {

    /**
     * The Constant UTC.
     */
    public static final String UTC = "UTC";
    /**
     * The Constant ISO_INSTANT_MILLS_Z.
     */
    public static final String ISO_INSTANT_MILLS_Z =
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // 2014-03-21T18:31:23.000Z
    /**
     * The Constant ISO_OFFSET_DATE_TIME_MILLS.
     */
    public static final String ISO_OFFSET_DATE_TIME_MILLS =
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; // 2014-03-21T18:31:23.000+0900
    /**
     * The Constant ISO_INSTANT_TIMEZONE_NAME.
     */
    public static final String ISO_INSTANT_TIMEZONE_NAME =
            "yyyy-MM-dd'T'HH:mm:ssz"; // 2014-03-21T18:31:23KST
    /**
     * The Constant ISO_INSTANT_MILLS_TIMEZONE_NAME.
     */
    public static final String ISO_INSTANT_MILLS_TIMEZONE_NAME =
            "yyyy-MM-dd'T'HH:mm:ss.SSSz"; // 2014-03-21T18:31:23.000KST
    /**
     * The Constant ISO_LOCAL_DATE_TIME_MILLS.
     */
    public static final String ISO_LOCAL_DATE_TIME_MILLS =
            "yyyy-MM-dd'T'HH:mm:ss.SSS"; // 2014-03-21T18:31:23.000
    /**
     * The Constant BASIC_ISO_DATE_TIME_MILLS_OFFSET.
     */
    public static final String BASIC_ISO_DATE_TIME_MILLS_OFFSET =
            "yyyyMMddHHmmss.SSSZ"; // 20140321183123.000+0900
    /**
     * The Constant BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME.
     */
    public static final String BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME =
            "yyyyMMddHHmmss.SSSz"; // 20140321183123.000KST
    /**
     * The Constant BASIC_ISO_DATE_TIME_MILLS.
     */
    public static final String BASIC_ISO_DATE_TIME_MILLS = "yyyyMMddHHmmss.SSS";
    // 20140321183123.000
    /**
     * The Constant ISO_INSTANT.
     */
    public static final String ISO_INSTANT = "yyyy-MM-dd'T'HH:mm:ssZ";
    // 20140321183123+0900
    /**
     * The Constant ISO_INSTANT_Z.
     */
    public static final String ISO_INSTANT_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    // 2014-03-21T18:31:23Z
    /**
     * The Constant ISO_LOCAL_DATE_TIME.
     */
    public static final String ISO_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private static final ZoneOffset DEFAULT_ZONE_OFFSET_ID =
            OffsetDateTime.now().getOffset();
    /**
     * The Constant DEFAULT_ZONE_ID_STRING.
     */
    public static final String DEFAULT_ZONE_ID_STRING = DEFAULT_ZONE_ID.getId();
    /**
     * The constant DEFAULT_ZONE_OFFSET_ID_STRING.
     */
    public static final String DEFAULT_ZONE_OFFSET_ID_STRING =
            DEFAULT_ZONE_OFFSET_ID.getId();
    private static final String UTC_0000 = "+0000";
    private static final Pattern isoTimestampZoneInfoPattern =
            Pattern.compile("[+|-][0-9]{4}$");
    private static Map<String, SimpleDateFormat> simpleDateFormatMap =
            new HashMap<>();
    private static BiFunction<String, String, Supplier<SimpleDateFormat>>
            newSimpleDateFormatBuilder =
            (dateFormat, zoneId) -> () -> setIfZoneId(
                    new SimpleDateFormat(dateFormat, Locale.US), zoneId);

    /**
     * Change iso timestamp to iso local date time.
     *
     * @param isoTimestamp the iso timestamp
     * @return the string
     */
    public static String
    changeIsoTimestampToIsoLocalDateTime(String isoTimestamp) {
        return changeFormatAndTimeZone(isoTimestamp, ISO_LOCAL_DATE_TIME, UTC);
    }

    /**
     * Change iso timestamp to iso instant.
     *
     * @param isoTimestamp the iso timestamp
     * @return the string
     */
    public static String changeIsoTimestampToIsoInstant(String isoTimestamp) {
        return changeFormatAndTimeZone(isoTimestamp, ISO_INSTANT_Z, UTC);
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
     * @param timeFormat the time formatted
     * @return the current timestamp
     */
    public static String getCurrentTimestamp(String timeFormat) {
        return getTimeInUTC(System.currentTimeMillis(), timeFormat);
    }

    /**
     * Gets the current timestamp.
     *
     * @param timeFormat the time formatted
     * @param zoneId     the zone id
     * @return the current timestamp
     */
    public static String getCurrentTimestamp(String timeFormat, String zoneId) {
        return getTime(System.currentTimeMillis(), timeFormat, zoneId);
    }

    /**
     * Gets the time.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time
     */
    public static String getTime(long epochTimestamp) {
        return getTimeAsLongFormatWithPlusTimezone(epochTimestamp);
    }

    /**
     * Gets the time as default utc formatted.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as default utc formatted
     */
    public static String getTimeAsDefaultUtcFormat(long epochTimestamp) {
        return getTimeInUTC(epochTimestamp, ISO_INSTANT_MILLS_Z);
    }

    /**
     * Gets the time as long formatted with plus timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as long formatted with plus timezone
     */
    public static String
    getTimeAsLongFormatWithPlusTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, ISO_OFFSET_DATE_TIME_MILLS);
    }

    /**
     * Gets the time as long formatted with timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as long formatted with timezone
     */
    public static String getTimeAsLongFormatWithTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, ISO_INSTANT_MILLS_TIMEZONE_NAME);
    }

    /**
     * Gets the time as long formatted without timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as long formatted without timezone
     */
    public static String
    getTimeAsLongFormatWithoutTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, ISO_LOCAL_DATE_TIME_MILLS);
    }

    /**
     * Gets the time as short formatted with plus timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as short formatted with plus timezone
     */
    public static String
    getTimeAsShortFormatWithPlusTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, BASIC_ISO_DATE_TIME_MILLS_OFFSET);
    }

    /**
     * Gets the time as short formatted with timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as short formatted with timezone
     */
    public static String getTimeAsShortFormatWithTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME);
    }

    /**
     * Gets the time as short formatted without timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as short formatted without timezone
     */
    public static String
    getTimeAsShortFormatWithoutTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, BASIC_ISO_DATE_TIME_MILLS);
    }

    /**
     * Gets the time in UTC.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time formatted
     * @return the time in UTC
     */
    public static String getTimeInUTC(long epochTimestamp, String timeFormat) {
        return getTime(epochTimestamp, timeFormat, UTC);
    }

    /**
     * Gets the time.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time formatted
     * @return the time
     */
    public static String getTime(long epochTimestamp, String timeFormat) {
        return getTime(epochTimestamp, timeFormat, DEFAULT_ZONE_ID);
    }

    /**
     * Gets the time.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time formatted
     * @param zoneId         the zone id
     * @return the time
     */
    public static String getTime(long epochTimestamp, String timeFormat,
            String zoneId) {
        return getTime(epochTimestamp, timeFormat, ZoneId.of(zoneId));
    }

    /**
     * Gets the time.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time formatted
     * @param zoneId         the zone id
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
     * @param isoTimestamp the iso timestamp
     * @return the string
     */
    public static String changeIsoTimestampInUTC(String isoTimestamp) {
        return getTimeAsDefaultUtcFormat(
                changeIsoTimestampToLong(isoTimestamp));
    }

    /**
     * Change formatted and time zone.
     *
     * @param isoTimestamp   the iso timestamp
     * @param toBeTimeFormat the to be time formatted
     * @param zoneId         the zone id
     * @return the string
     */
    public static String changeFormatAndTimeZone(String isoTimestamp,
            String toBeTimeFormat, String zoneId) {
        return getTime(changeIsoTimestampToLong(isoTimestamp, zoneId),
                toBeTimeFormat, zoneId);
    }

    /**
     * Change format and time zone to default utc format string.
     *
     * @param dateFormat the date format
     * @param zoneId     the zone id
     * @param dateString the date string
     * @return the string
     */
    public static String changeFormatAndTimeZoneToDefaultUtcFormat(
            String dateFormat, String zoneId, String dateString) {
        return JMOptional.getOptional(zoneId)
                .map(zId -> getTimeAsDefaultUtcFormat(
                        changeTimestampToLong(dateFormat,
                                dateString, zoneId)))
                .orElseGet(() -> changeFormatAndTimeZoneToDefaultUtcFormat(
                        dateFormat, dateString));
    }

    /**
     * Change format and time zone to default utc format string.
     *
     * @param dateFormat the date format
     * @param dateString the date string
     * @return the string
     */
    public static String changeFormatAndTimeZoneToDefaultUtcFormat(
            String dateFormat, String dateString) {
        return getTimeAsDefaultUtcFormat(changeTimestampToLong(dateFormat,
                dateString));
    }

    /**
     * Change format and time zone to offset date time string.
     *
     * @param dateFormat the date format
     * @param dateString the date string
     * @return the string
     */
    public static String changeFormatAndTimeZoneToOffsetDateTime(
            String dateFormat, String dateString) {
        return getOffsetDateTime(changeTimestampToLong(dateFormat, dateString))
                .toString();
    }

    /**
     * Change format and time zone to offset date time string.
     *
     * @param dateFormat   the date format
     * @param zoneOffsetId the zone offset id
     * @param dateString   the date string
     * @return the string
     */
    public static String changeFormatAndTimeZoneToOffsetDateTime(
            String dateFormat, String zoneOffsetId, String dateString) {
        return JMOptional.getOptional(zoneOffsetId)
                .map(id -> getOffsetDateTime(changeTimestampToLong
                        (dateFormat, dateString, id)).toString())
                .orElseGet(() -> changeFormatAndTimeZoneToOffsetDateTime(
                        dateFormat, dateString));
    }

    /**
     * Change formatted and time zone.
     *
     * @param isoTimestamp   the iso timestamp
     * @param toBeTimeFormat the to be time formatted
     * @return the string
     */
    public static String changeFormatAndTimeZone(String isoTimestamp,
            String toBeTimeFormat) {
        return changeFormatAndTimeZone(isoTimestamp, toBeTimeFormat,
                DEFAULT_ZONE_ID_STRING);
    }

    /**
     * Change iso timestamp to long.
     *
     * @param isoTimestamp the iso timestamp
     * @return the long
     */
    public static long changeIsoTimestampToLong(String isoTimestamp) {
        try {
            return ZonedDateTime.parse(isoTimestamp).toInstant().toEpochMilli();
        } catch (Exception e) {
            return changeIsoTimestampToLong(isoTimestamp,
                    DEFAULT_ZONE_ID_STRING);
        }
    }

    /**
     * Change iso timestamp to long.
     *
     * @param isoTimestamp the iso timestamp
     * @param zoneId       the zone id
     * @return the long
     */
    public static long changeIsoTimestampToLong(String isoTimestamp,
            String zoneId) {
        try {
            return LocalDateTime.parse(isoTimestamp).atZone(ZoneId.of(zoneId))
                    .toInstant().toEpochMilli();
        } catch (Exception e) {
            isoTimestamp = changeZTo0000(isoTimestamp);
            return changeTimestampToLong(getTimeFormat(isoTimestamp),
                    isoTimestamp, zoneId);
        }
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
        if (isContainsDot && isContainsPlusOrMinus && length == 28)
            return ISO_OFFSET_DATE_TIME_MILLS;
        else if (isContainsDot && !isContainsPlusOrMinus) {
            switch (length) {
                case 26:
                    return ISO_INSTANT_MILLS_TIMEZONE_NAME;
                case 23:
                    return ISO_LOCAL_DATE_TIME_MILLS;
                case 22:
                    return ISO_INSTANT_TIMEZONE_NAME;
            }
        } else if (!isContainsDot && !isContainsPlusOrMinus && length == 19)
            return ISO_LOCAL_DATE_TIME;
        else if (!isContainsDot && isContainsPlusOrMinus && length == 24)
            return ISO_INSTANT;
        throw new RuntimeException(
                "Don't Support Format ISO Timestamp!!! - " + isoTimestamp);
    }

    private static SimpleDateFormat getSimpleDateFormat(String dateFormat) {
        return getSimpleDateFormat(dateFormat, null);
    }

    private static SimpleDateFormat getSimpleDateFormat(String dateFormat,
            String zoneId) {
        return JMMap.getOrPutGetNew(simpleDateFormatMap,
                buildSimpleDateFormatKey(dateFormat, zoneId),
                newSimpleDateFormatBuilder.apply(dateFormat, zoneId));
    }

    private static String buildSimpleDateFormatKey(String dateFormat,
            String zoneId) {
        return dateFormat + zoneId;
    }

    /**
     * Change timestamp to long.
     *
     * @param dateFormat the date formatted
     * @param timestamp  the timestamp
     * @param zoneId     the zone id
     * @return the long
     */
    public static long changeTimestampToLong(String dateFormat,
            String timestamp, String zoneId) {
        return changeTimestampToLong(getSimpleDateFormat(dateFormat, zoneId),
                timestamp);
    }

    /**
     * Change timestamp to long.
     *
     * @param dateFormat the date formatted
     * @param timestamp  the timestamp
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
     * @param simpleDateFormat the simple date formatted
     * @param timestamp        the timestamp
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
     * Change timestamp to new formatted.
     *
     * @param dateFormat    the date formatted
     * @param timestamp     the timestamp
     * @param newDateFormat the new date formatted
     * @return the string
     */
    public static String changeTimestampToNewFormat(String dateFormat,
            String timestamp, String newDateFormat) {
        return getTime(changeTimestampToLong(getSimpleDateFormat(dateFormat),
                timestamp), newDateFormat);
    }

    /**
     * Change timestamp to new formatted.
     *
     * @param isoTimestamp the iso timestamp
     * @param zoneID       the zone ID
     * @param newFormat    the new formatted
     * @return the string
     */
    public static String changeTimestampToNewFormat(String isoTimestamp,
            ZoneId zoneID, DateTimeFormatter newFormat) {
        return ZonedDateTime.parse(isoTimestamp).withZoneSameInstant(zoneID)
                .format(newFormat);
    }

    /**
     * Change timestamp to new formatted with default zone id.
     *
     * @param isoTimestamp the iso timestamp
     * @param newFormat    the new formatted
     * @return the string
     */
    public static String changeTimestampToNewFormatWithDefaultZoneId(
            String isoTimestamp, DateTimeFormatter newFormat) {
        return changeTimestampToNewFormat(isoTimestamp, DEFAULT_ZONE_ID,
                newFormat);
    }

    /**
     * Change timestamp to new formatted.
     *
     * @param isoTimestamp the iso timestamp
     * @param newFormat    the new formatted
     * @return the string
     */
    public static String changeTimestampToNewFormat(String isoTimestamp,
            DateTimeFormatter newFormat) {
        return ZonedDateTime.parse(isoTimestamp).format(newFormat);
    }

    /**
     * Change timestamp to iso instant.
     *
     * @param dateFormat the date formatted
     * @param timestamp  the timestamp
     * @return the string
     */
    public static String changeTimestampToIsoInstant(String dateFormat,
            String timestamp) {
        return getTime(changeTimestampToLong(getSimpleDateFormat(dateFormat),
                timestamp), ISO_INSTANT_Z, UTC);
    }

    private static SimpleDateFormat
    setIfZoneId(SimpleDateFormat simpleDateFormat, String zoneId) {
        synchronized (simpleDateFormat) {
            if (zoneId != null)
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(zoneId));
            return simpleDateFormat;
        }
    }

    /**
     * Gets the time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day addAll month
     * @param hour       the hour
     * @param minute     the minute
     * @param second     the second
     * @param mills      the mills
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            int hour, int minute, int second, int mills, String zoneId) {
        return ZonedDateTime
                .of(year, month, dayOfMonth, hour, minute, second,
                        new Long(TimeUnit.MILLISECONDS.toNanos(mills))
                                .intValue(),
                        ZoneId.of(zoneId))
                .toInstant().toEpochMilli();
    }

    /**
     * Gets the time millis with nano.
     *
     * @param year         the year
     * @param month        the month
     * @param dayOfMonth   the day addAll month
     * @param hour         the hour
     * @param minute       the minute
     * @param second       the second
     * @param nanoOfSecond the nano addAll second
     * @param zoneId       the zone id
     * @return the time millis with nano
     */
    public static long getTimeMillisWithNano(int year, int month,
            int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
            String zoneId) {
        return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second,
                nanoOfSecond, ZoneId.of(zoneId)).toInstant().toEpochMilli();
    }

    /**
     * Gets the time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day addAll month
     * @param hour       the hour
     * @param minute     the minute
     * @param second     the second
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            int hour, int minute, int second, String zoneId) {
        return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second,
                0, ZoneId.of(zoneId)).toInstant().toEpochMilli();
    }

    /**
     * Gets the time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day addAll month
     * @param hour       the hour
     * @param minute     the minute
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            int hour, int minute, String zoneId) {
        return getTimeMillis(year, month, dayOfMonth, hour, minute, 0, zoneId);
    }

    /**
     * Gets the time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day addAll month
     * @param hour       the hour
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            int hour, String zoneId) {
        return getTimeMillis(year, month, dayOfMonth, hour, 0, 0, zoneId);
    }

    /**
     * Gets the time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day addAll month
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            String zoneId) {
        return getTimeMillis(year, month, dayOfMonth, 0, 0, 0, zoneId);
    }

    /**
     * Gets the time millis.
     *
     * @param year   the year
     * @param month  the month
     * @param zoneId the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, String zoneId) {
        return getTimeMillis(year, month, 0, 0, 0, 0, zoneId);
    }

    /**
     * Gets the time millis.
     *
     * @param year   the year
     * @param zoneId the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, String zoneId) {
        return getTimeMillis(year, 0, 0, 0, 0, 0, zoneId);
    }

    /**
     * Gets the zoned metric time.
     *
     * @param timestamp the timestamp
     * @param zoneId    the zone id
     * @return the zoned metric time
     */
    public static ZonedDateTime getZonedDataTime(long timestamp,
            String zoneId) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.of(zoneId));
    }

    /**
     * Gets the zoned metric time.
     *
     * @param timestamp the timestamp
     * @return the zoned metric time
     */
    public static ZonedDateTime getZonedDataTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(DEFAULT_ZONE_ID);
    }

    /**
     * Gets offset date time.
     *
     * @param timestamp  the timestamp
     * @param zoneOffset the zone offset
     * @return the offset date time
     */
    public static OffsetDateTime getOffsetDateTime(long timestamp,
            ZoneOffset zoneOffset) {
        return Instant.ofEpochMilli(timestamp).atOffset(zoneOffset);
    }

    /**
     * Gets the offset date time.
     *
     * @param timestamp    the timestamp
     * @param zoneOffsetId the zone offset id
     * @return the offset date time
     */
    public static OffsetDateTime getOffsetDateTime(long timestamp,
            String zoneOffsetId) {
        return getOffsetDateTime(timestamp, ZoneOffset.of(zoneOffsetId));
    }

    /**
     * Gets the offset date time.
     *
     * @param timestamp the timestamp
     * @return the offset date time
     */
    public static OffsetDateTime getOffsetDateTime(long timestamp) {
        return getOffsetDateTime(timestamp, DEFAULT_ZONE_OFFSET_ID.getId());
    }

}
