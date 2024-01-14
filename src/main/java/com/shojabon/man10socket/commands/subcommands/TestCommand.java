package com.shojabon.man10socket.commands.subcommands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.mcutils.Utils.SItemStack;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class TestCommand implements CommandExecutor {
    Man10Socket plugin;

    public TestCommand(Man10Socket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        JSONObject obj = new JSONObject();
        obj.put("type", "request");
        obj.put("data", new JSONObject());
        obj.put("path", "test/a/d");
        obj.put("target", "Man10Shop");
        Long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            JSONObject reply = Man10Socket.send(obj);
//            Bukkit.broadcastMessage(String.valueOf(reply));
        }
        Long end = System.currentTimeMillis();
        p.sendMessage("time: " + (end - start));
        return true;
    }
}