package org.germankids.germany.listener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.manager.ConfigManager;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.getInventory().clear();
        player.teleport(ConfigManager.getLobby());
        GameUtil.giveItem(player, Material.COMPASS,4,"Game Selector", ChatColor.AQUA);
    }
}
