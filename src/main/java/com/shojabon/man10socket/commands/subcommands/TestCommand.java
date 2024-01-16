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
//        JSONObject obj = new JSONObject();
//        obj.put("type", "request");
//        obj.put("data", new JSONObject());
//        obj.put("path", "test/a/d");
//        obj.put("target", "Man10Shop");
//        new Thread(() -> {
//            Long start = System.currentTimeMillis();
//            for(int i = 0; i < 10000; i++){
//                JSONObject reply = Man10Socket.send(obj, true);
//                Bukkit.broadcastMessage(String.valueOf(reply));
//            }
//            Long end = System.currentTimeMillis();
//            p.sendMessage("time: " + (end - start));
//        }).start();
        SCommandRouter.addVirtualCommand("comm", new SCommandObject().prefix("a").argument("pureiya-"));
        SCommandRouter.addVirtualCommand("comm", new SCommandObject().prefix("b"));


        EmptyCommandProcessor c = new EmptyCommandProcessor("comm");
        Bukkit.getServer().getCommandMap().register("comm", c);
        try {
            Class<?> craftServer = Bukkit.getServer().getClass();
            Method syncCommandsMethod = craftServer.getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}