package kr.jm.utils;

import kr.jm.utils.enums.SimpleSIUnit;
import kr.jm.utils.helper.JMPath;

import java.nio.file.Path;
import java.text.DecimalFormat;

/**
 * The type File size.
 */
public class FileSize implements Comparable<FileSize> {

	private static final String NoSize = "--";
	private static final DecimalFormat DecimalFormat = new DecimalFormat("#.#");
	private static final String SUFFIX = "B";
	private long size;

	private String siSize;

    /**
	 * Instantiates a new File size.
     *
     * @param size the size
     */
    public FileSize(long size) {
		this.size = size;
		this.siSize = size <= 0 ? NoSize
				: SimpleSIUnit.findSIUnitAndConvertToString(size, DecimalFormat,
						SUFFIX);
	}

    /**
	 * Instantiates a new File size.
     *
     * @param path the path
     */
    public FileSize(Path path) {
		this(JMPath.getSize(path));
	}

    /**
	 * Gets size.
     *
     * @return the size
     */
    public long getSize() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return siSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(FileSize targetFileSize) {
		return Long.compare(size, targetFileSize.getSize());
	}

}
