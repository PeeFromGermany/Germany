package org.germankids.germany;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import static org.germankids.germany.Germany.germany;

public class Mayhem {
    private Player player;
    public Mayhem(Player player){
        this.player = player;
    }


    private void triggerMayhem() {
        World world = player.getWorld();
        Location loc = player.getLocation();

        world.setTime(18000);

        for (int i = 0; i < 5; i++) {
            world.strikeLightning(loc.clone().add(3, 0, 0));
            world.strikeLightning(loc.clone().add(-3, 0, 0));
            world.strikeLightning(loc.clone().add(0, 0, 3));
            world.strikeLightning(loc.clone().add(0, 0, -3));

            Entity e = world.spawnEntity(loc, EntityType.CREEPER);
            if (e instanceof Creeper creeper) {
                creeper.setPowered(true);
            }
        }
    }

    public void sendPrayers(){
        new BukkitRunnable() {
            int ticks = 0; // 1 tick = 1/20 second

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                // Messages with custom delays (all in ticks)
                if (ticks == 0) player.sendTitle("Welcome " + player.getName() + ".", "", 0, 60, 0);

                else if (ticks == 60) player.sendTitle(ChatColor.RED + "You have", "6–7 seconds to prepare.", 0, 60, 0);

                else if (ticks == 120) player.sendTitle(ChatColor.RED + "Good luck!", "", 0, 60, 0);

                else if (ticks == 180) player.sendTitle(ChatColor.RED + "Also thanks", "for your ip.", 0, 60, 0);

                else if (ticks == 240) player.sendTitle(ChatColor.RED + player.getAddress().getHostName(), "", 0, 60, 0);

                else if (ticks >= 240 && ticks <= 360 && (ticks - 240) % 20 == 0) {
                    int count = 6 - ((ticks - 240) / 20);
                    player.sendTitle(String.valueOf(count), "", 0, 20, 0);
                }
                else {
                    triggerMayhem();
                    cancel();
                }
                ticks++;
            }
        }.runTaskTimer(germany, 0L, 1L); // run every tick
    }
}

