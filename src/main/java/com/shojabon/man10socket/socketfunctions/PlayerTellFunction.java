package com.shojabon.man10socket.socketfunctions;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.utils.ServerExecCommandSender;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.UUID;

@SocketFunctionDefinition(
        name = "PlayerTell",
        type = "player_tell"
)
public class PlayerTellFunction extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"player", "message"}){
            if(!message.has(key)){
                client.sendReply("error_invalid_args_" + key, null, replyId);
                return;
            }
        }
        try{
            UUID playerUUID = UUID.fromString(message.getString("player"));
            Player player = Bukkit.getPlayer(playerUUID);
            if(player == null){
                client.sendReply("error_invalid_args_player", null, replyId);
                return;
            }
            player.sendMessage(message.getString("message"));
            client.sendReply("success", null, replyId);
        }catch (Exception e){
            client.sendReply("error_internal", null, replyId);
            return;
        }
    }
}
