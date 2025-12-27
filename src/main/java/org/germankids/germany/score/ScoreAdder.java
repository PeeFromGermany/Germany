package org.germankids.germany.score;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.germankids.germany.Germany;
import org.germankids.germany.game.GameStatus;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.game.Games;

public class ScoreAdder implements Listener {
    private Germany germany;

    public ScoreAdder(Germany germany){
        this.germany = germany;
    }

    @EventHandler
    public void onDragonEggInteraction(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if (event.getAction().isRightClick() && block != null){
            Material materialOfClickedOn = block.getType();
            if (materialOfClickedOn == Material.PODZOL){
                Player player = event.getPlayer();
                Games games = germany.gameManager().getGame(player);
                if (games == null || games.getGameStatus() != GameStatus.LIVE){
                    return;
                }
                games.getDragonEggGame().addPoint(player);
                GameUtil.addPointSound(player);
                block.setType(Material.AIR);
                games.getDragonEggGame().spawnDragonEggAtRandomLoc();
            }
        }
    }
}