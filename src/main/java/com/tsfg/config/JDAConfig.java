package com.tsfg.config;

import com.tsfg.lavaplayer.PlayerManager;
import com.tsfg.listener.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.security.auth.login.LoginException;

@Configuration
public class JDAConfig {
    private final MessageListener messageListener;
    @Value("${discord.token}")
    private String token;

    public JDAConfig(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Bean
    public JDA jda() throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(messageListener);

//        builder.disableCache(CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.watching("All the channels..."));
        builder.enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));
        return builder.build();
    }

    @Bean
    @DependsOn("messageListener")
    public PlayerManager playerManager() {
        return new PlayerManager();
    }
}
