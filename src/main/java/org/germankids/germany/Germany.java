package org.germankids.germany;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.germankids.germany.commands.MayhemCommand;
import org.germankids.germany.listener.JoinListener;

public final class Germany extends JavaPlugin {
    public static Germany germany;
    @Override
    public void onEnable() {
        germany = this;
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        this.getCommand("mayhem").setExecutor(new MayhemCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
