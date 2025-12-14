package org.germankids.germany.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.germankids.germany.Mayhem;
import org.jetbrains.annotations.NotNull;

public class MayhemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (commandSender instanceof Player player){
            if (strings.length == 0) player.sendMessage(ChatColor.RED + "Noo wrong! You need to use /mayhem {name}");
            else if (strings.length == 1){
                String argument = strings[0];
                if (Bukkit.getPlayer(argument) != null){
                    Player playerTarget = Bukkit.getPlayer(argument);
                    Mayhem mayhem = new Mayhem(playerTarget);
                    mayhem.sendPrayers();
                }
            }
        }
        return false;
    }
}
