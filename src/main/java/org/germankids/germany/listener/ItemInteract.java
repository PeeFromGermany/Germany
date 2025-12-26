package org.germankids.germany.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemInteract implements Listener{
    private Inventory inventory;

    public ItemInteract(){
        inventory = Bukkit.createInventory(null, 9, "Menu");
        inventory.addItem(createGuiItem(Material.DIAMOND_SWORD, ChatColor.BLUE + "Game 1"));
    }

    @EventHandler
    public void onCompassRightClick(PlayerInteractEvent e){
        if(e.getMaterial() == Material.COMPASS){
            Player player = e.getPlayer();
            player.openInventory(initializeGui());
        }
    }

    private Inventory initializeGui(){
        return inventory;
    }

    private ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}