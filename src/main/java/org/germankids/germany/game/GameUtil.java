package org.germankids.germany.game;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameUtil {
    private final double SCALE_SIZE = 0.1;
    public void setGameAttributesAfterStart(Player player){
        AttributeInstance a = player.getAttribute(Attribute.SCALE);
        try{
            a.setBaseValue(SCALE_SIZE);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public void setGameAttributesAfterLeave(Player player){
        AttributeInstance a = player.getAttribute(Attribute.SCALE);
        try{
            a.setBaseValue(1);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void giveItem(Player player, Material material, int slot, String name, ChatColor chatColor){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setItemName(chatColor + name);
        item.setItemMeta(itemMeta);
        player.getInventory().setItem(slot, item);
    }

    public void setJoinInventory(Player player){
        player.getInventory().clear();;
        player.setGameMode(GameMode.ADVENTURE);
        giveItem(player, Material.REDSTONE, 8, "Leave", ChatColor.RED);
    }
    public void setLeaveInventory(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        giveItem(player, Material.COMPASS, 4, "Join Game", ChatColor.AQUA);
    }
}
