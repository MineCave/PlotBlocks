package com.lcastr0;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class PermissionHelper {

    public static Map<String, PermissionHelper> permissions = new HashMap<>();

    public final String permission;
    public final int blocks;
    public final int blockId;
    public final String message;

    public PermissionHelper(String permission, int blocks, int blockId, String message){
        this.permission = permission;
        this.blocks = blocks;
        this.blockId = blockId;
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getPermission(){
        return this.permission;
    }

    public int getBlocks(){
        return this.blocks;
    }

    public int getBlockId(){
        return this.blockId;
    }

    public String getMessage(){ return this.message; }

}
