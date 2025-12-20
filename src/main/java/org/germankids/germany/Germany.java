package org.germankids.germany;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.germankids.germany.commands.MayhemCommand;
import org.germankids.germany.listener.ItemInteract;
import org.germankids.germany.listener.JoinListener;
import org.germankids.germany.listener.Permission;

public final class Germany extends JavaPlugin {
    public static Germany germany;
    @Override
    public void onEnable() {
        germany = this;
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new Permission(), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteract(), this);
        this.getCommand("mayhem").setExecutor(new MayhemCommand());

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
