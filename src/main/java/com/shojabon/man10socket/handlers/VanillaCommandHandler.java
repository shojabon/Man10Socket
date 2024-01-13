package com.shojabon.man10socket.handlers;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class VanillaCommandHandler {
    private ClientHandler client;
    public VanillaCommandHandler(ClientHandler client){
        this.client = client;
    }

    public void handleMessage(JSONObject message){
        CompletableFuture<String> future = new ServerExecCommandSender().executeCommand(message.getString("command"), TimeUnit.MILLISECONDS);
        future.thenAccept((String response) -> {

            HashMap<String, Object> responseObject = new HashMap<>();
            responseObject.put("type", "reply");
            responseObject.put("message", response);
            responseObject.put("replyId", message.getInt("replyId"));
            client.send(new JSONObject(responseObject));
        });

    }
}
