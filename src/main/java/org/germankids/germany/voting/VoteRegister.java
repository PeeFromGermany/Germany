package org.germankids.germany.voting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.germankids.germany.Germany;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.game.Games;
import org.germankids.germany.manager.ConfigManager;

public class VoteRegister implements Listener {

    private Germany germany;
    private Inventory voteGui;
    public VoteRegister(Germany germany){
        this.germany = germany;
        voteGui = Bukkit.createInventory(null, 9, "Kies een map");
    }


    @EventHandler
    public void onFeatherRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Games games = germany.gameManager().getGame(player);
        if (games == null) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.FEATHER) return;

        Inventory gui = Bukkit.createInventory(null, 9, "Kies een map");

        for (String mapId : ConfigManager.getMapIds()) {
            String name = ConfigManager.getMapName(mapId);
            Material material = ConfigManager.getMapMaterial(mapId);
            int slot = ConfigManager.getMapSlot(mapId);
            int votes = games.getVotes(mapId);

            ItemStack mapItem = GameUtil.createGuiItem(
                    material,
                    ChatColor.YELLOW + name + " (" + votes + " Votes)"
            );

            gui.setItem(slot, mapItem);
        }

        player.openInventory(gui);
    }


    @EventHandler
    public void onMapPick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Games games = germany.gameManager().getGame(player);
        if (games == null) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        for (String mapId : ConfigManager.getMapIds()) {
            if (item.getType() == ConfigManager.getMapMaterial(mapId)) {
                games.vote(player, mapId);
                player.closeInventory();
                return;
            }
        }
    }

}
