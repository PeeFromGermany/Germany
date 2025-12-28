package org.germankids.germany.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.germankids.germany.Germany;
import org.germankids.germany.game.Games;

import java.util.Arrays;

public class Permission implements Listener {

    private Germany germany;

    private final EntityType[] entityDecors = {
            EntityType.ARMOR_STAND,
            EntityType.ITEM_FRAME,
            EntityType.GLOW_ITEM_FRAME,
            EntityType.MINECART,
            EntityType.PAINTING
    };

    public Permission(Germany germany){
        this.germany = germany;
    }

    //EventHandlers
        //PlayerEvents
    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
        cancelDecorDamage(event);
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        cancelDamageAtSpawn(event);
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        cancelPlayerEvent(event);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        cancelPlayerEvent(event);
    }
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event){
        cancelPlayerEvent(event);
    }
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        cancelPlayerEvent(event);
    }
    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event){
        cancelPlayerEvent(event);
    }
        //Inventory Events
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        cancelInventoryClickEvent(event);
    }
    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent event){
        cancelHangingBreak(event);
    }

    //Cancel Methods
    private void cancelPlayerEvent(PlayerEvent playerEvent){
        if (playerEvent.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (playerEvent instanceof Cancellable cancellableEvent && hasNoStartedGame(playerEvent.getPlayer())){
            cancellableEvent.setCancelled(true);
        }
    }
    private void cancelInventoryClickEvent(InventoryClickEvent inventoryClickEvent){
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (hasNoStartedGame(player)){
            inventoryClickEvent.setCancelled(true);
        }
    }
        //Damage Methods
    private void cancelDecorDamage(EntityDamageByEntityEvent entityDamageByEntityEvent){
        Entity damager = entityDamageByEntityEvent.getDamager();
        Player player = (Player) damager;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        EntityType damagedEntityType = entityDamageByEntityEvent.getEntity().getType();
        if (Arrays.asList(entityDecors).contains(damagedEntityType)){
            entityDamageByEntityEvent.setCancelled(true);
        }
    }
    private void cancelHangingBreak(HangingBreakByEntityEvent hangingBreakByEntityEvent){
        Entity damager = hangingBreakByEntityEvent.getRemover();
        Player player = (Player) damager;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        hangingBreakByEntityEvent.setCancelled(true);
    }

    private void cancelDamageAtSpawn(EntityDamageEvent entityDamageEvent){
        Entity damageTaker = entityDamageEvent.getEntity();
        if (damageTaker instanceof Player player && hasNoStartedGame(player)){
            entityDamageEvent.setCancelled(true);
        }
    }

    //Game Checkers
    private boolean hasNoStartedGame(Player player){
        Games games = germany.gameManager().getGame(player);
        if (games == null) return true;
        return games.getUuidWaiterList().contains(player.getUniqueId());
    }
}