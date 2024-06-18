package main.io.utils;

import java.io.File;
import java.net.URL;

public class FileIOUtils {
    public static File getResourceFile(String fileName) {
        URL url = Thread.currentThread()
                .getContextClassLoader()
                .getResource(fileName);

        if (url == null) {
            throw new IllegalArgumentException(fileName + " is not found.");
        }

        return new File(url.getFile());
    }
}
