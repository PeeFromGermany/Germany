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
    public static Location getGameLobby(int lobbySpawnId){
        return new Location(
                Bukkit.getWorld("game-lobby." + lobbySpawnId),
                config.getDouble("game-lobby." + lobbySpawnId + ".x"),
                config.getDouble("game-lobby." + lobbySpawnId + ".y"),
                config.getDouble("game-lobby." + lobbySpawnId + ".z"),
                (float) config.getDouble("game-lobby." + lobbySpawnId + ".yaw"),
                (float) config.getDouble("game-lobby." + lobbySpawnId + ".pitch")
        );
    }
}