package kr.jm.utils.time;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.helper.JMOptional;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * The type Jm time util.
 */
public class JMTimeUtil {

    /**
     * The constant ISO_INSTANT_MILLS_Z.
     */
    public static final String ISO_INSTANT_MILLS_Z =
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // 2014-03-21T18:31:23.000Z
    /**
     * The constant ISO_OFFSET_DATE_TIME_MILLS.
     */
    public static final String ISO_OFFSET_DATE_TIME_MILLS =
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; // 2014-03-21T18:31:23.000+0900
    /**
     * The constant ISO_INSTANT_TIMEZONE_NAME.
     */
    public static final String ISO_INSTANT_TIMEZONE_NAME =
            "yyyy-MM-dd'T'HH:mm:ssz"; // 2014-03-21T18:31:23KST
    /**
     * The constant ISO_INSTANT_MILLS_TIMEZONE_NAME.
     */
    public static final String ISO_INSTANT_MILLS_TIMEZONE_NAME =
            "yyyy-MM-dd'T'HH:mm:ss.SSSz"; // 2014-03-21T18:31:23.000KST
    /**
     * The constant ISO_LOCAL_DATE_TIME_MILLS.
     */
    public static final String ISO_LOCAL_DATE_TIME_MILLS =
            "yyyy-MM-dd'T'HH:mm:ss.SSS"; // 2014-03-21T18:31:23.000
    /**
     * The constant BASIC_ISO_DATE_TIME_MILLS_OFFSET.
     */
    public static final String BASIC_ISO_DATE_TIME_MILLS_OFFSET =
            "yyyyMMddHHmmss.SSSZ"; // 20140321183123.000+0900
    /**
     * The constant BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME.
     */
    public static final String BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME =
            "yyyyMMddHHmmss.SSSz"; // 20140321183123.000KST
    /**
     * The constant BASIC_ISO_DATE_TIME_MILLS.
     */
    public static final String BASIC_ISO_DATE_TIME_MILLS = "yyyyMMddHHmmss.SSS";
    /**
     * The constant ISO_INSTANT.
     */
// 20140321183123.000
    public static final String ISO_INSTANT = "yyyy-MM-dd'T'HH:mm:ssZ";
    /**
     * The constant ISO_INSTANT_Z.
     */
// 20140321183123+0900
    public static final String ISO_INSTANT_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * The constant ISO_LOCAL_DATE_TIME.
     */
// 2014-03-21T18:31:23Z
    public static final String ISO_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * The constant UTC_ZONE_ID.
     */
    public static final String UTC_ZONE_ID = "UTC";

    /**
     * The constant DEFAULT_ZONE_ID.
     */
    public static final String DEFAULT_ZONE_ID = ZoneId.systemDefault().getId();
    /**
     * The constant DEFAULT_ZONE_OFFSET_ID.
     */
    public static final String DEFAULT_ZONE_OFFSET_ID =
            OffsetDateTime.now().getOffset().getId();

    /**
     * The constant dateTimeFormatterCache.
     */
    public static final Map<String, DateTimeFormatter> dateTimeFormatterCache
            = new WeakHashMap<>();
    /**
     * The constant zoneIdCache.
     */
    public static final Map<String, ZoneId> zoneIdCache = new WeakHashMap<>();
    /**
     * The constant zoneOffsetCache.
     */
    public static final Map<String, ZoneOffset> zoneOffsetCache =
            new WeakHashMap<>();


    private static final String UTC_0000 = "+0000";
    private static final Pattern isoTimestampZoneInfoPattern =
            Pattern.compile("[+|-][0-9]{4}$");
    private static Map<String, SimpleDateFormat> simpleDateFormatMap =
            new WeakHashMap<>();
    private static BiFunction<String, String, Supplier<SimpleDateFormat>>
            newSimpleDateFormatBuilder =
            (dateFormat, zoneId) -> () -> setIfZoneId(
                    new SimpleDateFormat(dateFormat, Locale.US), zoneId);

    /**
     * Change iso timestamp to iso local date time string.
     *
     * @param isoTimestamp the iso timestamp
     * @return the string
     */
    public static String
    changeIsoTimestampToIsoLocalDateTime(String isoTimestamp) {
        return changeFormatAndTimeZone(isoTimestamp, ISO_LOCAL_DATE_TIME,
                UTC_ZONE_ID);
    }

    /**
     * Change iso timestamp to iso instant string.
     *
     * @param isoTimestamp the iso timestamp
     * @return the string
     */
    public static String changeIsoTimestampToIsoInstant(String isoTimestamp) {
        return changeFormatAndTimeZone(isoTimestamp, ISO_INSTANT_Z,
                UTC_ZONE_ID);
    }

