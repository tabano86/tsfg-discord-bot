package listener.command.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import listener.AbstractCommand;
import lombok.SneakyThrows;
import model.CommandContext;
import model.Quote;

public class StockQuoteCommand extends AbstractCommand {
    final ObjectMapper mapper = new ObjectMapper();

    public StockQuoteCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "stock";
    }

    public void setOptions() {
        this.getParser().accepts("quote", "get a stock quote");
        this.getParser().accepts("ticker", "ticker symbol").requiredIf("quote").withRequiredArg();
        this.getParser().recognizeAlternativeLongOptions(true);
        this.getParser().accepts("help").forHelp();
    }

    @SneakyThrows
    @Override
    public void handle() {
        if (this.getOptionSet().has("quote")) {
            Quote quote = this.getStockService().getQuote(String.valueOf(this.getOptionSet().valueOf("s")));

            String msg =
                    String.format("""   
                            **[%s]**
                            ```
                            price       $%s ($%s - $%s)
                            volume      %s
                            change      %s percent```
                            """, quote.getSymbol(), quote.getPrice(), quote.getLow(), quote.getHigh(), quote.getVolume(), quote.getChangePercent());

            this.sendMessageToAuthorChannel(msg);
        }
    }
}