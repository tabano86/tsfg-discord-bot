package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private String method;
    private String parameter;

    public Command next() {
        Command command_ = new Command();

        String[] split = parameter.split(" ", 2);

        if (split.length == 0) return null;

        command_.method = split[0];

        if (split.length > 1) command_.parameter = split[1];

        return command_;
    }
}