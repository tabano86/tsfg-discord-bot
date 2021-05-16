package com.tsfg.commands;

import com.tsfg.listener.MessageListener;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "message", mixinStandardHelpOptions = true)
public class MessageCmd implements Callable<Integer> {
    @Parameters(arity = "0", description = "discord user name")
    private String user;

    @Parameters(arity = "1..*", description = "message text")
    private String messageText;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        User user = event.getGuild().getMemberByTag(this.user).getUser();

        user.openPrivateChannel().queue((channel) ->
                channel.sendMessageFormat(messageText).queue());

        return 0;
    }
}