    /**
     * Gets current timestamp in utc.
     *
     * @return the current timestamp in utc
     */
    public static String getCurrentTimestampInUtc() {
        return getTimeAsDefaultUtcFormat(System.currentTimeMillis());
    }

    /**
     * Gets current timestamp.
     *
     * @return the current timestamp
     */
    public static String getCurrentTimestamp() {
        return getTime(System.currentTimeMillis());
    }

    /**
     * Gets current timestamp.
     *
     * @param timeFormat the time format
     * @return the current timestamp
     */
    public static String getCurrentTimestamp(String timeFormat) {
        return getTimeInUTC(System.currentTimeMillis(), timeFormat);
    }

    /**
     * Gets current timestamp.
     *
     * @param timeFormat the time format
     * @param zoneId     the zone id
     * @return the current timestamp
     */
    public static String getCurrentTimestamp(String timeFormat, String zoneId) {
        return getTime(System.currentTimeMillis(), timeFormat, zoneId);
    }

    /**
     * Gets time.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time
     */
    public static String getTime(long epochTimestamp) {
        return getTimeAsLongFormatWithPlusTimezone(epochTimestamp);
    }

    /**
     * Gets time as default utc format.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as default utc format
     */
    public static String getTimeAsDefaultUtcFormat(long epochTimestamp) {
        return getTimeInUTC(epochTimestamp, ISO_INSTANT_MILLS_Z);
    }

