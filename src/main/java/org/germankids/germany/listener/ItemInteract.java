package org.germankids.germany.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemInteract implements Listener{

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getMaterial() == Material.COMPASS){
            Player player = e.getPlayer();
            player.openInventory(initializeGui());
        }
    }

    private Inventory initializeGui(){
        final Inventory inv = Bukkit.createInventory(null, 9, "Menu");
        inv.addItem(createGuiItem(Material.DIAMOND_SWORD, ChatColor.BLUE + "Game 1"));
        return inv;
    }
    private ItemStack createGuiItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}