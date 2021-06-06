/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * This class is used for teams in round-based games.
 */
public class Team {

    /**
     * The name of the team.
     */
    private final String name;
    /**
     * The color code used for the team.
     */
    private final String color;
    /**
     * The spawn of the team
     */
    private Location spawn;
    /**
     * All players in the team.
     */
    private final ArrayList<Player> players;

    /**
     * @param name  The name of the team.
     * @param color The color code used for the team.
     */
    public Team(String name, String color) {
        this.name = name;
        this.color = color;
        this.players = new ArrayList<>();
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

    /**
     * Adds a player to the team.
     */
    public void addPlayer(Player p) {
        this.players.add(p);
    }

    /**
     * @return All players in the team.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return The spawn of the team.
     */
    public Location getSpawn() {
        return spawn;
    }

    /**
     * @param spawn The new spawn.
     */
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}
