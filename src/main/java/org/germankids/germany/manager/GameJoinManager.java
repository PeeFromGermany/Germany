package org.germankids.germany.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.germankids.germany.Germany;
import org.germankids.germany.game.Games;
import org.jspecify.annotations.NonNull;

public class GameJoinManager implements Listener {

    private Germany germany;

    public GameJoinManager(Germany germany){
        this.germany = germany;
    }

    private void addPlayer(Player player, int gameId){
        Games game = germany.gameManager().getGame(gameId);
        if (game == null) return;
        game.addPlayer(player);
    }

    private void removePlayer(Player player){
        Games game = germany.gameManager().getGame(player);
        if (game == null) return;
        game.removePlayer(player);
    }

    @EventHandler
    public void onAttemptGameJoin(InventoryClickEvent e){
        var player = ((Player) e.getWhoClicked()).getPlayer();
        var itemStack = e.getCurrentItem();
        if (player == null || itemStack == null) return;
        Material clickedOnItem = itemStack.getType();
        if(clickedOnItem == Material.DIAMOND_SWORD) addPlayer(player,1);
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
    }
}
