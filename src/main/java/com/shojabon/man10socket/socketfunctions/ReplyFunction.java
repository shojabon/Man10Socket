package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.utils.ConcurrentHashMapWithTimeout;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

@SocketFunctionDefinition(
        name = "ReplyFunction",
        type = "reply"
)
public class ReplyFunction extends SocketFunction {
    public static ConcurrentHashMapWithTimeout<String, Consumer<JSONObject>> replyFunctions = new ConcurrentHashMapWithTimeout<>(5);
    public static ConcurrentHashMapWithTimeout<String, Object> replyLocks = new ConcurrentHashMapWithTimeout<>(5);
    public static ConcurrentHashMapWithTimeout<String, JSONObject> replyData = new ConcurrentHashMapWithTimeout<>(5);

    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        replyData.put(replyId, message);
        if(replyFunctions.containsKey(replyId)){
            replyFunctions.get(replyId).accept(message);
            replyFunctions.remove(replyId);
        }
        if(replyLocks.containsKey(replyId)){
            Object lock = replyLocks.get(replyId);
            synchronized (lock){
                lock.notify();
                replyLocks.remove(replyId);
            }
        }
    }
}
