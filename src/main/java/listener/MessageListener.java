package listener;

import lavaplayer.PlayerManager;
import listener.command.*;
import listener.command.music.PlayCommand;
import listener.command.music.StopCommand;
import listener.command.music.VolumeCommand;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.InputCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MessageListener extends ListenerAdapter {
    private static final char PREFIX = '`';
    private static final Map<String, Class<? extends AbstractCommand>> messageMap = new HashMap<>();
    private final PlayerManager playerManager;

    public MessageListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
        // basic
        messageMap.put(RepeatCommand.getText(), RepeatCommand.class);
        messageMap.put(PrivateMessageCommand.getText(), PrivateMessageCommand.class);
        messageMap.put(HelpCommand.getText(), HelpCommand.class);
        messageMap.put(JoinCommand.getText(), JoinCommand.class);
        messageMap.put(LeaveCommand.getText(), LeaveCommand.class);

        // music
        messageMap.put(PlayCommand.getText(), PlayCommand.class);
        messageMap.put(StopCommand.getText(), StopCommand.class);
        messageMap.put(VolumeCommand.getText(), VolumeCommand.class);
    }

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String input = event.getMessage().getContentStripped();

        if (!hasValidPrefix(input)) return;

        if (event.getAuthor().isBot()) return;


        input = trimPrefix(input);

        InputCommand inputCommand = InputCommand.parse(input);

        if (!messageMap.containsKey(inputCommand.getCommand())) {
            log.error("'{}' command does not exist", inputCommand.getCommand());
            return;
        }

        createInstance(messageMap.get(inputCommand.getCommand()), inputCommand, event, playerManager);
    }

    String trimPrefix(String s) {
        return s.substring(1);
    }

    boolean hasValidPrefix(String s) {
        return s.length() > 2 && s.startsWith(String.valueOf(MessageListener.PREFIX));
    }

    public AbstractCommand createInstance(Class<?> c, Object... args) throws Exception {
        Constructor<?> ctor = c.getConstructor(InputCommand.class, MessageReceivedEvent.class, PlayerManager.class);
        return ((AbstractCommand) ctor.newInstance(args));
    }
}