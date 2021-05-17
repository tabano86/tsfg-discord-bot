package com.tsfg.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.tsfg.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.InitializingBean;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerManager implements InitializingBean {
    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void getListFormatted(TextChannel channel) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
        MessageUtils.sendPublicMessage(channel, musicManager.scheduler.getListFormatted());
    }

    public void queue(TextChannel channel, String trackUrl, boolean isFirst) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                log.info("track loaded");

                if (isFirst) {
                    musicManager.scheduler.queueFirst(track);
                } else {
                    musicManager.scheduler.queue(track);
                }

                String msg = MessageFormat.format("Now playing: {0} by {1}", track.getInfo().title, track.getInfo().author);

                MessageUtils.sendPublicMessage(channel, msg);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                log.info("play list loaded");
                final AudioTrack track = playlist.getTracks().stream().findFirst().orElse(null);

                assert track != null;
                String msg = MessageFormat.format("Adding to queue: {0} by {1}", track.getInfo().title, track.getInfo().author);

                MessageUtils.sendPublicMessage(channel, msg);
            }

            @Override
            public void noMatches() {
                log.warn("no matches found");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                log.error("load failed");
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }
}
