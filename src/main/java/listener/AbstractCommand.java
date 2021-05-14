package listener;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import lavaplayer.PlayerManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.CommandContext;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import service.StockService;
import util.CommandHelpFormatter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Getter
public abstract class AbstractCommand {
    private final PlayerManager playerManager;
    private final StockService stockService;
    private OptionParser parser = new OptionParser();

    private MessageReceivedEvent event;

    private OptionSet optionSet;

    public AbstractCommand(CommandContext commandContext) {
        this.playerManager = commandContext.getPlayerManager();
        this.stockService = commandContext.getStockService();
        this.parser.formatHelpWith(new CommandHelpFormatter());

        assert StringUtils.isNoneEmpty(getCommandName());
    }

    public static String getCommandName() {
        return "";
    }

    public abstract void setOptions();

    public void process(MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.optionSet = parser.parse(args.toArray(String[]::new));
    }

    public abstract void handle();

    public void help() throws IOException {
        sendMessageToAuthorChannel(this.getHelpMessage());
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

    public String getHelpMessage() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.parser.printHelpOn(stream);
        return stream.toString();
    }
}
