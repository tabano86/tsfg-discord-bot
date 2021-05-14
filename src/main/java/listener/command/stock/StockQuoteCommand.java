package listener.command.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import listener.AbstractCommand;
import lombok.SneakyThrows;
import model.CommandContext;
import model.Quote;

public class StockQuoteCommand extends AbstractCommand {
    public StockQuoteCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "stock";
    }

    @SneakyThrows
    @Override
    public void handle() {
        final ObjectMapper mapper = new ObjectMapper();

        Quote quote = this.getStockService().getQuote(this.getCommand().getParameter());

        this.sendMessageToAuthorChannel(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quote));
    }
}