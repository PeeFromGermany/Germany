package org.germankids.germany.listener;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.germankids.germany.Germany;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.manager.ConfigManager;

public class JoinListener implements Listener {

    private Germany germany;
    public JoinListener(Germany germany){
        this.germany = germany;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.getInventory().clear();
        player.teleport(ConfigManager.getLobby());
        player.setGameMode(GameMode.ADVENTURE);
        GameUtil.giveItem(player, Material.COMPASS,4,"Game Selector", ChatColor.AQUA);
        GameUtil.updateAllTabLists(germany.gameManager());
    }
}
