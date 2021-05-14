package listener.command.music;

import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.CommandContext;

@Slf4j
public class StopCommand extends AbstractCommand {

    public StopCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "stop";
    }

    @Override
    public void setOptions() {

    }

    @Override
    public void handle() {
        this.getPlayerManager().getMusicManager(this.getEvent().getGuild()).audioPlayer.stopTrack();
    }
}
