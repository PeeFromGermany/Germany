package org.germankids.germany.listener;

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
import org.bukkit.inventory.meta.ItemMeta;
import org.germankids.germany.Germany;
import org.germankids.germany.game.Games;
import org.germankids.germany.manager.ConfigManager;

import java.util.List;

public class TeamSelectListener implements Listener {

    private final Germany germany;

    public TeamSelectListener(Germany germany) {
        this.germany = germany;
    }

    @EventHandler
    public void onDeadBushRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Games game = germany.gameManager().getGame(player);
        if (game == null) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.DEAD_BUSH) return;

        Inventory gui = Bukkit.createInventory(null, 9, "Choose a Team");

        // Use ConfigManager for all team info
        for (String teamId : ConfigManager.getTeamIds()) {
            String teamName = ConfigManager.getTeamName(teamId);
            Material material = ConfigManager.getTeamMaterial(teamId);
            ChatColor color = ConfigManager.getTeamColor(teamId);
            int slot = ConfigManager.getTeamSlot(teamId);
            ItemStack teamItem = new ItemStack(material);
            ItemMeta meta = teamItem.getItemMeta();
            meta.setDisplayName(color + teamName);
            teamItem.setItemMeta(meta);

            gui.setItem(slot, teamItem);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onTeamPick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        event.setCancelled(true);

        Games game = germany.gameManager().getGame(player);
        if (game == null) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        // Check which team was clicked
        for (String teamId : ConfigManager.getTeamIds()) {
            Material material = ConfigManager.getTeamMaterial(teamId);
            if (clicked.getType() == material) {
                Games.Team newTeam = Games.Team.valueOf(teamId.toUpperCase());

                // Remove player from previous team if any
                Games.Team currentTeam = game.getPlayerTeam(player);
                if (currentTeam != null && currentTeam != newTeam) {
                    game.leaveTeam(player, currentTeam); // remove from old team
                }

                // Try to join the new team
                boolean success = game.joinTeam(player, newTeam);

                if (!success) {
                    player.sendMessage(ChatColor.RED + "You cannot join that team (team full).");
                } else {
                    player.sendMessage(ChatColor.GREEN + "You joined " + ConfigManager.getTeamName(teamId) + "!");

                    // Set tablist color from config
                    ChatColor color = ConfigManager.getTeamColor(teamId);
                    player.setPlayerListName(color + player.getName());
                }

                player.closeInventory();
                break;
            }
        }
    }
}