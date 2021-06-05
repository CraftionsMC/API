/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game;

import java.util.ArrayList;

public class GameManager {

    private static ArrayList<String> usedNames = new ArrayList<>();

    private static void addGame(String name){
        usedNames.add(name);
    }

    public static void removeGame(String name){
        usedNames.remove(name);
    }

    public static boolean createGame(Game game, Boolean force){
        if(usedNames.contains(game.getName())){
            if(force){
                return true;
            }else {
                return false;
            }
        }else {
            addGame(game.getName());
            return true;
        }
    }
}