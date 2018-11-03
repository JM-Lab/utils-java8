
package kr.jm.utils;

/**
 * The type Auto string builder.
 */
public class AutoStringBuilder {

	private String autoAppendingString;

	private StringBuilder sb;

    /**
     * Instantiates a new Auto string builder.
     *
     * @param autoAppendingString the auto appending string
     */
    public AutoStringBuilder(String autoAppendingString) {
		this.autoAppendingString = autoAppendingString;
		sb = new StringBuilder();
	}

    /**
     * Append if exists auto string builder.
     *
     * @param appendingObjects the appending objects
     * @return the auto string builder
     */
    public AutoStringBuilder appendIfExists(Object... appendingObjects) {
		for (Object object : appendingObjects)
			if (object != null && !"".equals(object))
				this.append(object.toString());
		return this;
	}

    /**
     * Append auto string builder.
     *
     * @param string the string
     * @return the auto string builder
     */
    public AutoStringBuilder append(String string) {
		sb.append(string).append(autoAppendingString);
		return this;
	}

    /**
     * Append auto string builder.
     *
     * @param strings the strings
     * @return the auto string builder
     */
    public AutoStringBuilder append(String... strings) {
		for (String s : strings)
			append(s);
		return this;
	}

    /**
     * Append auto string builder.
     *
     * @param object the object
     * @return the auto string builder
     */
    public AutoStringBuilder append(Object object) {
		sb.append(object.toString()).append(autoAppendingString);
		return this;
	}

    /**
     * Append auto string builder.
     *
     * @param objects the objects
     * @return the auto string builder
     */
    public AutoStringBuilder append(Object... objects) {
		for (Object o : objects)
			append(o);
		return this;
	}

    /**
     * Gets string builder.
     *
     * @return the string builder
     */
    public StringBuilder getStringBuilder() {
		return sb;
	}

    /**
     * Remove last auto appending string auto string builder.
     *
     * @return the auto string builder
     */
    public AutoStringBuilder removeLastAutoAppendingString() {
		sb = sb.delete(sb.length() - autoAppendingString.length(), sb.length());
		return this;
	}

    /**
     * Auto to string string.
     *
     * @return the string
     */
    public String autoToString() {
		return removeLastAutoAppendingString().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return sb.toString();
	}

}
