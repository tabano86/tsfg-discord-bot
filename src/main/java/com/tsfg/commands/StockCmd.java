package com.tsfg.commands;

import com.tsfg.listener.MessageListener;
import com.tsfg.service.StockService;
import com.tsfg.util.EventUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "stock", mixinStandardHelpOptions = true)
public class StockCmd implements Callable<Integer> {
    @Autowired
    private StockService stockService;

    @Parameters(arity = "1..*", description = "ticker symbol")
    private String ticker;

    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        MessageReceivedEvent event = MessageListener.messageReceivedEventThreadLocal.get();

        stockService.getQuote(ticker).subscribe(quote -> {
            String msg =
                    String.format("""
                            **[%s]**
                            ```apache
                            price       $%s ($%s - $%s)
                            volume      %s
                            change      %s```
                            """, quote.getSymbol(), quote.getPrice(), quote.getLow(), quote.getHigh(), quote.getVolume(), quote.getChangePercent());
            EventUtils.sendMessageToAuthorChannel(event, msg);
        });

        return 0;
    }
}
