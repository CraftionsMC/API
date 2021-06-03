/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game.events.bukkit;

import net.craftions.api.game.Game;
import net.craftions.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    public Game game;

    public EventPlayerJoin(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(Language.getMessage(this.game.getLanguageCode(), 0x2).replaceAll("=p", e.getPlayer().getName()));
        if (!this.game.getStarting() && !this.game.getRunning()) {
            if (Bukkit.getOnlinePlayers().size() >= this.game.getMinPlayers()) {
                this.game.start();
            }
        } else {
            if (this.game.getRunning()) {
                e.getPlayer().sendMessage(Language.getMessage(this.game.getLanguageCode(), 0x4));
            }
        }
        if(this.game.getStarting()){
            e.getPlayer().teleport(this.game.getWaitingLobby());
        }

    }
}
