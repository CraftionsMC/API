/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game.exceptions;

public class GameException extends Exception {

    public GameException(String errorMessage){
        super(errorMessage);
    }
}
