package org.germankids.germany.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.germankids.germany.game.Games;

public class JoinManager implements Listener {

    @EventHandler
    public void onLeaveRightClick(InventoryClickEvent e){
        var player = ((Player) e.getWhoClicked()).getPlayer();
        var itemStack = e.getCurrentItem();
        if (player == null && itemStack == null) return;


        var clickedOnItem = itemStack.getType();
        if(clickedOnItem == Material.REDSTONE){
            var games = new Games();
            //Can't just create a game, I have to create the games when the server starts.
            games.removePlayer(player);
        }
    }

    @EventHandler
    public void onAttemptGameJoin(InventoryClickEvent e){
        var player = ((Player) e.getWhoClicked()).getPlayer();
        var itemStack = e.getCurrentItem();
        if (player == null && itemStack == null) return;
        var clickedOn = itemStack.getType();
        if (clickedOn == Material.DIAMOND_SWORD) {
            //put him in the game
        }
    }
}
