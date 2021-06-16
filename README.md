
# Craftions API

The Craftions API is an Application Programming Interface which helps you to create awesome minecraft plugins!




## Features

- Minigame Api
    - Teams
    - Countdown (start and stop)
    - Multi Language (de_de/en_us)
    - Minimal players
    - Default Inventories
    - Game start and stop event
- Easier Interface for communicating with the Bukkit FileConfigurationAPI
- Convert &-colorcodes to §-colorcodes and backwards
- MySQL Connections
- RSA Encryption
- Download Files
- Send Players to other BungeeCord Servers
- Player heads with custom textures
## Usage/Examples

### Mini Games

```java
package net.craftions.apitest;

import net.craftions.api.game.util.GameBuilder;
import net.craftions.api.game.util.TeamBuilder;
import net.craftions.api.game.Game;
import net.craftions.api.language.Language;
import net.craftions.api.game.events.GameEndEvent;
import net.craftions.api.game.events.GameStartEvent;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.Listener;

public class MyMiniGame extends JavaPlugin implements Listener {

    @Override
    public void onEnable(){
        // create the game
        Game game = new GameBuilder()
            .setName("MyGame")
            .setMinPlayers(2)
            .setUseTeams(true)
            .setUseTeamSpawns(true)
            .setStartTime(30)
            .setEndTime(40)
            .setLanguage(Language.Code.GERMAN)
            .setColorCode("§c")
            .setSpawn(null)
            .setWaitingLobby(new Location(Bukkit.getWorld("world"), 0, 100, 0))
            .setTeamSize(1)
            .addTeam(new TeamBuilder()
                .setName("Blau")
                .setSpawn(new Location(Bukkit.getWorld("world"), 0, 100, 100))
                .setColor("§9")
                .build()
            )
            .addTeam(new TeamBuilder()
                .setName("Rot")
                .setSpawn(new Location(Bukkit.getWorld("world"), 100, 100, 0))
                .setColor("§c")
                .build()
            )
            .setDefaultInventory(Bukkit.createInventory(null, InventoryType.PLAYER))
            .build();
        // register the listeners
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event){
        System.out.println("The game has started!");
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event){
        System.out.println("The game has end!");
    }
}

```

### File Configuration

```java
package net.craftions.apitest;

import net.craftions.api.config.Config;

import java.io.File;

public class MyClass {

    public static void myMethod() {
        File conf = new File("plugins/myplugin/config.yml");
        try {
            if(!conf.exists()){
                if(!conf.getParentFile().isDirectory()){
                    conf.mkdirs();
                }
                conf.createNewFile();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        Config config = new Config(conf, "myConfigName");

        // get the config
        Config config2 = Config.getInstance("myConfigName");

        // set a value
        config.set("path", null); // "path", Object
        
        // get a value
        config.get("path");

        // save the config (true reloads the config)
        config.reload(true);
    }
}
```


### Convert Color Codes

```java
package net.craftions.apitest;

import net.craftions.api.color.ColorCode;

public class MyColorCodeClass {

    public static void convert(){
        String msg = "";
        // convert & to §
        msg = ColorCode.to("&cHallo Welt");
        // convert § to &
        msg = ColorCode.from(msg);
    }
}
```

### MySQL Connections
Please visit ![MCTzOCK/JavaMySQL](https://github.com/MCTzOCK/JavaMySQL/#example)

### RSA Encryption
Please visit ![MCTzOCK/RSAKeys](https://github.com/MCTzOCK/RSAKeys/#example)

### Download a File

```java 
package net.craftions.apitest;

import net.craftions.api.networking.NetUtils;

import java.io.File;

public class MyDownloadClass {

    public static void downloadDefaults(){
        NetUtils.download("https://craftions.net/index.html", new File("craftions.html"));
    }
}
```

### Send Players to other BungeeCord Servers

```java
package net.craftions.apitest;

import net.craftions.api.bungeecord.BungeeCordConnector;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class MyConnectorClass(){

    public static void sendToLobby() {
        BungeeCordConnector.connect(Bukkit.getPlayer("MCTzOCK"), "lobby");
    }
}

```

### Player heads with custom textures

```java
package net.craftions.apitest;

import net.craftions.api.util.PlayerHeads;

import org.bukkit.inventory.ItemStack;

public class MyHeadClass {

    public static void example(){
        ItemStack head = PlayerHeads.getHead("http://textures.minecraft.net/texture/4ea0c6e1b754e83f0e6e42900343a2dfe41dff342d8b062af532611b9ef6c99b");
    }
}
```
## Installation (Developers)

Install the Craftions API with maven

```xml
    <dependency>
        <groupId>net.craftions</groupId>
        <artifactId>api</artifactId>
        <version>1.2-SNAPSHOT</version>
    </dependency>
```
    
## Installation (Server Admins)

- Download the latest jar file from the release-tab
- Drag and drop it into your ``plugins`` folder 
- Restart your server
    
## Roadmap

- ScoreBoard Manager
- Vanilla Hex Codes
- Everything you want! Create an issue or reach me on discord: MCTzOCK#0047
## Authors

- [@MCTzOCK](https://www.github.com/MCTzOCK)


  
## Documentation

- [JavaDocs](https://craftionsmc.github.io/API/docs)

  
## License

[GPL-3.0-ONLY](https://choosealicense.com/licenses/gpl-3.0/)
