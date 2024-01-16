package com.shojabon.man10socket.commands;

import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.commands.subcommands.PlayersInfoCommand;
import com.shojabon.man10socket.commands.subcommands.ReloadCommand;
import com.shojabon.man10socket.commands.subcommands.TestCommand;
import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;

import java.util.ArrayList;
import java.util.List;

public class Man10SocketCommands extends SCommandRouter {

    Man10Socket plugin;
    public Man10SocketCommands(Man10Socket plugin) {
        super(plugin, "man10socket");
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
                        .permission("man10socket.test")
                        .explanation("テスト")
                        .executor(new TestCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .prefix("reload")
                        .permission("man10socket.reload")
                        .explanation("リロード")
                        .executor(new ReloadCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .prefix("playersInfo")
                        .permission("man10socket.playersInfo")
                        .explanation("サーバーにいるプレイヤーの情報を取得")
                        .executor(new PlayersInfoCommand(plugin))
        );

    }


}
