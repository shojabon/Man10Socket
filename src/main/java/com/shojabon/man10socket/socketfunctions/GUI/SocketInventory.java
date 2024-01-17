package com.shojabon.man10socket.socketfunctions.GUI;

import com.shojabon.mcutils.Utils.SInventory.SInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class SocketInventory extends SInventory {
    public SocketInventory(String title, int inventoryRows, JavaPlugin plugin) {
        super(title, inventoryRows, plugin);
    }

}
