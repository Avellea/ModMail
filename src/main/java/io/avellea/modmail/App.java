package io.avellea.modmail;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin implements Listener
{

    public static App instance;

    FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, (Plugin)this);
        this.getCommand("modmail").setExecutor(new ModMail());

        instance = this;

        config.addDefault("Discord_Webhook_URL", "<URL>");
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down");
        instance = null;
    }

}
