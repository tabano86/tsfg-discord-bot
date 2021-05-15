package listener.command.admin;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.entities.Member;
import org.apache.commons.lang3.StringUtils;

public class KickCommand extends AbstractCommand {

    public KickCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "kick";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("user", "discord user name").withRequiredArg();
        this.getParser().accepts("reason", "reason for kick");
        this.getParser().accepts("help", "get help for the command").forHelp();
    }

    @Override
    public void handle() {
        String userName = String.valueOf(this.getOptionSet().valueOf("user"));
        String reason = String.valueOf(this.getOptionSet().valueOf("reason"));

        Member user = this.getEvent().getGuild().getMemberByTag(userName);

        if (user == null) return;

        try {
            if (StringUtils.isEmpty(reason)) {
                user.kick(reason);
            } else {
                user.kick();
            }
        } catch (Exception ex) {
            this.sendMessageToAuthorChannel(ex.getMessage());
        }
    }
}
