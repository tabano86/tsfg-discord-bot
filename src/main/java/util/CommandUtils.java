package util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class CommandUtils {
    public static List<String> parse(String text) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(text);
        while (m.find()) {
            list.add(removeOuterQuotes(m.group(1).trim()));
        }
        return list;
    }

    public static String trimPrefix(String s, String prefix) {
        return s.substring(prefix.length());
    }

    public static boolean hasValidPrefix(String s, String prefix) {
        return s.length() > prefix.length() && s.startsWith(prefix);
    }

    public static String extractLeadingCommand(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ' || i == text.length() - 1) {
                return text.substring(0, i + 1).trim();
            }
        }
        return null;
    }

    public static List<String> extractArgs(String text, String command) {
        return parse(text.substring(command.length()));
    }

    public static String removeOuterQuotes(String text) {
        for (int left = 0; left < text.length() / 2; left++) {
            int right = text.length() - left - 1;

            if (!(text.charAt(left) == text.charAt(right) && (text.charAt(right) == '"' || text.charAt(right) == '\''))) {
                return text.substring(left, right + 1);
            }
        }

        return text;
    }
}
