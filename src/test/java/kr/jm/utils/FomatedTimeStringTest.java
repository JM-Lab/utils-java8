package kr.jm.utils;

import kr.jm.utils.time.JMTimeUtil;
import org.junit.Test;

public class FomatedTimeStringTest {

    private final long timestamp = 1395394283524l;

    @Test
    public void testFomatedTimeString() {

        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.ISO_OFFSET_DATE_TIME_MILLS, "Asia/Seoul"));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.ISO_INSTANT_MILLS_TIMEZONE_NAME, "Asia/Seoul"));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.ISO_LOCAL_DATE_TIME_MILLS, "Asia/Seoul"));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS_OFFSET, "Asia/Seoul"));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS_TIMEZONE_NAME,
                "Asia/Seoul"));
        System.out.println(JMTimeUtil.getTime(timestamp,
                JMTimeUtil.BASIC_ISO_DATE_TIME_MILLS, "Asia/Seoul"));
    }

}
