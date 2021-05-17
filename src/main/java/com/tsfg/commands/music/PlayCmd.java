package com.tsfg.commands.music;

import com.tsfg.lavaplayer.PlayerManager;
import com.tsfg.listener.MessageListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Slf4j
@Component
@Command(name = "play", mixinStandardHelpOptions = true)
public class PlayCmd implements Callable<Integer> {
    @Autowired
    private PlayerManager playerManager;

    @Parameters(arity = "0", description = "song name and/or artist search criteria")
    private String searchString;

    @Option(names = {"-f", "--first"}, description = "force song to be next in queue", defaultValue = "", required = false)
    private boolean isFirst = false;

    @Option(names = {"-l", "--list"}, description = "get playlist", defaultValue = "", required = false)
    private boolean isPlaylist = false;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        final TextChannel textChannel = event.getTextChannel();

        if (isPlaylist) {
            this.playerManager.getListFormatted(textChannel);
            return 0;
        }

        event.getGuild().findMembers(m -> m.getIdLong() == event.getAuthor().getIdLong()).onSuccess(members -> {
            if (CollectionUtils.isEmpty(members)) {
                log.error("could not find members");
                return;
            }

            Member member = members.get(0);

            final GuildVoiceState memberVoiceState = member.getVoiceState();

            final AudioManager audioManager = event.getGuild().getAudioManager();
            final VoiceChannel memberVoiceChannel = memberVoiceState.getChannel();

            audioManager.openAudioConnection(memberVoiceChannel);
            this.playerManager.queue(textChannel, String.format("ytsearch: %s", searchString), isFirst);
        });

        return 0;
    }
}
