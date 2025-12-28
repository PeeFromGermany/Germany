package org.germankids.germany.voting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.germankids.germany.Germany;
import org.germankids.germany.game.GameUtil;
import org.germankids.germany.game.Games;

public class VoteRegister implements Listener {

    private Germany germany;
    private Inventory voteGui;
    public VoteRegister(Germany germany){
        this.germany = germany;
        voteGui = Bukkit.createInventory(null, 9, "Kies een map");
    }


    @EventHandler
    public void onFeatherRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Games games = germany.gameManager().getGame(player);
        if (games == null) return;
        ItemStack itemStack = event.getItem();
        if(itemStack == null) return;
        if (itemStack.getType() == Material.FEATHER){
            ItemStack desert = GameUtil.createGuiItem(
                    Material.DEAD_BUSH, ChatColor.YELLOW + "Desert (" + games.getDesertVotes() + " Votes" + ")");
            ItemStack twoBrothers = GameUtil.createGuiItem(
                    Material.RED_CONCRETE,
                    ChatColor.GRAY + "Two Brothers " + ChatColor.YELLOW + "(" + games.getTwoBrothersVotes() + " Votes" + ")");
            voteGui.setItem(3, desert);
            voteGui.setItem(5, twoBrothers);
            player.openInventory(voteGui);
        }
    }
    @EventHandler
    public void onMapPick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Games games = germany.gameManager().getGame(player);
        if (games == null) return;
        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null) return;
        if(itemStack.getType() == Material.DEAD_BUSH) games.addPlayerToDesertVotes(player);
        if (itemStack.getType() == Material.RED_CONCRETE) games.addPlayerToTwoBrothersVotes(player);
        player.closeInventory();
    }
}
