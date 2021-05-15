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
    public void setOptions() {
        this.getParser().accepts("user", "discord user name").withRequiredArg();
        this.getParser().accepts("message", "message to be sent").requiredIf("user").withRequiredArg();
        this.getParser().accepts("help", "get help for the command").forHelp();
    }

    @Override
    public void handle() {
        User user = this.getAuthorByTag(String.valueOf(this.getOptionSet().valueOf("user")));

        if (user == null) {
            this.sendMessageToAuthorChannel("error: user does not exist");
            return;
        }

        this.sendPrivateMessageToAuthor(user, String.valueOf(this.getOptionSet().valueOf("message")));
    }
}
