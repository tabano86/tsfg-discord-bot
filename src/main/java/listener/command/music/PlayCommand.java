package listener.command.music;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.InputCommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class PlayCommand extends AbstractCommand {

    public PlayCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "play";
    }

    @Override
    public void handle() {
        final TextChannel textChannel = this.getAuthorTextChannel();

        this.getEvent().getGuild().findMembers(m -> m.getIdLong() == this.getEvent().getAuthor().getIdLong()).onSuccess(members -> {
            if (CollectionUtils.isEmpty(members)) {
                log.error("could not find members");
                return;
            }

            Member member = members.get(0);

            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!member.getVoiceState().inVoiceChannel()) {
                this.sendMessageToAuthorChannel("You need to be in a voice channel for this listener.command to work");
                return;
            }

            final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();
            final VoiceChannel memberVoiceChannel = memberVoiceState.getChannel();

            audioManager.openAudioConnection(memberVoiceChannel);
            this.getAudioPlayerManager().queueSong(textChannel, String.format("ytsearch: %s", this.getInputCommand().getArgString()));
        });

        this.sendMessageToAuthorChannel(super.getInputCommand().getArgString());
    }
}
