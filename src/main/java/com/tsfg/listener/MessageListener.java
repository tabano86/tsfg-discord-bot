package com.tsfg.listener;

import com.tsfg.commands.ApplicationCmd;
import com.tsfg.util.CommandUtils;
import com.tsfg.util.EventUtils;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageListener extends ListenerAdapter {
    public static final ThreadLocal<MessageReceivedEvent> messageReceivedEventThreadLocal = new InheritableThreadLocal<>();
    private final ApplicationCmd applicationCmd;
    @Value("${discord.prefix}")
    private String prefix;

    public MessageListener(ApplicationCmd applicationCmd) {
        this.applicationCmd = applicationCmd;
    }

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {
        String input = messageReceivedEvent.getMessage().getContentStripped();

        if (!CommandUtils.hasValidPrefix(input, prefix) || messageReceivedEvent.getAuthor().isBot()) return;

        List<String> args = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(CommandUtils.trimPrefix(input, prefix));
        while (m.find()) {
            args.add(CommandUtils.removeOuterQuotes(m.group(1)));
        }

        messageReceivedEventThreadLocal.set(messageReceivedEvent);

        CommandLine cmd = new CommandLine(applicationCmd);
        StringWriter out = new StringWriter();
        cmd.setOut(new PrintWriter(out));
        cmd.execute(args.toArray(String[]::new));
        String result = out.toString();

        if (StringUtils.isNotEmpty(result)) {
            EventUtils.sendMessageToAuthorChannel(messageReceivedEvent, result);
        }
    }
}