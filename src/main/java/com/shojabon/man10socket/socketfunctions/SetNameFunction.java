package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.utils.ConcurrentHashMapWithTimeout;
import org.json.JSONObject;

import java.util.function.Consumer;

@SocketFunctionDefinition(
        name = "SetName",
        type = "set_name"
)
public class SetNameFunction extends SocketFunction {
    public String name;

    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"name"}){
            if(!message.has(key)){
                client.sendReply("error_invalid_args_" + key, null, replyId);
                return;
            }
        }
        name = message.getString("name");
    }
}
