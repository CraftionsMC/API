/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Config {

    private static final HashMap<String, Config> instance = new HashMap<>();
    private final File c;
    private FileConfiguration conf;

    public Config(File configFile, String name) {
        this.c = configFile;
        this.reload(false);
        instance.put(name, this);
    }

    public static Config getInstance(String name) {
        return instance.get(name);
    }

    public void reload(Boolean save) {
        if (save) {
            this.save();
        }
        this.conf = YamlConfiguration.loadConfiguration(this.c);
    }

    public void set(String path, Object value) {
        this.conf.set(path, value);
    }

    public Object get(String path) {
        return this.conf.get(path);
    }

    public Set<String> getAll(String path, Boolean keys) {
        return this.conf.getConfigurationSection(path).getKeys(keys);
    }

    protected void save() {
        try {
            conf.save(this.c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
