package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
@SocketFunctionDefinition(
        name = "VanillaCommandHandler",
        type = "command"
)
public class VanillaCommandFunction extends SocketFunction {
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        CompletableFuture<String> future = new ServerExecCommandSender().executeCommand(message.getString("command"), TimeUnit.MILLISECONDS);
        future.thenAccept((String response) -> {
            if(!message.has("replyId")){
                return;
            }
            client.sendReply("success", response, message.getString("replyId"));
        });

    }
}
