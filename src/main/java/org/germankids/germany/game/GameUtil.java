package org.germankids.germany.game;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.germankids.germany.manager.GameManager;

import java.util.UUID;

public class GameUtil {

    public static void setGameAttributesAfterStart(Player player){
        AttributeInstance a = player.getAttribute(Attribute.SCALE);
        final double SCALE_SIZE = 0.1;
        try{
            a.setBaseValue(SCALE_SIZE);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public static void setDefaultAttributes(Player player){
        AttributeInstance a = player.getAttribute(Attribute.SCALE);
        try{
            a.setBaseValue(1);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public static void giveItem(Player player, Material material, int slot, String name, ChatColor chatColor){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setItemName(chatColor + name);
        item.setItemMeta(itemMeta);
        player.getInventory().setItem(slot, item);
    }

    public static void countDownEnded(Player player){
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BANJO, 10f, 1f);
    }
    public static void addPointSound(Player player){
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_COW_BELL, 10f ,1f);
    }

    public static ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static void updateAllTabLists(GameManager gameManager) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();

            // Determine which zone the player is in
            Games playerGame = gameManager.getGame(player);
            boolean isInGame = playerGame != null;
            boolean isInLobby = gameManager.getGame(player) == null;

            for (Player other : Bukkit.getOnlinePlayers()) {
                UUID otherUUID = other.getUniqueId();

                Games otherGame = gameManager.getGame(other);
                boolean otherInGame = otherGame != null;
                boolean otherInLobby = gameManager.getGame(other) == null;

                // Show player if both are in same game OR both in lobby
                if ((isInLobby && otherInLobby) || (isInGame && playerGame == otherGame)) {
                    player.showPlayer(other);
                } else {
                    player.hidePlayer(other);
                }
            }
        }
    }
}