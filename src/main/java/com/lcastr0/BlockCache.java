package com.lcastr0;

import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.IPlayer;
import com.worldcretornica.plotme_core.bukkit.api.BukkitPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BlockCache {

    public static Map<Plot, Map<Material, BlockCache>> blockCaches = new HashMap<>();

    public final Plot plot;
    public final Material type;

    public int blocks = 0;
    public boolean counted = false;

    public BlockCache(Plot plot, Material type){
        this.plot = plot;
        this.type = type;
        Map<Material, BlockCache> cachedUuid = new HashMap<>();
        if(blockCaches.containsKey(plot)){
            cachedUuid = blockCaches.get(plot);
            if(!cachedUuid.containsKey(type))
                cachedUuid.put(type, this);
            blockCaches.put(plot, cachedUuid);
            return;
        }
        cachedUuid.put(type, this);
        blockCaches.put(plot, cachedUuid);
    }

    public void countPlot(Player player){
        if(!this.counted) {
            String plotId = this.plot.getId();
            IPlayer iPlayer = new BukkitPlayer(player);
            PlotMeCoreManager manager = PlotMeCoreManager.getInstance();
            int bottomX = manager.bottomX(plotId, iPlayer.getWorld());
            int bottomZ = manager.bottomZ(plotId, iPlayer.getWorld());
            int topX = manager.topX(plotId, iPlayer.getWorld());
            int topZ = manager.topZ(plotId, iPlayer.getWorld());
            int topY = player.getWorld().getMaxHeight();
            for (int x = bottomX; x < topX; x++) {
                for (int y = 0; y < topY; y++) {
                    for (int z = bottomZ; z < topZ; z++) {
                        Location loc = new Location(player.getWorld(), x, y, z);
                        if (loc.getBlock().getType() == this.type)
                            this.blocks++;
                    }
                }
            }
            this.counted = true;
        }
    }

    public int getBlocks(){
        return this.blocks;
    }

    public void increaseBlocks(){
        this.blocks++;
    }

    public void decreaseBlocks(){
        this.blocks--;
    }

    public static BlockCache getBlockCache(Plot plot, Material type){
        if(blockCaches.containsKey(plot)){
            if(blockCaches.get(plot).containsKey(type))
                return blockCaches.get(plot).get(type);
        }
        return new BlockCache(plot, type);
    }

}
