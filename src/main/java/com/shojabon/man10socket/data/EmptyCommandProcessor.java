package com.shojabon.man10socket.data;

import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmptyCommandProcessor extends @NotNull Command{

    String name = "";
    public EmptyCommandProcessor(@NotNull String name) {
        super(name);
        this.name = name;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return SCommandRouter.tabComplete(name, sender, args);
    }

}
