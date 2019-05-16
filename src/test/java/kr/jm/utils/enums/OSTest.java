package kr.jm.utils.enums;

import kr.jm.utils.helper.JMStream;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

public class OSTest {

    private static final String FILE_PATH = "";

    @Ignore
    @Test
    public void test() {
        OS.getOS().open(new File(FILE_PATH));
    }

    @Test
    public void testGetHostname() {
        System.out.println(OS.getHostname());
    }

    @Test
    public void testGetAllInetAddressInfoList() {
        System.out.println(OS.getAllInetAddressInfoList());
    }

    @Test
    public void testGetIpInfo() {
        System.out.println(OS.getIpInfo());
    }

    @Test
    public void testGetIp() {
        System.out.println(OS.getIp());
    }

    @Ignore
    @Test
    public void testGetIpInfoList() {
        System.out.println(OS.getIpInfoList());
        System.out.println(
                OS.getAllInetAddressInfoStream().map(InetAddress::getHostName)
                        .filter(s -> !s.contains(":")).findFirst().get());
    }

    @Test
    public void testGetDefaultDirctoryFile() {
        System.out.println(OS.getOS().getRootFileList());
    }

    @Test
    public void testGetAvailableLocalPort() {
        assertEquals(100,
                JMStream.numberRange(0, 100, 1)
                        .map(i -> OS.getAvailableLocalPort())
                        .peek(System.out::println).distinct().count());
    }

}
