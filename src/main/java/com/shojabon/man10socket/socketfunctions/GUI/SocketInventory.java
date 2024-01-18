package com.shojabon.man10socket.socketfunctions.GUI;

import com.shojabon.man10socket.utils.ConcurrentHashMapWithTimeout;
import com.shojabon.mcutils.Utils.SInventory.SInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class SocketInventory extends SInventory {
    public static ConcurrentHashMapWithTimeout<String, SInventory> activeSessions = new ConcurrentHashMapWithTimeout<>(60 * 5);
    public SocketInventory(String title, int inventoryRows, JavaPlugin plugin) {
        super(title, inventoryRows, plugin);
    }

}
