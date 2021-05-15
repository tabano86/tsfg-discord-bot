package listener.command.admin;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.entities.Member;

public class MuteCommand extends AbstractCommand {

    public MuteCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "mute";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("user", "discord user name").withRequiredArg();
    }

    @Override
    public void handle() {
        String userName = String.valueOf(this.getOptionSet().valueOf("user"));

        Member user = this.getEvent().getGuild().getMemberByTag(userName);

        if (user == null) return;

        try {
            user.mute(true);
        } catch (Exception ex) {
            this.sendMessageToAuthorChannel(ex.getMessage());
        }
    }
}
