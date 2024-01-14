package com.shojabon.man10socket.handlers;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data_class.SocketFunction;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
@SocketFunctionDefinition(
        name = "VanillaCommandHandler",
        type = "command"
)
public class VanillaCommandHandler extends SocketFunction {
    public void handleMessage(JSONObject message, ClientHandler client){
        CompletableFuture<String> future = new ServerExecCommandSender().executeCommand(message.getString("command"), TimeUnit.MILLISECONDS);
        future.thenAccept((String response) -> {
            if(!message.has("replyId")){
                return;
            }
            HashMap<String, Object> responseObject = new HashMap<>();
            responseObject.put("type", "reply");
            responseObject.put("status", "success");
            responseObject.put("message", response);
            responseObject.put("replyId", message.getString("replyId"));
            client.send(new JSONObject(responseObject));
        });

    }
}
