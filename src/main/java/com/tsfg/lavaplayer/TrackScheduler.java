package com.tsfg.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.text.MessageFormat;
import java.util.ArrayDeque;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final ArrayDeque<AudioTrack> queue = new ArrayDeque<>(20);

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.addLast(track);
        }
    }

    public void queueFirst(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.addFirst(track);
        }
    }

    public String getListFormatted() {
        int idx = 0;
        StringBuilder sb = new StringBuilder();
        for (AudioTrack t : this.queue) {
            sb.append(MessageFormat.format("**{0}.** {1} by {2}", String.valueOf(++idx), t.getInfo().title, t.getInfo().author)).append("\n");
        }
        return sb.toString();
    }

    public void emptyQueue() {
        this.queue.clear();
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
}
