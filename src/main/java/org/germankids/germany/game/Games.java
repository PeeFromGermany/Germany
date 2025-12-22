package org.germankids.germany.game;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.germankids.germany.manager.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Games {

    private final int REQUIRED_PLAYERS = 2;
    private final Location gameLobbySpawn = ConfigManager.getGameLobby();
    private int gameId;
    private List<UUID> uuidList = new ArrayList<>();
    private GameUtil gameUtil = new GameUtil();

    private enum GameStatus{
        RECRUITING,
        STARTING,
        LIVE
    }
    private GameStatus gameStatus = GameStatus.RECRUITING;
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    public GameStatus getGameStatus(){
        return gameStatus;
    }


    public List<UUID> getUuidList() {return uuidList;}
    public int getGameId(){return gameId;}
    public Location getGameLobbySpawn(){return gameLobbySpawn;}


    public Games(int gameId){
        this.gameId = gameId;
    }

    public void addPlayer(Player player){
        UUID uuid = player.getUniqueId();
        uuidList.add(uuid);
        gameUtil.setJoinInventory(player);
        player.teleport(gameLobbySpawn);
    }
    public void removePlayer(Player player){
        UUID uuid = player.getUniqueId();
        uuidList.remove(uuid);
        gameUtil.setLeaveInventory(player);
        gameUtil.setGameAttributesAfterLeave(player);
        player.teleport(ConfigManager.getLobby());
    }
}
