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
import model.IncomingEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import service.StockService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MessageListener extends ListenerAdapter {
    private static final char PREFIX = '`';
    private static final Map<String, AbstractCommand> messageMap = new HashMap<>();
    private static final StockService stockService = new StockService();

    public MessageListener(PlayerManager playerManager) {
        // basic
        messageMap.put(RepeatCommand.getText(), new RepeatCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(PrivateMessageCommand.getText(), new PrivateMessageCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(HelpCommand.getText(), new HelpCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(JoinCommand.getText(), new JoinCommand(CommandContext.of(playerManager, stockService)));
        messageMap.put(LeaveCommand.getText(), new LeaveCommand(CommandContext.of(playerManager, stockService)));

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

        if (!hasValidPrefix(input)) return;

        if (messageReceivedEvent.getAuthor().isBot()) return;

        IncomingEvent incomingEvent = IncomingEvent.of(messageReceivedEvent);
        incomingEvent.getCommand().setMethod(trimPrefix(incomingEvent.getCommand().getMethod()));

        if (!messageMap.containsKey(incomingEvent.getCommand().getMethod())) {
            log.error("'{}' command does not exist", incomingEvent.getCommand().getMethod());
            return;
        }

        messageMap.get(incomingEvent.getCommand().getMethod()).process(incomingEvent);
    }

    String trimPrefix(String s) {
        return s.substring(1);
    }

    boolean hasValidPrefix(String s) {
        return s.length() > 2 && s.startsWith(String.valueOf(MessageListener.PREFIX));
    }
}