    /**
     * Gets time as long format with plus timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as long format with plus timezone
     */
    public static String
    getTimeAsLongFormatWithPlusTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, ISO_OFFSET_DATE_TIME_MILLS);
    }

    /**
     * Gets time as long format with timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as long format with timezone
     */
    public static String getTimeAsLongFormatWithTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, ISO_INSTANT_MILLS_TIMEZONE_NAME);
    }

    /**
     * Gets time as long format without timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as long format without timezone
     */
    public static String
    getTimeAsLongFormatWithoutTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, ISO_LOCAL_DATE_TIME_MILLS);
    }

    /**
     * Gets time as short format with plus timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as short format with plus timezone
     */
    public static String
    getTimeAsShortFormatWithPlusTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, BASIC_ISO_DATE_TIME_MILLS_OFFSET);
    }

    /**
     * Gets time as short format with timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as short format with timezone
     */
    public static String getTimeAsShortFormatWithTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME);
    }

    /**
     * Gets time as short format without timezone.
     *
     * @param epochTimestamp the epoch timestamp
     * @return the time as short format without timezone
     */
    public static String
    getTimeAsShortFormatWithoutTimezone(long epochTimestamp) {
        return getTime(epochTimestamp, BASIC_ISO_DATE_TIME_MILLS);
    }

    /**
     * Gets time in utc.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time format
     * @return the time in utc
     */
    public static String getTimeInUTC(long epochTimestamp, String timeFormat) {
        return getTime(epochTimestamp, timeFormat, UTC_ZONE_ID);
    }

    /**
     * Gets time.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time format
     * @return the time
     */
    public static String getTime(long epochTimestamp, String timeFormat) {
        return getTime(epochTimestamp, timeFormat, DEFAULT_ZONE_ID);
    }

    /**
     * Gets time.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time format
     * @param zoneId         the zone id
     * @return the time
     */
    public static String getTime(long epochTimestamp, String timeFormat,
            String zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTimestamp),
                getZoneId(zoneId)).format(getDateTimeFormatter(timeFormat));
    }

    /**
     * Gets date time formatter.
     *
     * @param timeFormat the time format
     * @return the date time formatter
     */
    public static DateTimeFormatter getDateTimeFormatter(String timeFormat) {
        return JMMap
                .getOrPutGetNew(dateTimeFormatterCache, timeFormat,
                        () -> DateTimeFormatter.ofPattern(timeFormat));
    }

    /**
     * Gets zone id.
     *
     * @param zoneId the zone id
     * @return the zone id
     */
    public static ZoneId getZoneId(String zoneId) {
        return JMMap
                .getOrPutGetNew(zoneIdCache, zoneId, () -> ZoneId.of(zoneId));
    }

    /**
     * Gets time.
     *
     * @param epochTimestamp the epoch timestamp
     * @param timeFormat     the time format
     * @param zoneId         the zone id
     * @return the time
     */
    public static String getTime(long epochTimestamp, String timeFormat,
            ZoneId zoneId) {
        return getTime(epochTimestamp, getDateTimeFormatter(timeFormat),
                zoneId);
    }

    /**
     * Gets time.
     *
     * @param epochTimestamp    the epoch timestamp
     * @param dateTimeFormatter the date time formatter
     * @param zoneId            the zone id
     * @return the time
     */
    public static String getTime(long epochTimestamp,
            DateTimeFormatter dateTimeFormatter, ZoneId zoneId) {
        return ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(epochTimestamp), zoneId)
                .format(dateTimeFormatter);
    }

    /**
     * Change iso timestamp in utc string.
     *
     * @param isoTimestamp the iso timestamp
     * @return the string
     */
    public static String changeIsoTimestampInUTC(String isoTimestamp) {
        return getTimeAsDefaultUtcFormat(
                changeIsoTimestampToLong(isoTimestamp));
    }

    /**
     * Change format and time zone string.
     *
     * @param isoTimestamp   the iso timestamp
     * @param toBeTimeFormat the to be time format
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
     * Change format and time zone string.
     *
     * @param isoTimestamp   the iso timestamp
     * @param toBeTimeFormat the to be time format
     * @return the string
     */
    public static String changeFormatAndTimeZone(String isoTimestamp,
            String toBeTimeFormat) {
        return changeFormatAndTimeZone(isoTimestamp, toBeTimeFormat,
                DEFAULT_ZONE_ID);
    }

    /**
     * Change iso timestamp to long long.
     *
     * @param isoTimestamp the iso timestamp
     * @return the long
     */
    public static long changeIsoTimestampToLong(String isoTimestamp) {
        try {
            return ZonedDateTime.parse(isoTimestamp).toInstant().toEpochMilli();
        } catch (Exception e) {
            return changeIsoTimestampToLong(isoTimestamp, DEFAULT_ZONE_ID);
        }
    }

    /**
     * Change iso timestamp to long long.
     *
     * @param isoTimestamp the iso timestamp
     * @param zoneId       the zone id
     * @return the long
     */
    public static long changeIsoTimestampToLong(String isoTimestamp,
            String zoneId) {
        try {
            return LocalDateTime.parse(isoTimestamp).atZone(getZoneId(zoneId))
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

    /**
     * Gets simple date format.
     *
     * @param dateFormat the date format
     * @return the simple date format
     */
    public static SimpleDateFormat getSimpleDateFormat(String dateFormat) {
        return getSimpleDateFormat(dateFormat, null);
    }

    /**
     * Gets simple date format.
     *
     * @param dateFormat the date format
     * @param zoneId     the zone id
     * @return the simple date format
     */
    public static SimpleDateFormat getSimpleDateFormat(String dateFormat,
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
     * Change timestamp to long long.
     *
     * @param dateFormat the date format
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
     * Change timestamp to long long.
     *
     * @param dateFormat the date format
     * @param timestamp  the timestamp
     * @return the long
     */
    public static long changeTimestampToLong(String dateFormat,
            String timestamp) {
        return changeTimestampToLong(getSimpleDateFormat(dateFormat),
                timestamp);
    }

    /**
     * Change timestamp to long long.
     *
     * @param simpleDateFormat the simple date format
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
     * Change timestamp to new format string.
     *
     * @param originDateFormat the origin date format
     * @param originTimestamp  the origin timestamp
     * @param newDateFormat    the new date format
     * @return the string
     */
    public static String changeTimestampToNewFormat(String originDateFormat,
            String originTimestamp, String newDateFormat) {
        return getTime(
                changeTimestampToLong(getSimpleDateFormat(originDateFormat),
                        originTimestamp), newDateFormat);
    }

    /**
     * Change timestamp to new format string.
     *
     * @param isoTimestamp the iso timestamp
     * @param zoneID       the zone id
     * @param newFormat    the new format
     * @return the string
     */
    public static String changeTimestampToNewFormat(String isoTimestamp,
            ZoneId zoneID, DateTimeFormatter newFormat) {
        return ZonedDateTime.parse(isoTimestamp).withZoneSameInstant(zoneID)
                .format(newFormat);
    }

    /**
     * Change timestamp to new format with default zone id string.
     *
     * @param isoTimestamp the iso timestamp
     * @param newFormat    the new format
     * @return the string
     */
    public static String changeTimestampToNewFormatWithDefaultZoneId(
            String isoTimestamp, DateTimeFormatter newFormat) {
        return changeTimestampToNewFormat(isoTimestamp,
                getZoneId(DEFAULT_ZONE_ID), newFormat);
    }

    /**
     * Change timestamp to new format with default zone id string.
     *
     * @param isoTimestamp  the iso timestamp
     * @param newDateFormat the new date format
     * @return the string
     */
    public static String changeTimestampToNewFormatWithDefaultZoneId(
            String isoTimestamp, String newDateFormat) {
        return changeTimestampToNewFormat(isoTimestamp,
                getDateTimeFormatter(newDateFormat));
    }

    /**
     * Change timestamp to new format string.
     *
     * @param isoTimestamp the iso timestamp
     * @param newFormat    the new format
     * @return the string
     */
    public static String changeTimestampToNewFormat(String isoTimestamp,
            DateTimeFormatter newFormat) {
        return ZonedDateTime.parse(isoTimestamp).format(newFormat);
    }

    /**
     * Change timestamp to iso instant string.
     *
     * @param dateFormat the date format
     * @param timestamp  the timestamp
     * @return the string
     */
    public static String changeTimestampToIsoInstant(String dateFormat,
            String timestamp) {
        return getTime(changeTimestampToLong(getSimpleDateFormat(dateFormat),
                timestamp), ISO_INSTANT_Z, UTC_ZONE_ID);
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
     * Gets time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
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
                        Long.valueOf(TimeUnit.MILLISECONDS.toNanos(mills))
                                .intValue(),
                        getZoneId(zoneId))
                .toInstant().toEpochMilli();
    }

    /**
     * Gets time millis with nano.
     *
     * @param year         the year
     * @param month        the month
     * @param dayOfMonth   the day of month
     * @param hour         the hour
     * @param minute       the minute
     * @param second       the second
     * @param nanoOfSecond the nano of second
     * @param zoneId       the zone id
     * @return the time millis with nano
     */
    public static long getTimeMillisWithNano(int year, int month,
            int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
            String zoneId) {
        return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second,
                nanoOfSecond, getZoneId(zoneId)).toInstant().toEpochMilli();
    }

    /**
     * Gets time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param minute     the minute
     * @param second     the second
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            int hour, int minute, int second, String zoneId) {
        return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second,
                0, getZoneId(zoneId)).toInstant().toEpochMilli();
    }

    /**
     * Gets time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
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
     * Gets time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param hour       the hour
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            int hour, String zoneId) {
        return getTimeMillis(year, month, dayOfMonth, hour, 0, 0, zoneId);
    }

    /**
     * Gets time millis.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day of month
     * @param zoneId     the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, int month, int dayOfMonth,
            String zoneId) {
        return getTimeMillis(year, month, dayOfMonth, 0, 0, 0, zoneId);
    }

    /**
     * Gets time millis.
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
     * Gets time millis.
     *
     * @param year   the year
     * @param zoneId the zone id
     * @return the time millis
     */
    public static long getTimeMillis(int year, String zoneId) {
        return getTimeMillis(year, 0, 0, 0, 0, 0, zoneId);
    }

    /**
     * Gets zoned data time.
     *
     * @param timestamp the timestamp
     * @param zoneId    the zone id
     * @return the zoned data time
     */
    public static ZonedDateTime getZonedDataTime(long timestamp,
            String zoneId) {
        return Instant.ofEpochMilli(timestamp).atZone(getZoneId(zoneId));
    }

    /**
     * Gets zoned data time.
     *
     * @param timestamp the timestamp
     * @return the zoned data time
     */
    public static ZonedDateTime getZonedDataTime(long timestamp) {
        return getZonedDataTime(timestamp, DEFAULT_ZONE_ID);
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
     * Gets offset date time.
     *
     * @param timestamp    the timestamp
     * @param zoneOffsetId the zone offset id
     * @return the offset date time
     */
    public static OffsetDateTime getOffsetDateTime(long timestamp,
            String zoneOffsetId) {
        return getOffsetDateTime(timestamp, getZoneOffset(zoneOffsetId));
    }

    /**
     * Gets zone offset.
     *
     * @param zoneOffsetId the zone offset id
     * @return the zone offset
     */
    public static ZoneOffset getZoneOffset(String zoneOffsetId) {
        return JMMap.getOrPutGetNew(zoneOffsetCache, zoneOffsetId,
                () -> ZoneOffset.of(zoneOffsetId));
    }

    /**
     * Gets offset date time.
     *
     * @param timestamp the timestamp
     * @return the offset date time
     */
    public static OffsetDateTime getOffsetDateTime(long timestamp) {
        return getOffsetDateTime(timestamp, DEFAULT_ZONE_OFFSET_ID);
    }

}
