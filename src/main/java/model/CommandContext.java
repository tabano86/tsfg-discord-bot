package model;

import lavaplayer.PlayerManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import service.StockService;

@Data
@AllArgsConstructor
public class CommandContext {
    private PlayerManager playerManager;
    private StockService stockService;

    public static CommandContext of(PlayerManager playerManager, StockService stockService) {
        return new CommandContext(playerManager, stockService);
    }
}