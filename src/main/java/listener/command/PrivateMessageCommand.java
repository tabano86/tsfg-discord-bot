package listener.command;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.InputCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
public class PrivateMessageCommand extends AbstractCommand {

    public PrivateMessageCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "pm";
    }

    @Override
    public void handle() {
        String[] argument = this.getInputCommand().getArgString().split(" ");
        if (argument.length < 2) {
            this.sendMessageToAuthorChannel("error: not enough arguments provided");
            return;
        }

        User user = this.getAuthorByTag(argument[0]);

        if (user == null) {
            this.sendMessageToAuthorChannel("error: user does not exist");
            return;
        }

        this.sendPrivateMessageToAuthor(user, this.getInputCommand().getArgString());
    }
}
