/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.game;

import net.craftions.api.Api;
import net.craftions.api.color.ColorCode;
import net.craftions.api.config.Config;
import net.craftions.api.game.events.GameEndEvent;
import net.craftions.api.game.events.GameStartEvent;
import net.craftions.api.game.events.bukkit.EventPlayerJoin;
import net.craftions.api.game.exceptions.GameException;
import net.craftions.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * This class is used for round-based team games.
 */
public class Game {

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
     * The configuration of the game
     */
    private Config config;
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
    // Teams
    /**
     * Registered teams
     */
    private ArrayList<Team> teams = new ArrayList<>();
    /**
     * Use Teams
     */
    private boolean useTeams;
    /**
     * The size of each Team
     */
    private Integer teamSize;
    /**
     * Use Team Spawns instead of normal game spawn
     */
    private boolean useTeamSpawns;
    /**
     * The in game spawn. You won't see any effect if {link #useTeamSpawns} is set to true and each team has a spawn location.
     */
    private Location spawn;


    // Timer variables
    private Integer _startTimer;
    private Integer _startTimerId;
    private Integer _endTimer;
    private Integer _endTimerId;

    // State variables
    private Boolean isStarting = false;
    private Boolean isRunning = false;

    /**
     * @param name       The name of the game
     * @param colorCode  The color code used for the prefix
     * @param minPlayers The minimum number of players
     * @param startTime  The time after the game starts when enough players joined
     * @param endTime    The time after the game ends
     */
    public Game(String name, String colorCode, Integer minPlayers, Integer startTime, Integer endTime, String languageCode) {
        this.name = name;
        this.colorCode = colorCode;
        this.minPlayers = minPlayers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.languageCode = languageCode;
        if(GameManager.createGame(this, false)){

        }else {
            try {
                throw new GameException("Could not create the game! The name is already taken!");
            } catch (GameException e) {
                e.printStackTrace();
            }
        }
        this.initialize();
    }

    /**
     * Creates a Game Instance with values from the given config instance
     *
     * @param configName The name of the Configuration Instance
     */
    public Game(String configName) {
        this.config = Config.getInstance(configName);
        this.name = (String) this.config.get("name");
        this.colorCode = ColorCode.from((String) this.config.get("colorCode"));
        this.minPlayers = (Integer) config.get("minPlayers");
        this.startTime = (Integer) config.get("startTime");
        this.endTime = (Integer) config.get("endTime");
        this.languageCode = (String) config.get("languageCode");
        this.initialize();
    }

    /**
     * Creates a Game Instance from a Config File
     *
     * @return The created game
     */
    public static Game fromConfigFile(String gameName) {
        new Config(new File("plugins/" + gameName + "/game.config.yml"), gameName + ":temp-loaded:");
        return new Game(gameName + ":temp-loaded:");
    }

    /**
     * Saves the current Game options (name, color code, ...) to the configuration
     */
    public void saveToConfig() {
        if (this.config == null) {
            File root = new File("plugins/" + this.name);
            File configFile = new File(root.getPath() + "/game.config.yml");
            if (!root.exists()) {
                root.mkdirs();
            }
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.config = new Config(configFile, "_temp:" + UUID.randomUUID());
            this.config.set("name", this.name);
            this.config.set("colorCode", this.colorCode);
            this.config.set("minPlayers", this.minPlayers);
            this.config.set("startTime", this.startTime);
            this.config.set("endTime", this.endTime);
            this.config.set("languageCode", this.languageCode);
            this.config.reload(true);
            this.config = null;
        } else {
            throw new UnsupportedOperationException("You can not use this method if you have loaded the settings from a config.");
        }
    }

