package com.shojabon.man10socket.commands.subcommands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.utils.JSONConverter;
import com.shojabon.mcutils.Utils.SItemStack;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class ItemInformationCommand implements CommandExecutor {
    Man10Socket plugin;

    public ItemInformationCommand(Man10Socket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try{
            SItemStack item = SItemStack.fromBase64(args[1]);
            sender.sendMessage(JSONConverter.itemStackToJSON(item.build()).toString());
        }catch (Exception e){
            sender.sendMessage("error_internal");
            return true;
        }
        return true;
    }
}