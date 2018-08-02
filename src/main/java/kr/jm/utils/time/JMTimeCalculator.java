
package kr.jm.utils.time;

import kr.jm.utils.enums.Milliseconds;

/**
 * The type Jm time calculator.
 */
public class JMTimeCalculator {

	private static final long aSecond = Milliseconds.SECOND.getValue();
	private static final long aMinute = Milliseconds.MINUTE.getValue();
	private static final long anHour = Milliseconds.HOUR.getValue();
	private static final long aDay = Milliseconds.DAY.getValue();
	private static final long aWeek = Milliseconds.WEEK.getValue();

    /**
     * Gets timestamp minus timestamp.
     *
     * @param targetTimestamp the target timestamp
     * @param timestamp       the timestamp
     * @return the timestamp minus timestamp
     */
    public static long getTimestampMinusTimestamp(long targetTimestamp,
			long timestamp) {
		return targetTimestamp - timestamp;
	}

    /**
     * Gets current minus timestamp.
     *
     * @param timeMillis the time millis
     * @return the current minus timestamp
     */
    public static long getCurrentMinusTimestamp(long timeMillis) {
		return System.currentTimeMillis() - timeMillis;
	}

    /**
     * Gets current minus timestamp.
     *
     * @param isoTimestampString the iso timestamp string
     * @return the current minus timestamp
     */
    public static long getCurrentMinusTimestamp(String isoTimestampString) {
		return getCurrentMinusTimestamp(
				JMTimeUtil.changeIsoTimestampToLong(isoTimestampString));
	}

    /**
     * Gets timestamp minus parameters.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfWeeks      the num of weeks
     * @param numOfDays       the num of days
     * @param numOfHours      the num of hours
     * @param numOfMinutes    the num of minutes
     * @param numOfSeconds    the num of seconds
     * @return the timestamp minus parameters
     */
    public static long getTimestampMinusParameters(long targetTimestamp,
			int numOfWeeks, int numOfDays, int numOfHours, int numOfMinutes,
			int numOfSeconds) {
		long sumOfParameters =
				numOfWeeks * aWeek + numOfDays * aDay + numOfHours * anHour
						+ numOfMinutes * aMinute + numOfSeconds * aSecond;
		return targetTimestamp - sumOfParameters;
	}

    /**
     * Gets current timestamp minus parameters.
     *
     * @param numOfWeeks   the num of weeks
     * @param numOfDays    the num of days
     * @param numOfHours   the num of hours
     * @param numOfMinutes the num of minutes
     * @param numOfSeconds the num of seconds
     * @return the current timestamp minus parameters
     */
    public static long getCurrentTimestampMinusParameters(int numOfWeeks,
			int numOfDays, int numOfHours, int numOfMinutes, int numOfSeconds) {
		return getTimestampMinusParameters(System.currentTimeMillis(),
				numOfWeeks, numOfDays, numOfHours, numOfMinutes, numOfSeconds);
	}

    /**
     * Gets timestamp minus seconds.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfSeconds    the num of seconds
     * @return the timestamp minus seconds
     */
    public static long getTimestampMinusSeconds(long targetTimestamp,
			int numOfSeconds) {
		return targetTimestamp - numOfSeconds * aSecond;
	}

    /**
     * Gets timestamp minus minutes.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfMinutes    the num of minutes
     * @return the timestamp minus minutes
     */
    public static long getTimestampMinusMinutes(long targetTimestamp,
			int numOfMinutes) {
		return targetTimestamp - numOfMinutes * aMinute;
	}

    /**
     * Gets timestamp minus hours.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfHours      the num of hours
     * @return the timestamp minus hours
     */
    public static long getTimestampMinusHours(long targetTimestamp,
			int numOfHours) {
		return targetTimestamp - numOfHours * anHour;
	}

    /**
     * Gets timestamp minus days.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfDays       the num of days
     * @return the timestamp minus days
     */
    public static long getTimestampMinusDays(long targetTimestamp,
			int numOfDays) {
		return targetTimestamp - numOfDays * aDay;
	}

    /**
     * Gets timestamp minus weeks.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfWeeks      the num of weeks
     * @return the timestamp minus weeks
     */
    public static long getTimestampMinusWeeks(long targetTimestamp,
			int numOfWeeks) {
		return targetTimestamp - numOfWeeks * aWeek;
	}

    /**
     * Gets timestamp plus minutes.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfMinutes    the num of minutes
     * @return the timestamp plus minutes
     */
    public static long getTimestampPlusMinutes(long targetTimestamp,
			int numOfMinutes) {
		return targetTimestamp + numOfMinutes * aMinute;
	}

    /**
     * Gets timestamp plus hours.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfHours      the num of hours
     * @return the timestamp plus hours
     */
    public static long getTimestampPlusHours(long targetTimestamp,
			int numOfHours) {
		return targetTimestamp + numOfHours * anHour;
	}

    /**
     * Gets timestamp plus days.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfDays       the num of days
     * @return the timestamp plus days
     */
    public static long getTimestampPlusDays(long targetTimestamp,
			int numOfDays) {
		return targetTimestamp + numOfDays * aDay;
	}

    /**
     * Gets timestamp plus weeks.
     *
     * @param targetTimestamp the target timestamp
     * @param numOfWeeks      the num of weeks
     * @return the timestamp plus weeks
     */
    public static long getTimestampPlusWeeks(long targetTimestamp,
			int numOfWeeks) {
		return targetTimestamp + numOfWeeks * aWeek;
	}

}
