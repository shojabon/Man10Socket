package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import org.json.JSONObject;

import java.util.ArrayList;

@SocketFunctionDefinition(
        name = "subscribeToEvent",
        type = "event_subscribe"
)
public class SubscribeToEventFunction extends SocketFunction {
    public ArrayList<String> eventTypes = new ArrayList<>();
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"event_types"}){
            if(!message.has(key)){
                client.sendReply("error_invalid_args_" + key, null, replyId);
                return;
            }
        }
        for(Object eventType : message.getJSONArray("event_types")){
            eventTypes.add(eventType.toString());
        }


    }
}
