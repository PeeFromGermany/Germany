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
import org.germankids.germany.manager.ConfigManager;
import org.germankids.germany.manager.GameManager;

public class ItemInteract implements Listener {

    private final GameManager gameManager;

    public ItemInteract(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onCompassRightClick(PlayerInteractEvent e){
        if (e.getItem() == null) return;
        if (e.getItem().getType() != Material.COMPASS) return;

        Player player = e.getPlayer();
        player.openInventory(createMenu());
    }

    private Inventory createMenu(){
        int amountOfGames = ConfigManager.getAmountOfGames();

        // size rounded up to multiple of 9
        int size = ((amountOfGames - 1) / 9 + 1) * 9;
        Inventory inventory = Bukkit.createInventory(null, size, "Select a Game");

        for (int i = 1; i <= amountOfGames; i++) {
            inventory.addItem(createGameItem(i));
        }

        return inventory;
    }

    private ItemStack createGameItem(int gameId){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.BLUE + "Game " + gameId);
        item.setItemMeta(meta);

        return item;
    }
}