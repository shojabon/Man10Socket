package com.shojabon.man10socket.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DynamicTest extends @NotNull Command {

    String name = "";
    public DynamicTest(@NotNull String name) {
        super(name);
        this.name = name;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        Bukkit.broadcastMessage("executed " + name + " command");
        return false;
    }
}
