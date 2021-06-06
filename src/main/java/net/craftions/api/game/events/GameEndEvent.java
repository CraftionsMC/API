/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.game.events;

import net.craftions.api.game.Game;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameEndEvent extends Event implements Cancellable {

    public static HandlerList HANDLERS = new HandlerList();
    private final Game game;

    public GameEndEvent(Game game) {
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * @return The game that ends
     */
    public Game getGame() {
        return this.game;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {
        throw new UnsupportedOperationException("You can not cancel this event!");
    }
}
