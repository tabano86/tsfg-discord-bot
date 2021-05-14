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
    public void handle() {
        this.sendMessageToAuthorChannel(super.getCommand().getParameter());
    }
}
