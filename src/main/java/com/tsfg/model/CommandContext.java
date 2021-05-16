package com.tsfg.model;

import com.tsfg.lavaplayer.PlayerManager;
import com.tsfg.service.StockService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandContext {
    private PlayerManager playerManager;
    private StockService stockService;

    public static CommandContext of(PlayerManager playerManager, StockService stockService) {
        return new CommandContext(playerManager, stockService);
    }
}