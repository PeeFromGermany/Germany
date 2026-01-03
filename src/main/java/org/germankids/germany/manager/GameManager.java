package org.germankids.germany.manager;

import org.bukkit.entity.Player;
import org.germankids.germany.Germany;
import org.germankids.germany.game.Games;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {

    private final List<Games> gamesList = new ArrayList<>();

    public GameManager(Germany germany){
        int amount = ConfigManager.getAmountOfGames();

        for (int i = 1; i <= amount; i++) {
            gamesList.add(new Games(i, germany));
        }
    }

    public List<Games> getGamesList(){
        return gamesList;
    }

    @Nullable
    public Games getGame(Player player){
        UUID uuid = player.getUniqueId();
        for (Games game : gamesList) {
            if (game.getUuidList().contains(uuid)) {
                return game;
            }
        }
        return null;
    }

    @Nullable
    public Games getGame(int gameId){
        for (Games game : gamesList) {
            if (game.getGameId() == gameId) {
                return game;
            }
        }
        return null;
    }
}