package listener.command.music;

import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.CommandContext;

@Slf4j
public class VolumeCommand extends AbstractCommand {

    public VolumeCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "volume";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("v", "volume level (1 - 100)").withRequiredArg().ofType(Integer.class);
    }

    @Override
    public void handle() {
        this.getPlayerManager().getMusicManager(this.getEvent().getGuild())
                .audioPlayer.setVolume(Integer.parseInt(String.valueOf(this.getOptionSet().valueOf("v"))));
    }
}
