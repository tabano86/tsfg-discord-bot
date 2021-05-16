package com.tsfg.commands.music;

import com.tsfg.lavaplayer.PlayerManager;
import com.tsfg.listener.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "stop", mixinStandardHelpOptions = true)
public class StopCmd implements Callable<Integer> {
    @Autowired
    private PlayerManager playerManager;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        this.playerManager.getMusicManager(event.getGuild())
                .audioPlayer.stopTrack();

        return 0;
    }
}
