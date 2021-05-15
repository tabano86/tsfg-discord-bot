package listener.command;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.IOException;

public class JoinCommand extends AbstractCommand {

    public JoinCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "join";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("channel", "voice channel name").withRequiredArg();
        this.getParser().accepts("help", "get help for the command").forHelp();
    }

    @Override
    public void handle() {
        VoiceChannel voiceChannel = this.getEvent().getGuild()
                .getVoiceChannelsByName(String.valueOf(this.getOptionSet().valueOf("channel")), true).stream()
                .findFirst().orElse(null);

        final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();

        audioManager.openAudioConnection(voiceChannel);
    }

    @Override
    public void help() throws IOException {
        super.help();
    }
}
