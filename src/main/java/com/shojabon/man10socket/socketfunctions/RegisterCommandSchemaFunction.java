package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.EmptyCommandProcessor;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

@SocketFunctionDefinition(
        name = "Register Command",
        type = "command_register_schema"
)
public class RegisterCommandSchemaFunction extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"command", "schema"}){
            if(!message.has(key)){
                client.sendReply("error_invalid_args_" + key, null, replyId);
                return;
            }
        }

        SCommandObject commandObject = new SCommandObject();
        for(Object schema : message.getJSONArray("schema")){
            JSONObject schemaObject = (JSONObject) schema;
            if(!schemaObject.has("type")){
                client.sendReply("error_invalid_args_prefix", null, replyId);
                return;
            }
            if(!schemaObject.has("argument")){
                client.sendReply("error_invalid_args_argument", null, replyId);
                return;
            }

            String type = schemaObject.getString("type");
            if(type.equals("prefix")){
                commandObject.prefix(schemaObject.getString("argument"));
            }else if(type.equals("argument")){
                if(!schemaObject.has("selections")){
                    client.sendReply("error_invalid_args_selection", null, replyId);
                    return;
                }
                commandObject.argument(schemaObject.getString("argument"), (commandSender) -> {
                    ArrayList<String> selections = new ArrayList<>();
                    for(Object selection : schemaObject.getJSONArray("selections")){
                        selections.add((String) selection);
                    }
                    return selections;
                });
            }
        }

        SCommandRouter.addVirtualCommand(message.getString("command"), commandObject);

        client.sendReply("success", null, replyId);

    }
}
