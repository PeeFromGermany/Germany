package org.germankids.germany;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;



public class JoinHandler implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Mayhem mayhem = new Mayhem(e.getPlayer());
        mayhem.sendPrayers();
    }
}
