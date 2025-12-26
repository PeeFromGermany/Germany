package org.germankids.germany.game;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    public static void addPointSound(Player player){
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_COW_BELL, 10f ,1f);
    }
}