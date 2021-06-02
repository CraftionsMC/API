package net.craftions.api;

import net.craftions.api.color.ColorCode;
import net.craftions.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Api extends JavaPlugin {

    protected static Api instance;

    @Override
    public void onEnable() {
        System.out.println(ColorCode.to("&7[&cCraftions&9API&7] &aThanks for using &cCraftions&9API"));
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
