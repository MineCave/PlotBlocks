package com.lcastr0.events;

import com.lcastr0.BlockCache;
import com.lcastr0.PermissionHelper;
import com.lcastr0.PlotBlocks;
import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.ILocation;
import com.worldcretornica.plotme_core.api.IPlayer;
import com.worldcretornica.plotme_core.bukkit.api.BukkitLocation;
import com.worldcretornica.plotme_core.bukkit.api.BukkitPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        IPlayer iPlayer = new BukkitPlayer(event.getPlayer());
        ILocation iLocation = new BukkitLocation(event.getBlock().getLocation());
        String plotId = PlotMeCoreManager.getInstance().getPlotId(iLocation);
        if(plotId != null) {
            Plot plot = PlotMeCoreManager.getInstance().getPlotById(plotId, iPlayer);
            if (plot != null) {
                Material mat = event.getBlock().getType();
                for(PermissionHelper permissionHelper : PlotBlocks.getInstance().permissions){
                    if(permissionHelper.getBlockId() == mat.getId() && event.getPlayer().hasPermission(permissionHelper.getPermission())
                            && !event.getPlayer().isOp() && !event.getPlayer().hasPermission(PlotBlocks.getInstance().masterPermission)) {
                        BlockCache blockCache = BlockCache.getBlockCache(plot, event.getBlock().getType());
                        if (!blockCache.counted)
                            blockCache.countPlot(event.getPlayer());
                        else
                            blockCache.increaseBlocks();
                        int count = blockCache.getBlocks();
                        if(count > permissionHelper.blocks) {
                            event.setCancelled(true);
                            blockCache.decreaseBlocks();
                            event.getPlayer().sendMessage(permissionHelper.getMessage());
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void onBlockRemove(BlockBreakEvent event){

        IPlayer iPlayer = new BukkitPlayer(event.getPlayer());
        ILocation iLocation = new BukkitLocation(event.getBlock().getLocation());
        String plotId = PlotMeCoreManager.getInstance().getPlotId(iLocation);
        if(plotId != null) {
            Plot plot = PlotMeCoreManager.getInstance().getPlotById(plotId, iPlayer);
            if (plot != null) {
                Material mat = event.getBlock().getType();
                for(PermissionHelper permissionHelper : PlotBlocks.getInstance().permissions){
                    if(permissionHelper.getBlockId() == mat.getId() && event.getPlayer().hasPermission(permissionHelper.getPermission())
                            && !event.getPlayer().isOp() && !event.getPlayer().hasPermission(PlotBlocks.getInstance().masterPermission)) {
                        BlockCache blockCache = BlockCache.getBlockCache(plot, event.getBlock().getType());
                        if (!blockCache.counted)
                            blockCache.countPlot(event.getPlayer());
                        else
                            blockCache.decreaseBlocks();
                    }
                }
            }
        }

    }

}
