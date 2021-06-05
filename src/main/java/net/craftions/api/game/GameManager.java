/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game;

import java.util.ArrayList;

public class GameManager {

    private static ArrayList<Game> games = new ArrayList<>();

    private static void addGame(Game game){
        games.add(game);
    }

    public static void removeGame(Game game){
        games.remove(game);
    }

    /**
     * @param game The game instance
     * @param force If true this function will always return true.
     * @return True if the game could be created. Overwritten if force is true
     */
    public static boolean createGame(Game game, Boolean force){
        if(games.contains(game)){
            if(force){
                return true;
            }else {
                return false;
            }
        }else {
            addGame(game);
            return true;
        }
    }

    /**
     * @param name The name of the game
     * @return The game or null.
     */
    public static Game getGameByName(String name){
        for(Game g : games){
            if(g.getName().equals(name)){
                return g;
            }
        }
        return null;
    }
}