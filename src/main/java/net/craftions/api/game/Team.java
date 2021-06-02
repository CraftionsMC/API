/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game;

public class Team {

    /**
     * The name of the team.
     */
    private String name;
    /**
     * The color code used for the team.
     */
    private String color;

    /**
     * @param name The name of the team.
     * @param color The color code used for the team.
     */
    public Team(String name, String color){
        this.name = name;
        this.color = color;
    }

    /**
     * @return The color code used for the team.
     */
    public String getColor() {
        return color;
    }

    /**
     * @return The name of the team.
     */
    public String getName() {
        return name;
    }
}
