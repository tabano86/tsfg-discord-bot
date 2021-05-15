package listener.command.music;

import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.CommandContext;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class PlayCommand extends AbstractCommand {

    public PlayCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "play";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("search", "search by song track or artist name (or both!)").withRequiredArg().ofType(Integer.class);
        this.getParser().accepts("help", "get help for the command").forHelp();
    }

    @Override
    public void handle() {
        final TextChannel textChannel = this.getAuthorTextChannel();
        final String searchString = String.valueOf(this.getOptionSet().valueOf("search"));

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
            this.getPlayerManager().queueSong(textChannel, String.format("ytsearch: %s", searchString));
        });

        this.sendMessageToAuthorChannel(searchString);
    }
}
