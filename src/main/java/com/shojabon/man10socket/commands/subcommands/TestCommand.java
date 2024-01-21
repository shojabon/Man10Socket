package com.shojabon.man10socket.commands.subcommands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.commands.Man10SocketCommands;
import com.shojabon.man10socket.data.EmptyCommandProcessor;
import com.shojabon.mcutils.Utils.SItemStack;
import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestCommand implements CommandExecutor {
    Man10Socket plugin;

    public TestCommand(Man10Socket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        Bukkit.broadcastMessage(new SItemStack(p.getInventory().getItemInMainHand()).getItemTypeMD5(true));
        Bukkit.broadcastMessage(new SItemStack(p.getInventory().getItemInMainHand()).getItemTypeMD5(false));

        return true;
    }
}