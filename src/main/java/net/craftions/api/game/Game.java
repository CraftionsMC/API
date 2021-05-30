/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.game;

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

    // Timer variables
    private Integer _startTimer;
    private Integer _endTimer;

    // State variables
    private Boolean isStarting;
    private Boolean isRunning;

    /**
     * Start the game with its default values
     */
    public void start(){

    }

    /**
     * Start the game with a specific start time (used for /start)
     * @param time The time after the game starts.
     */
    public void start(int time){
        this.startTime = time;
        this.start();
    }

    /**
     * Starts the final game (after countdown)
     */
    protected void startFinal(){

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
}
