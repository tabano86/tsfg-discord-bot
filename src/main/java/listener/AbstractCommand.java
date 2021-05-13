package listener;

import lavaplayer.PlayerManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.InputCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
@Getter
public abstract class AbstractCommand {
    private InputCommand inputCommand;
    private MessageReceivedEvent event;
    private PlayerManager audioPlayerManager;

    public AbstractCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        this.inputCommand = inputCommand;
        this.event = event;
        this.audioPlayerManager = audioPlayerManager;

        if (inputCommand.getArgString().startsWith("help")) {
            help();
        } else {
            handle();
        }
    }

    public static String getText() {
        return "";
    }

    public abstract void handle();

    public void help() {
        sendMessageToAuthorChannel(getHelpMessage());
    }

    public void sendMessageToAuthorChannel(String message) {
        if (this.getAuthorTextChannel() != null) {
            this.getAuthorTextChannel().sendMessage(message).queue();
        }
    }

    public void sendPrivateMessageToAuthor(String message) {
        this.getAuthor().openPrivateChannel().queue((channel) ->
                channel.sendMessageFormat(message).queue());
    }

    public void sendPrivateMessageToAuthor(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessageFormat(message).queue());
    }

    public TextChannel getAuthorTextChannel() {
        return this.event.getGuild().getTextChannelById(event.getChannel().getId());
    }

    public User getAuthorByTag(String userTag) {
        return this.event.getJDA().getUserByTag(userTag);
    }

    public User getAuthor() {
        return this.event.getAuthor();
    }

    public String getHelpMessage() {
        String cmd = this.inputCommand.getCommand();

        return String.format(
                """
                        command: [%s]
                        description: <empty>
                        usage: <[%s] [message]>""", //
                cmd, cmd);
    }
}
