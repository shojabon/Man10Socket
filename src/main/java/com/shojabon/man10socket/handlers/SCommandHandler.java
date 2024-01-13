package com.shojabon.man10socket.handlers;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SCommandHandler {
    private ClientHandler client;
    public SCommandHandler(ClientHandler client){
        this.client = client;
    }

    public void handleMessage(JSONObject message){
        ServerExecCommandSender executor = new ServerExecCommandSender();
        SCommandRouter.executeSCommand(executor, message.getString("command"));

        HashMap<String, Object> response = new HashMap<>();
        response.put("type", "reply");
        response.put("message", executor.messageBuffer.toString());
        response.put("replyId", message.getInt("replyId"));
        client.send(new JSONObject(response));

    }
}
