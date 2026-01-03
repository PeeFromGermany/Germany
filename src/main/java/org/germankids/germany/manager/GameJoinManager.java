package org.germankids.germany.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.germankids.germany.Germany;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.game.Games;
import org.jspecify.annotations.NonNull;

public class GameJoinManager implements Listener {

    private Germany germany;

    public GameJoinManager(Germany germany){
        this.germany = germany;
    }

    private void addPlayer(Player player, int gameId){
        Games game = germany.gameManager().getGame(gameId);
        if (game == null) {
            player.sendMessage("This game does not exist.");
            return;
        }
        game.addPlayer(player);
    }

    private void removePlayer(Player player){
        Games game = germany.gameManager().getGame(player);
        if (game == null) {
            player.sendMessage("You are not in a game.");
            return;
        }
        game.removePlayer(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (!e.getView().getTitle().equals("Select a Game")) return;
        if (!(e.getWhoClicked() instanceof Player player)) return;

        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        String name = ChatColor.stripColor(meta.getDisplayName());

        if (!name.startsWith("Game ")) return;

        int gameId;
        try {
            gameId = Integer.parseInt(name.replace("Game ", ""));
        } catch (NumberFormatException ex) {
            return;
        }

        player.closeInventory();
        addPlayer(player,gameId); // will now teleport to the correct world
    }

    @EventHandler
    public void onLeaveRightClick(PlayerInteractEvent e){
        var player = e.getPlayer();
        var itemStack = e.getItem();
        if (itemStack == null) return;
        var clickedOnItem = itemStack.getType();
        if(clickedOnItem == Material.REDSTONE) removePlayer(player);
    }

    @EventHandler
    public void onPlayerGameLeave(PlayerQuitEvent event){
        var player = event.getPlayer();
        removePlayer(player);
        GameUtil.updateAllTabLists(germany.gameManager());
    }
}
