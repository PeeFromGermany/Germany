package org.germankids.germany.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.germankids.germany.Germany;

public class ConfigManager {
    private static FileConfiguration config;
    public static void setupConfig(Germany germany){
        ConfigManager.config = germany.getConfig();
        germany.saveDefaultConfig();
    }
    public static Location getLobby(){
        return new Location(
                Bukkit.getWorld("world"),
                config.getDouble("lobby" + ".x"),
                config.getDouble("lobby" + ".y"),
                config.getDouble("lobby" + ".z"),
                (float) config.getDouble("lobby" + ".yaw"),
                (float) config.getDouble("lobby" + ".pitch")
        );
    }

    public static Location getGameLobby(){
        return new Location(
                Bukkit.getWorld("world"),
                config.getDouble("game-lobby" + ".x"),
                config.getDouble("game-lobby" + ".y"),
                config.getDouble("game-lobby" + ".z"),
                (float) config.getDouble("game-lobby" + ".yaw"),
                (float) config.getDouble("game-lobby" + ".pitch")
        );
    }
}