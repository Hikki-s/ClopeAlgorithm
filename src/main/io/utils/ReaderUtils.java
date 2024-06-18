package main.io.utils;

import java.util.Arrays;
import java.util.List;

public class ReaderUtils {
    public static List<String> getItemsFromLine(String line, String delimiter) {
        return Arrays.asList(line.split(delimiter));
    }
}
