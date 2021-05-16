package com.tsfg.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
@UtilityClass
public class EventUtils {
    public static void sendMessageToAuthorChannel(MessageReceivedEvent event, String message) {
        if (EventUtils.getAuthorTextChannel(event) != null) {
            EventUtils.getAuthorTextChannel(event).sendMessage(message).queue();
        }
    }

    public void sendPrivateMessageToAuthor(MessageReceivedEvent event, String message) {
        event.getAuthor().openPrivateChannel().queue((channel) ->
                channel.sendMessageFormat(message).queue());
    }

    public void sendPrivateMessageToAuthor(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessageFormat(message).queue());
    }

    public TextChannel getAuthorTextChannel(MessageReceivedEvent event) {
        return event.getGuild().getTextChannelById(event.getChannel().getId());
    }

    public User getAuthorByTag(MessageReceivedEvent event, String userTag) {
        return event.getJDA().getUserByTag(userTag);
    }
}
