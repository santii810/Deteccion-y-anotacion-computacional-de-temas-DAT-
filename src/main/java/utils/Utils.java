package utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static List<String> depRelPermitidos = Arrays.asList("acl", "advcl", "advmod", "appos", "aux", "ccomp",
            "cop", "csubj", "discourse", "dislocated", "expl", "iobj", "mark", "nsubj", "obj", "obl", "root",
            "vocative", "xcomp");



    public static String getElapsedTime(long start, long end) {
        long elapsed = end - start;
        return String.format("%02dm %02ds %02dms",
                TimeUnit.MILLISECONDS.toMinutes(elapsed) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(elapsed) % TimeUnit.MINUTES.toSeconds(1),
                TimeUnit.MILLISECONDS.toMillis(elapsed) % TimeUnit.SECONDS.toMillis(1));
    }

    public static String extractRefFromFilename(String filename) {
        filename = new File(filename).getName().split("\\.")[0];
        if (filename.contains("-")) return filename.split("-")[1];
        if (filename.contains("_")) return filename.split("_")[1];
        return filename;
    }
}
