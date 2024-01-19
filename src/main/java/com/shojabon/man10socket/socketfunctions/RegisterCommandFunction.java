package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.EmptyCommandProcessor;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@SocketFunctionDefinition(
        name = "Register Command",
        type = "command_register"
)
public class RegisterCommandFunction extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"command", "schema"}){
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
        } catch (Exception e) {
            client.sendReply("error_internal", null, replyId);
            return;
        }

        SCommandRouter.clearVirtualCommand(message.getString("command"));

        JSONArray schemaArray = message.getJSONArray("schema");
        for(Object schemaObj : schemaArray){
            JSONObject schema = (JSONObject) schemaObj;
            SCommandObject commandObject = new SCommandObject();
            if(schema.has("permission")){
                commandObject.permission(schema.getString("permission"));
            }
            if(schema.has("explanation")){
                commandObject.explanation(schema.getString("explanation"));
            }
            for(Object innerSchemaObject : schema.getJSONArray("schema")){
                if(innerSchemaObject instanceof String){
                    commandObject.prefix((String) innerSchemaObject);
                }else if(innerSchemaObject instanceof JSONArray){
                    commandObject.argument(null, (commandSender) -> {
                        ArrayList<String> selections = new ArrayList<>();
                        for(Object selection : (JSONArray) innerSchemaObject){
                            selections.add((String) selection);
                        }
                        return selections;
                    });
                }
            }

            SCommandRouter.addVirtualCommand(message.getString("command"), commandObject);

        }

        client.sendReply("success", null, replyId);

    }
}
