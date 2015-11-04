package kr.jm.utils.enums;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class OSTest {

	private static final String FILE_PATH = "";

	@Ignore
	@Test
	public void test() {
		OS.getOS().open(new File(FILE_PATH));
	}

}
