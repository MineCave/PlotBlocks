package com.lcastr0;

import com.lcastr0.events.BlockEvents;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PlotBlocks extends JavaPlugin {

    public List<PermissionHelper> permissions = new ArrayList<>();

    public String masterPermission = "plotblocks.place.all";

    public static PlotBlocks instance;

    @Override
    public void onEnable(){
        // Load configuration
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.loadConfig();
        // Register events
        this.getServer().getPluginManager().registerEvents(new BlockEvents(), this);
        // Set instance
        instance = this;
    }

    public void loadConfig(){
        List<String> configPermissions = this.getConfig().getStringList("permissions");
        if(configPermissions != null){
            for(String permission : configPermissions){
                int blockId = this.getConfig().getInt(permission + ".blockId");
                int allowed = this.getConfig().getInt(permission + ".allowedBlocks");
                String message = this.getConfig().getString(permission + ".message");
                if(message == null)
                    message = this.getConfig().getString("defaultMessage");
                permissions.add(new PermissionHelper(permission, allowed, blockId, message.replaceAll("\\{b\\}", Material.getMaterial(blockId).toString())
                        .replaceAll("\\{n\\}", String.valueOf(allowed))));
            }
        }
        if(this.getConfig().getString("masterPermission") != null)
            this.masterPermission = this.getConfig().getString("masterPermission");
    }

    public static PlotBlocks getInstance(){
        return instance;
    }

}
