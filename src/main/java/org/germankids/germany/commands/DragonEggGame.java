package org.germankids.germany.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class DragonEggGame {

    private List<Location> possibleSpawnLocs = new ArrayList<>();


//    public void putPossibleSpawnLocs(double coordsX, double coordsY, double coordsZ){
//        for (int i = 0; i < 632; i++) {
//            for (int j = 0; j < 53553; j++) {
//                possibleSpawnLocs.add(new Location(Bukkit.getWorld("world"), coordsX+j, coordsY, coordsZ+i));
//            }
//        }
//    }


    private void spawnDragoneggs(){
        World w = Bukkit.getWorld("world");
//        w.spawnEntity()
    }
}
