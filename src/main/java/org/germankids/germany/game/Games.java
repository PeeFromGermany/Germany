package org.germankids.germany.game;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.germankids.germany.Germany;
import org.germankids.germany.manager.ConfigManager;
import org.germankids.germany.manager.WorldManager;
import org.germankids.germany.minigames.DragonEggGame;

import java.util.*;

public class Games {

    private World gameWorld;
    private DragonEggGame dragonEggGame;
    private Countdown countdown;
    private Germany germany;

    public final int REQUIRED_PLAYERS = 2;
    public final Location lobbySpawn = ConfigManager.getLobby();
    private int gameId;

    private List<UUID> uuidList;
    private List<UUID> uuidWaiterList;
    private GameStatus gameStatus;
    private Map<String, Set<UUID>> mapVotes;

    public enum Team {
        TEAM_ONE,
        TEAM_TWO
    }

    private Map<Team, Set<UUID>> teams;




    private void initializeMapVotes() {
        mapVotes = new HashMap<>();
        for (String mapId : ConfigManager.getMapIds()) {
            mapVotes.put(mapId, new HashSet<>());
        }
    }

    public Games(int gameId, Germany germany){
        this.germany = germany;
        this.gameId = gameId;
        this.gameWorld = WorldManager.createGameWorld(gameId, germany);
        dragonEggGame = new DragonEggGame(this);
        countdown = new Countdown(this, germany);
        uuidList = new ArrayList<>();
        uuidWaiterList = new ArrayList<>();
        gameStatus = GameStatus.RECRUITING;
        initializeMapVotes();
        initializeTeams();
    }

    public void start(){
        //the current start method is called in the countdown class and the countdown.start()
        // is called when a player is added to instantly see if there are enough players.
        //the method is down here, the dragonegggame.start(), is obvious when looking in the class itself.
        // after removing a player a new sendmessage or title idk is sent to the player and then countdown will stop by itself.
        // look how it stops in the Countdown class.
        autoAssignTeams();
        dragonEggGame.start();
        gameStatus = GameStatus.LIVE;
    }
    public void reset(){
        uuidList.clear();
        uuidWaiterList.clear();
        clearTeams();
        initializeTeams();
        gameStatus = GameStatus.RECRUITING;
        dragonEggGame = new DragonEggGame(this);
        countdown = new Countdown(this, germany);
        this.gameWorld = WorldManager.resetGameWorld(gameId, germany);
        GameUtil.updateAllTabLists(germany.gameManager());
    }

    public void addPlayer(Player player){
        if (gameStatus == GameStatus.LIVE){
            player.sendMessage(ChatColor.RED + "The game has already started.");
            return;
        }

        player.teleport(getGameLobbySpawn());
        player.getInventory().clear();
        GameUtil.giveItem(player, Material.REDSTONE,4, "Leave", ChatColor.RED);
        GameUtil.giveItem(player, Material.FEATHER,0, "Map Voting", ChatColor.YELLOW);
        GameUtil.giveItem(player, Material.DEAD_BUSH,8, "Kies een team", ChatColor.GOLD);
        UUID uuid = player.getUniqueId();
        uuidList.add(uuid);
        uuidWaiterList.add(uuid);
        GameUtil.updateAllTabLists(germany.gameManager());
        if (uuidList.size() == REQUIRED_PLAYERS){
            countdown = new Countdown(this, germany);
            countdown.start();
            uuidWaiterList.clear();
            gameStatus = GameStatus.STARTING;
        }
    }

    public void removePlayer(Player player){
        UUID uuid = player.getUniqueId();
        uuidList.remove(uuid);
        uuidWaiterList.remove(uuid);
        removeFromTeam(player);
        removeVote(player);
        player.teleport(lobbySpawn);
        player.getInventory().clear();
        GameUtil.giveItem(player, Material.COMPASS,4,"Game Selector", ChatColor.AQUA);
        GameUtil.updateAllTabLists(germany.gameManager());
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

    public void vote(Player player, String mapId) {
        removeVote(player);
        mapVotes.get(mapId).add(player.getUniqueId());
    }
    public void removeVote(Player player) {
        for (Set<UUID> voters : mapVotes.values()) {
            voters.remove(player.getUniqueId());
        }
    }
    public int getVotes(String mapId) {
        return mapVotes.get(mapId).size();
    }
    public String getWinningMap() {
        return mapVotes.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    public World getGameWorld() {
        return gameWorld;
    }
    public Location getGameLobbySpawn() {
        Location base = ConfigManager.getGameLobby();
        return new Location(
                gameWorld,
                base.getX(),
                base.getY(),
                base.getZ(),
                base.getYaw(),
                base.getPitch()
        );
    }

    private void initializeTeams() {
        teams = new HashMap<>();
        teams.put(Team.TEAM_ONE, new HashSet<>());
        teams.put(Team.TEAM_TWO, new HashSet<>());
    }

    public int getTeamSize(Team team) {
        return teams.get(team).size();
    }

    public boolean isInTeam(Player player) {
        return teams.values().stream()
                .anyMatch(set -> set.contains(player.getUniqueId()));
    }


    public int getMaxTeamSize() {
        return (int) Math.ceil(uuidList.size() / 2.0);
    }
    public boolean joinTeam(Player player, Team team) {
        UUID uuid = player.getUniqueId();

        // already in a team
        if (isInTeam(player)) return false;

        // team full
        if (getTeamSize(team) >= getMaxTeamSize()) return false;

        teams.get(team).add(uuid);
        setTabColor(player, team);

        return true;
    }

    public void autoAssignTeams() {
        for (UUID uuid : uuidList) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;

            if (isInTeam(player)) continue;

            Team target = getTeamSize(Team.TEAM_ONE) <= getTeamSize(Team.TEAM_TWO)
                    ? Team.TEAM_ONE
                    : Team.TEAM_TWO;

            teams.get(target).add(uuid);
        }
    }

    private void clearTeams() {
        for (Set<UUID> team : teams.values()) {
            team.clear();
        }
    }

    public void setTabColor(Player player, Team team) {
        String teamId = team.name().toLowerCase(); // assumes enum names match config keys
        ChatColor color = ConfigManager.getTeamColor(teamId);
        player.setPlayerListName(color + player.getName());
    }
    public void removeFromTeam(Player player) {
        UUID uuid = player.getUniqueId();

        for (Set<UUID> team : teams.values()) {
            team.remove(uuid);
        }

        // reset tab color to default
        player.setPlayerListName(ChatColor.WHITE + player.getName());
    }

    public Team getPlayerTeam(Player player) {
        UUID uuid = player.getUniqueId();
        for (Map.Entry<Team, Set<UUID>> entry : teams.entrySet()) {
            if (entry.getValue().contains(uuid)) {
                return entry.getKey();
            }
        }
        return null; // player is not in any team
    }

    public void leaveTeam(Player player, Team team) {
        UUID uuid = player.getUniqueId();
        Set<UUID> teamSet = teams.get(team);
        if (teamSet != null) {
            teamSet.remove(uuid);
        }

        // Reset tab color
        player.setPlayerListName(ChatColor.WHITE + player.getName());
    }
}
