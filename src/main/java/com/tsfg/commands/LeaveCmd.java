package com.tsfg.commands;

import com.tsfg.listener.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "leave", mixinStandardHelpOptions = true)
public class LeaveCmd implements Callable<Integer> {
    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        event.getGuild().getAudioManager().closeAudioConnection();

        return 0;
    }
}
