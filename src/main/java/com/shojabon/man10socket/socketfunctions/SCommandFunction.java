package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.json.JSONObject;

@SocketFunctionDefinition(
        name = "SCommandHandler",
        type = "sCommand"
)
public class SCommandFunction extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        ServerExecCommandSender executor = new ServerExecCommandSender();
        SCommandRouter.executeSCommand(executor, message.getString("command"));
        if(!message.has("replyId")){
            return;
        }

        client.sendReply("success", executor.messageBuffer.toString(), message.getString("replyId"));
    }
}
