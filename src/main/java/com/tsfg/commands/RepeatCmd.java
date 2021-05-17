package com.tsfg.commands;

import com.tsfg.listener.MessageListener;
import com.tsfg.util.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "say", mixinStandardHelpOptions = true)
public class RepeatCmd implements Callable<Integer> {
    @Parameters(arity = "1..*", description = "message text")
    private String messageText;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        MessageUtils.sendPublicMessage(event.getTextChannel(), messageText);

        return 0;
    }
}
