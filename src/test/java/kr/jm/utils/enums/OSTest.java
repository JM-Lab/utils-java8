
package kr.jm.utils.enums;

import kr.jm.utils.helper.JMStream;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

/**
 * The Class OSTest.
 */
public class OSTest {

	private static final String FILE_PATH = "";

	/**
	 * Test.
	 */
	@Ignore
	@Test
	public void test() {
		OS.getOS().open(new File(FILE_PATH));
	}

	/**
	 * Test get hostname.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetHostname() {
		System.out.println(OS.getHostname());
	}

	/**
	 * Test get all inet address info list.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetAllInetAddressInfoList() {
		System.out.println(OS.getAllInetAddressInfoList());
	}

	/**
	 * Test get ip info.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetIpInfo() {
		System.out.println(OS.getIpInfo());
	}

	/**
	 * Test get ip.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetIp() {
		System.out.println(OS.getIp());
	}

	/**
	 * Test get ip info list.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetIpInfoList() {
		System.out.println(OS.getIpInfoList());
		System.out.println(
				OS.getAllInetAddressInfoStream().map(InetAddress::getHostName)
						.filter(s -> !s.contains(":")).findFirst().get());
	}

	/**
	 * Test get default dirctory file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetDefaultDirctoryFile() {
		System.out.println(OS.getOS().getRootFileList());
	}

	/**
	 * Test get available local port.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
    public void testGetAvailableLocalPort() {
		assertEquals(100,
				JMStream.numberRange(0, 100, 1)
						.map(i -> OS.getAvailableLocalPort())
						.peek(System.out::println).distinct().count());
	}

}
