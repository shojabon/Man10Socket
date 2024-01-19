package com.shojabon.man10socket.data;

import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmptyCommandProcessor extends @NotNull Command{

    String name = "";
    public EmptyCommandProcessor(@NotNull String name) {
        super(name);
        this.name = name;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 1) return false;
        if(!strings[0].equalsIgnoreCase("help")) return false;
        commandSender.sendMessage("§e===================================");
        Iterator var2 = ((ArrayList)SCommandRouter.commands.get(this.name)).iterator();

        while(var2.hasNext()) {
            SCommandObject obj = (SCommandObject)var2.next();
            if (obj.hasPermission(commandSender)) {
                commandSender.sendMessage(obj.helpText(s, "§d", commandSender));
            }
        }

        commandSender.sendMessage("§e===================================");
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return SCommandRouter.tabComplete(name, sender, args);
    }

}
