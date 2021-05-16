package com.tsfg.commands;

import com.tsfg.listener.MessageListener;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "join", mixinStandardHelpOptions = true)
public class JoinCmd implements Callable<Integer> {
    @Parameters(arity = "1..*", description = "message text")
    private String channel;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        VoiceChannel voiceChannel = event.getGuild()
                .getVoiceChannelsByName(channel, true).stream()
                .findFirst().orElse(null);

        final AudioManager audioManager = event.getGuild().getAudioManager();

        audioManager.openAudioConnection(voiceChannel);

        return 0;
    }
}
