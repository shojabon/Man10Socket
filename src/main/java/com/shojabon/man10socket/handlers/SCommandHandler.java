package com.shojabon.man10socket.handlers;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data_class.SocketFunction;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SocketFunctionDefinition(
        name = "SCommandHandler",
        type = "sCommand"
)
public class SCommandHandler extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client) {
        ServerExecCommandSender executor = new ServerExecCommandSender();
        SCommandRouter.executeSCommand(executor, message.getString("command"));
        if(!message.has("replyId")){
            return;
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("type", "reply");
        response.put("status", "success");
        response.put("message", executor.messageBuffer.toString());
        response.put("replyId", message.getString("replyId"));
        client.send(new JSONObject(response));
    }
}
