package listener;

import lavaplayer.PlayerManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Command;
import model.CommandContext;
import model.IncomingEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import service.StockService;

@Slf4j
@Getter
public abstract class AbstractCommand {
    private final PlayerManager playerManager;
    private final StockService stockService;
    private Command command;
    private MessageReceivedEvent event;

    public AbstractCommand(CommandContext commandContext) {
        this.playerManager = commandContext.getPlayerManager();
        this.stockService = commandContext.getStockService();
    }

    public static String getText() {
        return "";
    }

    public void process(IncomingEvent incomingEvent) {
        this.event = incomingEvent.getEvent();
        this.command = incomingEvent.getCommand();

        if (incomingEvent.getCommand().getMethod().startsWith("help")) {
            help();
        } else {
            handle();
        }
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
        String cmd = this.getCommand().getMethod();

        return String.format(
                """
                        command: [%s]
                        description: <empty>
                        usage: <[%s] [message]>""", //
                cmd, cmd);
    }
}
