package org.germankids.germany.game;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.germankids.germany.Germany;
import org.germankids.germany.minigames.DragonEggGame;

import java.util.UUID;

public class Countdown extends BukkitRunnable{

    private Games games;
    private Germany germany;
    private DragonEggGame dragonEggGame;
    private int startingSeconds = 15;

    public Countdown(Games games, Germany germany, DragonEggGame dragonEggGame){
        this.games = games;
        this.germany = germany;
        this.dragonEggGame = dragonEggGame;
    }

    public void start(){
        runTaskTimer(germany, 0, 20);
    }

    @Override
    public void run() {
        if (startingSeconds == 0){
            games.start();
            cancel();
        }
        if (startingSeconds >= 0 && startingSeconds <= 10){
            games.sendMessage("Game will start in " + startingSeconds);
        } else {
            games.sendTitle("Game will start in", String.valueOf(startingSeconds));
        }
    }

    private void makeTickSound(){
        for(UUID uuid : games.getUuidList()){
            Player player = Bukkit.getPlayer(uuid);
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HAT, 10, 1);
        }
    }
}
