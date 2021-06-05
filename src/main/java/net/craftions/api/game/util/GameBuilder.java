/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game.util;

import jdk.jfr.Experimental;
import net.craftions.api.game.Game;
import net.craftions.api.game.Team;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

@Experimental
public class GameBuilder {

    // Global variables
    /**
     * The name of the game
     */
    private String name;
    /**
     * The color code used for the prefix
     */
    private String colorCode;
    /**
     * The minimum number of players
     */
    private Integer minPlayers;
    /**
     * The time to start the game
     */
    private Integer startTime;
    /**
     * The time to end the game
     */
    private Integer endTime;
    /**
     * The language of the game
     */
    private String languageCode;
    /**
     * The default inventory each player should get when the game starts
     */
    private Inventory defaultInventory;
    /**
     * The place where each player will be teleported to while there aren't enough players on the server
     */
    private Location waitingLobby;
    /**
     * Use Teams
     */
    private boolean useTeams;
    /**
     * The size of each Team
     */
    private Integer teamSize = 0;
    /**
     * Use Team Spawns instead of normal game spawn
     */
    private boolean useTeamSpawns = false;
    /**
     * The in game spawn. You won't see any effect if {link #useTeamSpawns} is set to true and each team has a spawn location.
     */
    private Location spawn = null;
    /**
     * The teams of the game.
     */
    private ArrayList<Team> teams = new ArrayList<>();

    public GameBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GameBuilder setColorCode(String colorCode) {
        this.colorCode = colorCode;
        return this;
    }

    public GameBuilder setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public GameBuilder setStartTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public GameBuilder setEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    public GameBuilder setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public GameBuilder setDefaultInventory(Inventory defaultInventory) {
        this.defaultInventory = defaultInventory;
        return this;
    }

    public GameBuilder setWaitingLobby(Location waitingLobby) {
        this.waitingLobby = waitingLobby;
        return this;
    }

    public GameBuilder setUseTeams(Boolean useTeams) {
        this.useTeams = useTeams;
        return this;
    }

    public GameBuilder setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
        return this;
    }

    public GameBuilder setUseTeamSpawns(Boolean useTeamSpawns) {
        this.useTeamSpawns = useTeamSpawns;
        return this;
    }

    public GameBuilder setSpawn(Location spawn) {
        this.spawn = spawn;
        return this;
    }

    public GameBuilder addTeam(Team t){
        this.teams.add(t);
        return this;
    }

    public Game build() {
        Game game = new Game(this.name, this.colorCode, this.minPlayers, this.startTime, this.endTime, this.languageCode);
        game.setUseTeams(this.useTeams);
        game.setUseTeamSpawns(this.useTeamSpawns);
        game.setSpawn(this.spawn);
        game.setTeamSize(this.teamSize);
        game.setWaitingLobby(this.waitingLobby);
        game.setDefaultInventory(this.defaultInventory);
        for(Team t : this.teams){
            game.addTeam(t);
        }
        System.out.println("TS: " + game.getTeams().size());
        return game;
    }
}
