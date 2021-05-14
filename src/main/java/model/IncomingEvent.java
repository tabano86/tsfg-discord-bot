package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

@ToString
@Getter
@NoArgsConstructor
public class IncomingEvent {
    private Command command;
    private MessageReceivedEvent event;

    public static IncomingEvent of(MessageReceivedEvent event) {
        String text = event.getMessage().getContentStripped();

        IncomingEvent incomingEvent = new IncomingEvent();
        incomingEvent.event = event;

        if (StringUtils.isNotEmpty(text)) {
            incomingEvent.command = new Command("", text).next();
        }

        return incomingEvent;
    }
}
