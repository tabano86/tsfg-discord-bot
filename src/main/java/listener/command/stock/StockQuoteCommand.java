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
        return "stock-quote";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("s", "ticker symbol").withRequiredArg();
    }

    @SneakyThrows
    @Override
    public void handle() {
        final ObjectMapper mapper = new ObjectMapper();

        Quote quote = this.getStockService().getQuote(String.valueOf(this.getOptionSet().valueOf("s")));

        this.sendMessageToAuthorChannel(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quote));
    }
}