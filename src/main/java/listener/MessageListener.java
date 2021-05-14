package listener;

import lavaplayer.PlayerManager;
import listener.command.*;
import listener.command.music.PlayCommand;
import listener.command.music.StopCommand;
import listener.command.music.VolumeCommand;
import listener.command.stock.StockQuoteCommand;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.CommandContext;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import service.StockService;
import util.CommandUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MessageListener extends ListenerAdapter {
    private static final String PREFIX = "`";
    private static final Map<String, AbstractCommand> messageMap = new HashMap<>();
    private static final StockService stockService = new StockService();

    public MessageListener(PlayerManager playerManager) {
        // basic
        messageMap.put(RepeatCommand.getText(), new RepeatCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(PrivateMessageCommand.getText(), new PrivateMessageCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(HelpCommand.getText(), new HelpCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(JoinCommand.getText(), new JoinCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(LeaveCommand.getText(), new LeaveCommand(CommandContext.of(playerManager, stockService)));

        // admin
        messageMap.put(KickCommand.getText(), new KickCommand(CommandContext.of(playerManager, stockService)));

        // music
        messageMap.put(PlayCommand.getText(), new PlayCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(StopCommand.getText(), new StopCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(VolumeCommand.getText(), new VolumeCommand(CommandContext.of(playerManager, stockService)));

        // stock
        messageMap.put(StockQuoteCommand.getText(), new StockQuoteCommand(CommandContext.of(playerManager, stockService)));
    }

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {
        String input = messageReceivedEvent.getMessage().getContentStripped();

        if (!CommandUtils.hasValidPrefix(input, PREFIX) || messageReceivedEvent.getAuthor().isBot()) return;

        input = CommandUtils.trimPrefix(input, PREFIX);

        String command = CommandUtils.extractLeadingCommand(input);

        if (StringUtils.isEmpty(command)) {
            log.error("Could not find a command to parse in string: {}", input);
            return;
        }

        if (!messageMap.containsKey(command)) {
            log.error("'{}' command does not exist or is not configured", command);
            return;
        }

        List<String> args = CommandUtils.extractArgs(input, command);

        messageMap.get(command).process(messageReceivedEvent, args);
    }

}