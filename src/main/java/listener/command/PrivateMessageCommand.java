package listener.command;

import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.CommandContext;
import net.dv8tion.jda.api.entities.User;

@Slf4j
public class PrivateMessageCommand extends AbstractCommand {

    public PrivateMessageCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "pm";
    }

    @Override
    public void handle() {
        final String parameter = this.getCommand().getParameter();

        String[] argument = parameter.split(" ");

        if (argument.length < 2) {
            this.sendMessageToAuthorChannel("error: not enough arguments provided");
            return;
        }

        User user = this.getAuthorByTag(argument[0]);

        if (user == null) {
            this.sendMessageToAuthorChannel("error: user does not exist");
            return;
        }

        this.sendPrivateMessageToAuthor(user, parameter);
    }
}
