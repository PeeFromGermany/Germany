package org.germankids.germany.listener;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.germankids.germany.manager.ConfigManager;

public class JoinListener implements Listener {

    private Location lobbySpawn = ConfigManager.getLobby();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        resetInventory(player);
        player.teleport(lobbySpawn);

    }

    private void resetInventory(Player player){
        player.getInventory().clear();
        //Put a cool name on the compass.
        player.getInventory().setItem(4, new ItemStack(Material.COMPASS));
    }


}