    /**
     * Start the game with its default values
     */
    public void start() {
        _startTimer = startTime;
        _startTimerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Api.getInstance(), new Runnable() {
            @Override
            public void run() {
                _startTimer--;
                if (_startTimer == 0) {
                    Bukkit.getScheduler().cancelTask(_startTimerId);
                    startFinal();
                } else if (_startTimer.toString().endsWith("0") || _startTimer.toString().endsWith("5") || _startTimer < 10) {
                    Bukkit.broadcastMessage(Language.getMessage(languageCode, 0x0).replaceAll("=s", _startTimer.toString()));
                }
            }
        }, 0, 20L);
    }

    /**
     * Start the game with a specific start time (used for /start)
     *
     * @param time The time after the game starts.
     */
    public void start(int time) {
        this.startTime = time;
        this.start();
    }

    /**
     * Used for choosing the teams
     */
    protected void processTeams() {
        if (this.useTeams) {
            HashMap<Team, Integer> _teams = new HashMap<>();
            Random random = new Random();
            for (Player p : Bukkit.getOnlinePlayers()) {
                while (true) {
                    int r = random.nextInt(this.teams.size());
                    if (!_teams.get(this.teams.get(r)).equals(this.teamSize)) {
                        this.teams.get(r).addPlayer(p);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @param p The player
     * @return The team of the player or null
     */
    public Team getPlayerTeam(Player p) {
        if (!this.getStarting() && this.getRunning()) {
            for (Team t : this.getTeams()) {
                if (t.getPlayers().contains(p)) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * Starts the final game (after countdown)
     */
    protected void startFinal() {
        this.processTeams();
        if (this.getDefaultInventory() != null) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getInventory().setContents(this.getDefaultInventory().getContents());
                if (this.useTeamSpawns) {
                    p.teleport(this.getPlayerTeam(p).getSpawn());
                } else {
                    p.teleport(this.getSpawn());
                }
            }
        }
        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
        this.end();
    }

    protected void initialize() {
        // listeners
        Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(this), Api.getInstance());
    }

    /**
     * Ends the game after the given time.
     */
    public void end() {
        _endTimer = endTime;
        _endTimerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Api.getInstance(), new Runnable() {
            @Override
            public void run() {
                _endTimer--;
                if (_endTimer == 0) {
                    Bukkit.getScheduler().cancelTask(_endTimerId);
                    endFinal();
                } else if (_endTimer.toString().endsWith("0") || _endTimer.toString().endsWith("5") || _endTimer < 10) {
                    Bukkit.broadcastMessage(Language.getMessage(languageCode, 0x1).replaceAll("=s", _endTimer.toString()));
                }
            }
        }, 0, 20L);
    }

    /**
     * Ends the final game (after countdown)
     */
    protected void endFinal() {
        Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
    }

    /**
     * @return true if the game is currently starting
     */
    public Boolean getStarting() {
        return isStarting;
    }

    /**
     * @return true if the game is currently running
     */
    public Boolean getRunning() {
        return isRunning;
    }

    /**
     * Checks if a game can be loaded from a config
     *
     * @param gameName The name of the saved game
     * @return true if the game can be loaded from a config
     */
    public static boolean canLoadFromConfig(String gameName) {
        return new File("plugins/" + gameName + "/game.config.yml").exists();
    }

    /**
     * @return The name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * @return The color code of the game
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * @return The minimum number of players
     */
    public Integer getMinPlayers() {
        return minPlayers;
    }

    /**
     * @return The time the game needs to start
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * @return The time the game needs to end
     */
    public Integer getEndTime() {
        return endTime;
    }

    /**
     * @return
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @param teamSize The size of each team
     */
    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }

    /**
     * @param team The team to be added to the game
     */
    public void addTeam(Team team) {
        this.teams.add(team);
    }

    /**
     * @param useTeams True if the game should use teams
     */
    public void setUseTeams(boolean useTeams) {
        this.useTeams = useTeams;
    }

    /**
     * @param useTeamsSpawns True if the game should use team spawns
     */
    public void setUseTeamSpawns(boolean useTeamsSpawns) {
        this.useTeamSpawns = useTeamsSpawns;
    }

    /**
     * @return The inventory each player gets when the game starts
     */
    public Inventory getDefaultInventory() {
        return defaultInventory;
    }

    /**
     * @param defaultInventory The inventory each player get when the game starts
     */
    public void setDefaultInventory(Inventory defaultInventory) {
        this.defaultInventory = defaultInventory;
    }

    /**
     * @param waitingLobby The place where each player will be teleported to while there aren't enough players on the server
     */
    public void setWaitingLobby(Location waitingLobby) {
        this.waitingLobby = waitingLobby;
    }

    /**
     * @return The place where each player will be teleported to while there aren't enough players on the server
     */
    public Location getWaitingLobby() {
        return waitingLobby;
    }

    /**
     * @return All registered teams.
     */
    public ArrayList<Team> getTeams() {
        return teams;
    }

    /**
     * @return The in game spawn or null if using team spawns.
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
