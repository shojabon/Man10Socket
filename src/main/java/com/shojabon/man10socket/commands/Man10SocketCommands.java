package com.shojabon.man10socket.commands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.commands.subcommands.TestCommand;
import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;

public class Man10SocketCommands extends SCommandRouter {

    Man10Socket plugin;
    public Man10SocketCommands(Man10Socket plugin) {
        super(plugin, "test");
        this.plugin = plugin;
        registerCommands();
        registerEvents();
        pluginPrefix = "[Man10Socket]";
    }

    public void registerEvents(){
        setNoPermissionEvent(e -> e.sender.sendMessage(this.pluginPrefix + "§c§lあなたは権限がありません"));
        setOnNoCommandFoundEvent(e -> e.sender.sendMessage(this.pluginPrefix + "§c§lコマンドが存在しません"));
    }

    public void registerCommands(){
        addCommand(
                new SCommandObject()
                        .prefix("test")
                        .permission("man10shopv3.test")
                        .explanation("テスト")
                        .executor(new TestCommand(plugin))
        );
    }


}
