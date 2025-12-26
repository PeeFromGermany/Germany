package org.germankids.germany.manager;


import org.bukkit.entity.Player;
import org.germankids.germany.Germany;
import org.germankids.germany.game.Games;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {

    private Germany germany;

    private List<Games> gamesList = new ArrayList<>();
    private final int gameOne = 1;
    private final int gameTwo = 2;
    public List<Games> getGamesList(){return gamesList;}


    public GameManager(Germany germany){
        this.germany = germany;
        gamesList.add(new Games(gameOne, germany));
        gamesList.add(new Games(gameTwo, germany));
    }

    public Games getGame(Player player){
        UUID playerUUID = player.getUniqueId();
        for(Games games : gamesList) {
            List<UUID> uuidList = games.getUuidList();
            if (uuidList.contains(playerUUID)) return games;
        }
        return null;
    }
    public Games getGame(int gameId){
        for(Games games : gamesList){
            if(games.getGameId() == gameId) return games;
        }
        return null;
    }
}
