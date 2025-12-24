package org.germankids.germany;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.germankids.germany.minigames.DragonEggGame;
import org.germankids.germany.commands.MayhemCommand;
import org.germankids.germany.listener.ItemInteract;
import org.germankids.germany.listener.JoinListener;
import org.germankids.germany.listener.Permission;
import org.germankids.germany.manager.ConfigManager;
import org.germankids.germany.manager.GameManager;
import org.germankids.germany.manager.JoinManager;

public final class Germany extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setupConfig(this);


        gameManager = new GameManager(this);
        JoinManager joinManager = new JoinManager(this);

        ConfigManager.setupConfig(this);
        Bukkit.getPluginManager().registerEvents(joinManager, this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new Permission(), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteract(), this);
        getCommand("mayhem").setExecutor(new MayhemCommand(this));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GameManager gameManager(){
        return gameManager;
    }
}
