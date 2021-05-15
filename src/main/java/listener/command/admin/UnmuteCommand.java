package listener.command.admin;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.entities.Member;

public class UnmuteCommand extends AbstractCommand {

    public UnmuteCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "unmute";
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
            user.mute(false);
        } catch (Exception ex) {
            this.sendMessageToAuthorChannel(ex.getMessage());
        }
    }
}
