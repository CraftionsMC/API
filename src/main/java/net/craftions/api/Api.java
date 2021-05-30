package net.craftions.api;

import net.craftions.api.language.Language;
import org.bukkit.plugin.java.JavaPlugin;

public final class Api extends JavaPlugin {

    protected static Api instance;

    @Override
    public void onEnable() {
        Language.initMaps();
        instance = this;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Api getInstance() {
        return instance;
    }
}
