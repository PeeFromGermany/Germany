package org.germankids.germany.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class Permission implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) cancelUnwanted(e);
    }
    @EventHandler
    public void onOffHand(PlayerSwapHandItemsEvent e){
        cancelUnwanted(e);
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        cancelUnwanted(e);
    }
    @EventHandler
    public void onHungerDepletion(FoodLevelChangeEvent e){
        cancelUnwanted(e);
        e.setFoodLevel(20);
    }
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        cancelUnwanted(e);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        cancelUnwanted(e);
    }
    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent e){
        e.setCancelled(true);
    }

    private void cancelUnwanted(Event e){
        if(e instanceof Cancellable && e instanceof PlayerEvent){
            Player player = ((PlayerEvent) e).getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE){
                ((Cancellable) e).setCancelled(true);
            }
        } else if(e instanceof EntityDamageEvent){
            ((EntityDamageEvent) e).setCancelled(true);
        }
    }
}