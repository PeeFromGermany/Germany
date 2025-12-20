package org.germankids.germany.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.germankids.germany.manager.ConfigManager;

import java.util.ArrayList;
import java.util.UUID;

public class Games {



    private ArrayList<UUID> pUUID = new ArrayList<UUID>();
    private Location gameLobby = ConfigManager.getGameLobby();
    private GameUtil gameUtil = new GameUtil();

    public void addPlayer(Player player){
        pUUID.add(player.getUniqueId());
        player.teleport(gameLobby);
        gameUtil.setGameAttributesAfterStart(player);
        gameUtil.setJoinAttributes(player);
    }

    public void removePlayer(Player player){
        pUUID.remove(player.getUniqueId());
        gameUtil.setGameAttributesAfterLeave(player);

    }
}
