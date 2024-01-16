package com.shojabon.man10socket.commands.subcommands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.utils.JSONConverter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class PlayersInfoCommand implements CommandExecutor {
    Man10Socket plugin;

    public PlayersInfoCommand(Man10Socket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        JSONArray players = new JSONArray();
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            players.put(JSONConverter.playerToJSON(p));
        }
        sender.sendMessage(players.toString());
        return true;
    }
}