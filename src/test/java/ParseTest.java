import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTest {
    @SneakyThrows
    @Test
    public void test() {

        String text = "message -u \"Johnny Pappis\" -m hello";

        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(text);
        while (m.find()) {
            list.add(m.group(1));
        }

        CommandLine commandLine = new CommandLine(this);
        commandLine.addSubcommand("i", this);
        commandLine.getHelp();
    }
}
