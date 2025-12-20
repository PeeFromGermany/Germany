package org.germankids.germany.listener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.germankids.germany.manager.ConfigManager;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.teleport(ConfigManager.getGameLobby());
        player.setGameMode(GameMode.ADVENTURE);
        resetInventory(player);
    }

    private void resetInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(4, new ItemStack(Material.COMPASS));
    }


}
