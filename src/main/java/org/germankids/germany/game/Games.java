package org.germankids.germany.game;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.germankids.germany.Germany;
import org.germankids.germany.manager.ConfigManager;
import org.germankids.germany.minigames.DragonEggGame;

import java.util.ArrayList;
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
    private GameUtil gameUtil;
    private GameStatus gameStatus;


    public Games(int gameId, Germany germany){
        this.gameId = gameId;
        dragonEggGame = new DragonEggGame(this);
        countdown = new Countdown(this, germany, dragonEggGame);
        uuidList = new ArrayList<>();
        gameUtil = new GameUtil();
        gameStatus = GameStatus.RECRUITING;
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
        dragonEggGame = new DragonEggGame(this);
        countdown = new Countdown(this, germany, dragonEggGame);
        gameStatus = GameStatus.RECRUITING;
    }

    public void addPlayer(Player player){
        UUID uuid = player.getUniqueId();
        uuidList.add(uuid);
        gameUtil.setJoinInventory(player);
        player.teleport(gameLobbySpawn);
        if (uuidList.size() >= REQUIRED_PLAYERS){
            countdown.start();
            gameStatus = GameStatus.STARTING;
        }
    }

    public void removePlayer(Player player){
        UUID uuid = player.getUniqueId();
        uuidList.remove(uuid);
        gameUtil.setLeaveInventory(player);
        gameUtil.setGameAttributesAfterLeave(player);
        player.teleport(lobbySpawn);
        if (getUuidList().size() < REQUIRED_PLAYERS && gameStatus == GameStatus.RECRUITING){
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
            player.sendTitle(title,subTitle);
        }
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }
    public List<UUID> getUuidList() {return uuidList;}
    public int getGameId(){return gameId;}
    public Location getGameLobbySpawn(){return gameLobbySpawn;}
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    public DragonEggGame getGame(){
        return dragonEggGame;
    }
}
