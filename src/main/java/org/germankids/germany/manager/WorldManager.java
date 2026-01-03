package org.germankids.germany.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.germankids.germany.Germany;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WorldManager {

    private static final String TEMPLATE_WORLD = "world";

    public static World createGameWorld(int gameId, Germany plugin) {
        String worldName = "game_world_" + gameId;

        File source = new File(Bukkit.getWorldContainer(), TEMPLATE_WORLD);
        File target = new File(Bukkit.getWorldContainer(), worldName);

        if (!target.exists()) {
            try {
                copyWorld(source, target);
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to copy world for game " + gameId);
                e.printStackTrace();
                return null;
            }
        }

        return Bukkit.createWorld(new WorldCreator(worldName));
    }

    private static void copyWorld(File source, File target) throws IOException {
        if (source.isDirectory()) {
            if (!target.exists()) target.mkdirs();

            for (String file : source.list()) {
                // skip dangerous files
                if (file.equals("uid.dat") || file.equals("session.lock")) continue;
                if (file.equals("playerdata") || file.equals("stats") || file.equals("advancements"))
                    continue;

                copyWorld(
                        new File(source, file),
                        new File(target, file)
                );
            }
        } else {
            Files.copy(source.toPath(), target.toPath());
        }
    }

    public static boolean deleteWorldFolder(File folder) {
        if (!folder.exists()) return true;

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteWorldFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        return folder.delete();
    }

    public static World resetGameWorld(int gameId, Germany plugin) {
        String worldName = "game_world_" + gameId;

        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, false);
        }

        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        deleteWorldFolder(worldFolder);

        // copy fresh template
        File source = new File(Bukkit.getWorldContainer(), TEMPLATE_WORLD);
        try {
            copyWorld(source, worldFolder);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to reset world " + worldName);
            e.printStackTrace();
            return null;
        }

        return Bukkit.createWorld(new WorldCreator(worldName));
    }
}