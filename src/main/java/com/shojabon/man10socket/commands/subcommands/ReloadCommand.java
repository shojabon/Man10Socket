package com.shojabon.man10socket.commands.subcommands;

import com.shojabon.man10socket.Man10Socket;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    Man10Socket plugin;

    public ReloadCommand(Man10Socket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        plugin.running = false;
        plugin.reloadConfig();
        plugin.startServer();
        plugin.running = true;
        p.sendMessage("§a§l[Man10Socket]§f§lサーバーを再起動しました");
        return true;
    }
}