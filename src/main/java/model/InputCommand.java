package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class InputCommand {
    private String command = "";
    private String argString = "";
    private List<String> args = new ArrayList<>();

    public static InputCommand parse(String text) {
        InputCommand inputCommand = new InputCommand();

        if (StringUtils.isNotEmpty(text)) {
            List<String> stringArray = Arrays.stream(text.split(" ")).collect(Collectors.toList());

            inputCommand.command = stringArray.get(0);
            inputCommand.args = stringArray.stream().skip(1).collect(Collectors.toList());
            inputCommand.argString = stringArray.size() > 1 ? String.join(" ", inputCommand.args) : "";
        }

        return inputCommand;
    }
}
