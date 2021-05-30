/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.game.events;

import net.craftions.api.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {

    private Game game;

    public GameStartEvent(Game game){
        this.game = game;
    }

    /**
     * @return The game that starts
     */
    public Game getGame(){
        return this.game;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
