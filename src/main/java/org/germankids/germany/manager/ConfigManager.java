package org.germankids.germany.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.germankids.germany.Germany;

import java.util.Set;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(Germany germany){
        germany.saveDefaultConfig();
        config = germany.getConfig();
    }

    // -------------------
    // Games & Locations
    // -------------------
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

    // -------------------
    // Maps
    // -------------------
    public static ConfigurationSection getMapsSection() {
        return config.getConfigurationSection("maps");
    }

    public static Set<String> getMapIds() {
        ConfigurationSection section = getMapsSection();
        if (section == null) return Set.of();
        return section.getKeys(false);
    }

    public static String getMapName(String mapId) {
        return config.getString("maps." + mapId + ".display-name", mapId);
    }

    public static Material getMapMaterial(String mapId) {
        return Material.valueOf(config.getString("maps." + mapId + ".material", "STAINED_GLASS_PANE"));
    }

    public static int getMapSlot(String mapId) {
        return config.getInt("maps." + mapId + ".slot", 0);
    }

    // -------------------
    // Teams
    // -------------------
    public static ConfigurationSection getTeamsSection() {
        return config.getConfigurationSection("teams");
    }

    public static Set<String> getTeamIds() {
        ConfigurationSection section = getTeamsSection();
        if (section == null) return Set.of();
        return section.getKeys(false);
    }

    public static String getTeamName(String teamId) {
        return config.getString("teams." + teamId + ".name", teamId);
    }

    public static Material getTeamMaterial(String teamId) {
        return Material.valueOf(config.getString("teams." + teamId + ".material", "STAINED_GLASS_PANE").toUpperCase());
    }

    public static int getTeamSlot(String teamId) {
        return config.getInt("teams." + teamId + ".slot", 0);
    }

    public static ChatColor getTeamColor(String teamId) {
        String color = config.getString("teams." + teamId + ".color", "WHITE").toUpperCase();
        try {
            return ChatColor.valueOf(color);
        } catch (IllegalArgumentException e) {
            return ChatColor.WHITE;
        }
    }
}