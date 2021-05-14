package listener.command;

import listener.AbstractCommand;
import model.CommandContext;

public class RepeatCommand extends AbstractCommand {
    public RepeatCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "repeat";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("m", "message").withRequiredArg();
        this.getParser().accepts("help", "help");
    }

    @Override
    public void handle() {
        this.sendMessageToAuthorChannel(String.valueOf(this.getOptionSet().valueOf("m")));
    }
}
