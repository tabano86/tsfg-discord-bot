package com.tsfg.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

@Slf4j
@UtilityClass
public class MessageUtils {
    public void sendPrivateMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessageFormat(wrap(message)).queue());
    }

    public void sendPublicMessage(TextChannel channel, String message) {
        channel.sendMessage(wrap(message)).queue();
    }

    String wrap(String message) {
        return String.format("""
                ```apache
                %s
                ```
                """, message);
    }
}
