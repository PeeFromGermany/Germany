package org.germankids.germany.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.germankids.germany.game.Games;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class DragonEggGame {

    private Games games;

    private final double coordsX = 29.5;
    private final double coordsY = 6;
    private final double coordsZ = 306.5;
    private final int maxXRange = (int) (coordsX - 13.5);
    private final int maxZRange = (int) (coordsZ - 290.5);
    private HashMap<UUID, Integer> playerStats;
    public DragonEggGame(Games games){
        this.games = games;
        playerStats = new HashMap<>();
    }
    private Location spawnLocation(){
        return new Location(
                Bukkit.getWorld("world"),
                coordsX+getRandom(0,maxXRange),
                coordsY + 2,
                coordsZ+getRandom(0,maxZRange));
    }
    private int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void spawnDragonEggAtRandomLoc(){
        World world = Bukkit.getWorld("world");
        Block block = world.getBlockAt(spawnLocation());
        block.setType(Material.DRAGON_EGG);
    }

    public void start(){
        for (UUID uuid : games.getUuidList()){
            playerStats.put(uuid, 0);
        }
    }

    public void addPoint(Player player){
        UUID playerUUID = player.getUniqueId();
        playerStats.replace(playerUUID, playerStats.get(playerUUID) + 1);
        if (playerStats.get(playerUUID) == 20){
            games.reset();
        }
    }
}
