package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.EmptyCommandProcessor;
import com.shojabon.man10socket.data.SocketFunction;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SocketFunctionDefinition(
        name = "Register Command",
        type = "command_register"
)
public class RegisterCommandFunction extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"command"}){
            if(!message.has(key)){
                client.sendReply("error_invalid_args_" + key, null, replyId);
                return;
            }
        }
        Bukkit.getServer().getCommandMap().register(message.getString("command"), new EmptyCommandProcessor(message.getString("command")));
        try {
            Class<?> craftServer = Bukkit.getServer().getClass();
            Method syncCommandsMethod = craftServer.getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        client.sendReply("success", null, replyId);

    }
}
