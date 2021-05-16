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
@Command(name = "volume", mixinStandardHelpOptions = true)
public class VolumeCmd implements Callable<Integer> {
    @Autowired
    private PlayerManager playerManager;

    @Parameters(arity = "0", description = "set's the volume level [0-100]", type = Integer.class)
    private Integer level;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        this.playerManager.getMusicManager(event.getGuild())
                .audioPlayer.setVolume(level);

        return 0;
    }
}
