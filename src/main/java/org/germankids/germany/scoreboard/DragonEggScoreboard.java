package org.germankids.germany.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class DragonEggScoreboard{
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();
    private Objective objective = board.registerNewObjective(
            "DragonEgg",
            Criteria.DUMMY,
            "test"
    );

    private void showScoreboard(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(board);
        }
    }
}