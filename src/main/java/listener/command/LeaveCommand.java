package listener.command;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends AbstractCommand {

    public LeaveCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "leave";
    }

    @Override
    public void setOptions() {

    }

    @Override
    public void handle() {
        final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();

        audioManager.closeAudioConnection();
    }
}
