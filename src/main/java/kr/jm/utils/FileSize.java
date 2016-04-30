package kr.jm.utils;

import java.nio.file.Path;
import java.text.DecimalFormat;

import kr.jm.utils.enums.SimpleSIUnit;
import kr.jm.utils.helper.JMPath;

public class FileSize implements Comparable<FileSize> {

	private static final String NoSize = "--";
	private static final DecimalFormat DecimalFormat = new DecimalFormat("#.#");
	private static final String SUFFIX = "B";
	private long size;

	private String siSize;

	public FileSize(long size) {
		this.size = size;
		this.siSize = size <= 0 ? NoSize
				: SimpleSIUnit.findSIUnitAndConvertToString(size, DecimalFormat,
						SUFFIX);
	}

	public FileSize(Path path) {
		this(JMPath.getSize(path));
	}

	public long getSize() {
		return size;
	}

	@Override
	public String toString() {
		return siSize;
	}

	@Override
	public int compareTo(FileSize targetFileSize) {
		return Long.compare(size, targetFileSize.getSize());
	}

}
