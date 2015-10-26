package kr.jm.utils.helper;

import java.nio.file.Paths;
import java.util.Optional;

import kr.jm.utils.enums.OS;

import org.junit.Test;

public class JMFileTest {

	@Test
	public void testGetRoot() throws Exception {
		System.out.println(JMFile.getRoot());
		System.out.println(JMFile.getRoot().getFileSystem()
				.getRootDirectories());
	}

	@Test
	public void testGetPath() throws Exception {
		System.out.println(JMFile.getChildDirectoryPathStreamAsOpt(
				JMFile.getPath("[/]")).isPresent());
		System.out.println(JMFile.getPath("~/"));
		System.out.println(Paths.get("~"));
		System.out.println(JMFile.getPath(OS.getUserHomeDir()));
	}

	@Test
	public void testGetCurrentPath() throws Exception {
		System.out.println(JMFile.getCurrentPath());
	}

	@Test
	public void testGetUserHome() throws Exception {
		System.out.println(JMFile.getUserHome());
	}

	@Test
	public void testGetRootDirectories() throws Exception {
		System.out.println(JMFile.getRootDirectories());
		JMFile.getRootDirectories().forEach(
				path -> JMFile.getChildPathStreamAsOpt(path).ifPresent(
						s -> s.forEach(p -> System.out.print(p + " "))));
	}

	@Test
	public void testGetChildDirectoryPathStreamAsOpt() throws Exception {
		System.out.println(JMFile.getRootDirectories());
		JMFile.getRootDirectories()
				.forEach(
						path -> JMFile.getChildDirectoryPathStreamAsOpt(path)
								.ifPresent(
										s -> s.forEach(p -> System.out.print(p
												+ " "))));
	}

	@Test
	public void testGetFileStoreList() throws Exception {
		System.out.println(JMFile.getFileStoreList());
		JMFile.getFileStoreList().stream()
				.forEach(fs -> System.out.print(fs.name() + ", "));
		System.out.println();
		JMFile.getFileStoreList().stream()
				.forEach(fs -> System.out.print(fs.type() + ", "));
		System.out.println();
		JMFile.getFileStoreList().stream()
				.forEach(fs -> System.out.print(fs.toString() + ", "));
		System.out.println();
		JMFile.getFileStoreList()
				.stream()
				.forEach(
						fs -> System.out.print(JMFile.getPath(fs.name()) + ", "));
	}

	@Test
	public void testGetFileStorePathList() throws Exception {
		System.out.println(JMFile.getFileStorePathList());
		JMFile.getFileStorePathList().stream()
				.map(JMFile::getChildPathStreamAsOpt)
				.filter(Optional::isPresent).flatMap(Optional::get)
				.forEach(System.out::println);
	}
}
