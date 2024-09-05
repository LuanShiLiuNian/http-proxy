package cn.luanshiliunian.utils;

public class FlowUtil {
    public static String humanFileSize(long size) {
        if (size == 0) {
            return "0 B";
        }

        String[] units = {"B", "kB", "MB", "GB", "TB"};
        int i = (int) (Math.floor(Math.log(size) / Math.log(1024)));
        if (units[i].equals("B")) {
            return String.format("%d %s", (int) (size >> (i * 10)), units[i]);
        }
        return String.format("%.2f %s", (double) (size >> (i * 10)), units[i]);
    }
}
