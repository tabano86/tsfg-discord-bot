import config.SettingsConfig;
import lavaplayer.PlayerManager;
import listener.MessageListener;
import model.Settings;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;

import javax.security.auth.login.LoginException;

public class Application {
    public static void main(String[] args) throws LoginException {
        Settings settings = SettingsConfig.load();
        JDABuilder builder = JDABuilder.createDefault(settings.getToken());

        PlayerManager playerManager = new PlayerManager();

        MessageListener messageListener = new MessageListener(playerManager);
        builder.addEventListeners(messageListener);

//        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.watching("TV"));
        builder.enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));
        builder.build();
    }
}
