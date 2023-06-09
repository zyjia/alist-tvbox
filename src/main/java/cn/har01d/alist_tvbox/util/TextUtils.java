package cn.har01d.alist_tvbox.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TextUtils {

    private static final List<String> NUMBERS = Arrays.asList("零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
    private static final Pattern NUMBER = Pattern.compile("第(\\d+\\.?\\d*)季");
    private static final Pattern NUMBER2 = Pattern.compile("(第.+季)");

    public static String fixName(String name) {
        int index = name.lastIndexOf('/');
        String newName = name;
        if (index > -1) {
            newName = newName.substring(index + 1);
        }
        if (newName.endsWith(")")) {
            index = newName.lastIndexOf('(');
            if (index > 0) {
                newName = newName.substring(0, index);
            }
        }
        Matcher m = NUMBER.matcher(newName);
        if (m.find()) {
            String text = m.group(1);
            if (newName.charAt(m.start() - 1) != ' ') {
                newName = newName.replace("第" + text + "季", " 第" + text + "季");
            }
            int num = Integer.parseInt(text);
            newName = newName.replace(text, NUMBERS.get(num));
        } else {
            m = NUMBER2.matcher(newName);
            if (m.find()) {
                String text = m.group(1);
                if (newName.charAt(m.start() - 1) != ' ') {
                    newName = newName.replace(text, " " + text);
                }
            }
        }
        newName = newName.trim();
        if (!name.equals(newName)) {
            log.info("name: {} -> {}", name, newName);
        }
        return newName;
    }

}
