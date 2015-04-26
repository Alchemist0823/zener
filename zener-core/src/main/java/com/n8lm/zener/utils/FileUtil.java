package com.n8lm.zener.utils;

/**
 * Created on 2015/4/26.
 *
 * @author Forrest Sun
 */
public class FileUtil {

    private FileUtil() {
        new AssertionError();
    }

    public static final String getFileExtension(final String filename) {
        if (filename == null) return null;
        final String afterLastSlash = filename.substring(filename.lastIndexOf('/') + 1);
        final int afterLastBackslash = afterLastSlash.lastIndexOf('\\') + 1;
        final int dotIndex = afterLastSlash.indexOf('.', afterLastBackslash);
        return (dotIndex == -1) ? "" : afterLastSlash.substring(dotIndex + 1);
    }

    public static final String getDirectory(final String filename) {
        if (filename == null) return null;
        final int lastIndex = Math.max(filename.lastIndexOf('/') + 1, filename.lastIndexOf('\\') + 1);
        return filename.substring(0, lastIndex);
    }
}
