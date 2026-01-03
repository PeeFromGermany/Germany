package org.germankids.germany.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.germankids.germany.Germany;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(Germany germany){
        germany.saveDefaultConfig();
        config = germany.getConfig();
    }

    public static int getAmountOfGames(){
        return config.getInt("amount-of-games", 1);
    }

    private static Location getLocation(String path){
        return new Location(
                Bukkit.getWorld(config.getString(path + ".world")),
                config.getDouble(path + ".x"),
                config.getDouble(path + ".y"),
                config.getDouble(path + ".z"),
                (float) config.getDouble(path + ".yaw"),
                (float) config.getDouble(path + ".pitch")
        );
    }

    public static Location getLobby(){
        return getLocation("lobby");
    }

    public static Location getGameLobby(){
        return getLocation("game-lobby");
    }

}