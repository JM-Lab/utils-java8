
package kr.jm.utils.enums;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.InetAddress;

import org.junit.Ignore;
import org.junit.Test;

import kr.jm.utils.helper.JMStream;

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
	public void testGetHostname() throws Exception {
		System.out.println(OS.getHostname());
	}

	/**
	 * Test get all inet address info list.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetAllInetAddressInfoList() throws Exception {
		System.out.println(OS.getAllInetAddressInfoList());
	}

	/**
	 * Test get ip info.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetIpInfo() throws Exception {
		System.out.println(OS.getIpInfo());
	}

	/**
	 * Test get ip.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetIp() throws Exception {
		System.out.println(OS.getIp());
	}

	/**
	 * Test get ip info list.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetIpInfoList() throws Exception {
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
	public void testGetDefaultDirctoryFile() throws Exception {
		System.out.println(OS.getOS().getRootFileList());
	}

	@Test
	public void testGetAvailableLocalPort() throws Exception {
		assertEquals(100,
				JMStream.numberRange(0, 100, 1)
						.map(i -> OS.getAvailableLocalPort())
						.peek(System.out::println).distinct().count());
	}

}
