package org.germankids.germany.minigames;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.game.Games;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class DragonEggGame {

    private Games games;

    private final int MAX_SCORE = 20;
    private final double coordsX = -29.5;
    private final double coordsY = 6;
    private final double coordsZ = 290.5;
    private final int maxXRange = (int) (-coordsX - 13.5);
    private final int maxZRange = (int) (306.5 - coordsZ);
    private HashMap<UUID, Integer> playerStats;
    private Random random;

    public DragonEggGame(Games games){
        random = new Random();
        this.games = games;
        playerStats = new HashMap<>();
    }
    private Location spawnLocation(World world){
        return new Location(
                world,
                coordsX+getRandom(0,maxXRange),
                coordsY + 2,
                coordsZ+getRandom(0,maxZRange));
    }
    private int getRandom(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public void spawnDragonEggAtRandomLoc(World world){
        Block block = world.getBlockAt(spawnLocation(world));
        block.setType(Material.PODZOL);
    }

    private void gameStartBlockSpawn(World world){
        for (int i = 0; i < 3; i++) spawnDragonEggAtRandomLoc(world);
    }

    public void start(){
        for (UUID uuid : games.getUuidList()){
            Player player = Bukkit.getPlayer(uuid);

            if (player == null) return;
            player.getInventory().clear();
            GameUtil.setGameAttributesAfterStart(player);
            playerStats.put(uuid, 0);
            gameStartBlockSpawn(games.getGameWorld());
        }
    }

    public void addPoint(Player player){
        UUID playerUUID = player.getUniqueId();
        playerStats.replace(playerUUID, playerStats.get(playerUUID) + 1);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("+1 Point " + "( " + playerStats.get(playerUUID) + " / " + MAX_SCORE + " )"));

        if (playerStats.get(playerUUID) == 20){
            for(UUID uuid : playerStats.keySet()){
                Player player1 = Bukkit.getPlayer(uuid);
                player1.sendMessage("The winner of the game is " + player.getName());
                player1.teleport(games.lobbySpawn);
                player1.getInventory().clear();
                GameUtil.giveItem(player1, Material.COMPASS, 4, "Game Selector", ChatColor.AQUA);
                GameUtil.setDefaultAttributes(player1);
            }
            games.reset();
        }
    }
}
