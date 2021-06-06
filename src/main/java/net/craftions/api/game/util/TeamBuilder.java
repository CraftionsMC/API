/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game.util;

import net.craftions.api.game.Team;
import org.bukkit.Location;

public class TeamBuilder {

    /**
     * The name of the team.
     */
    private String name;
    /**
     * The color code used for the team.
     */
    private String color;
    /**
     * The spawn of the team
     */
    private Location spawn;

    public TeamBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TeamBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public TeamBuilder setSpawn(Location spawn) {
        this.spawn = spawn;
        return this;
    }

    public Team build() {
        Team t = new Team(this.name, this.color);
        t.setSpawn(this.spawn);
        return t;
    }
}
