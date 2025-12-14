package org.germankids.germany;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlockPlace implements Listener {
    @EventHandler
    public void playerPlaceBlockEvent(PlayerMoveEvent e){
        e.setCancelled(true);
    }
}
