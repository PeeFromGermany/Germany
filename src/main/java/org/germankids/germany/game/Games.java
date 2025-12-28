package org.germankids.germany.game;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.germankids.germany.Germany;
import org.germankids.germany.manager.ConfigManager;
import org.germankids.germany.minigames.DragonEggGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Games {


    private DragonEggGame dragonEggGame;
    private Countdown countdown;
    private Germany germany;

    public final int REQUIRED_PLAYERS = 2;
    public final Location gameLobbySpawn = ConfigManager.getGameLobby();
    public final Location lobbySpawn = ConfigManager.getLobby();
    private int gameId;

    private List<UUID> uuidList;
    private List<UUID> uuidWaiterList;
    private GameStatus gameStatus;
    private List<UUID> desertVotes;
    private List<UUID> twoBrothersVotes;

    private void initializeMapVotes(){
        desertVotes = new ArrayList<>();
        twoBrothersVotes = new ArrayList<>();
    }

    public Games(int gameId, Germany germany){
        this.germany = germany;
        this.gameId = gameId;
        dragonEggGame = new DragonEggGame(this);
        countdown = new Countdown(this, germany);
        uuidList = new ArrayList<>();
        uuidWaiterList = new ArrayList<>();
        gameStatus = GameStatus.RECRUITING;
        initializeMapVotes();
    }

    public void start(){
        //the current start method is called in the countdown class and the countdown.start()
        // is called when a player is added to instantly see if there are enough players.
        //the method is down here, the dragonegggame.start(), is obvious when looking in the class itself.
        // after removing a player a new sendmessage or title idk is sent to the player and then countdown will stop by itself.
        // look how it stops in the Countdown class.
        dragonEggGame.start();
        gameStatus = GameStatus.LIVE;
    }
    public void reset(){
        uuidList.clear();
        uuidWaiterList.clear();
        gameStatus = GameStatus.RECRUITING;
        dragonEggGame = new DragonEggGame(this);
        countdown = new Countdown(this, germany);
    }

    public void addPlayer(Player player){
        if (gameStatus == GameStatus.LIVE){
            player.sendMessage(ChatColor.RED + "The game has already started.");
            return;
        }

        player.teleport(gameLobbySpawn);
        player.getInventory().clear();
        GameUtil.giveItem(player, Material.REDSTONE,4, "Leave", ChatColor.RED);
        GameUtil.giveItem(player, Material.FEATHER,0, "Map Voting", ChatColor.YELLOW);
        UUID uuid = player.getUniqueId();
        uuidList.add(uuid);
        uuidWaiterList.add(uuid);
        if (uuidList.size() >= REQUIRED_PLAYERS){
            countdown.start();
            uuidWaiterList.clear();
            gameStatus = GameStatus.STARTING;
        }
    }

    public void removePlayer(Player player){
        UUID uuid = player.getUniqueId();
        uuidList.remove(uuid);
        uuidWaiterList.remove(uuid);
        removeMapVote(player);
        player.teleport(lobbySpawn);
        player.getInventory().clear();
        GameUtil.giveItem(player, Material.COMPASS,4,"Game Selector", ChatColor.AQUA);
        if (getUuidList().size() < REQUIRED_PLAYERS && gameStatus == GameStatus.STARTING){
            gameStatus = GameStatus.RECRUITING;
            countdown.cancel();
            sendMessage("There aren't enough players.");
        }
    }

    public void sendMessage(String message){
        for(UUID uuid : getUuidList()){
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public void sendTitle(String title, String subTitle){
        for(UUID uuid : getUuidList()){
            Player player = Bukkit.getPlayer(uuid);
            player.sendTitle(title,subTitle, 5, 10, 5);
        }
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }
    public List<UUID> getUuidList() {return uuidList;}
    public List<UUID> getUuidWaiterList() {return uuidWaiterList;}
    public int getGameId(){return gameId;}
    public DragonEggGame getDragonEggGame(){
        return dragonEggGame;
    }

    public int getDesertVotes() {
        return desertVotes.size();
    }

    public int getTwoBrothersVotes() {
        return twoBrothersVotes.size();
    }

    public void addPlayerToDesertVotes(Player player){
        if (desertVotes.contains(player.getUniqueId())) return;
        removeMapVote(player);
        desertVotes.add(player.getUniqueId());
    }
    public void addPlayerToTwoBrothersVotes(Player player){
        if (twoBrothersVotes.contains(player.getUniqueId())) return;
        removeMapVote(player);
        twoBrothersVotes.add(player.getUniqueId());
    }
    public void removeMapVote(Player player){
        if (desertVotes.contains(player.getUniqueId())) desertVotes.remove(player.getUniqueId());
        if (twoBrothersVotes.contains(player.getUniqueId())) twoBrothersVotes.remove(player.getUniqueId());
    }
}
