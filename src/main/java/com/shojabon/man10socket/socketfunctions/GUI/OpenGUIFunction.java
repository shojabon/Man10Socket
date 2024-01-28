package com.shojabon.man10socket.socketfunctions.GUI;

import com.shojabon.man10socket.ClientHandler;
import com.shojabon.man10socket.Man10Socket;
import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.utils.JSONConverter;
import com.shojabon.mcutils.Utils.SInventory.SInventory;
import com.shojabon.mcutils.Utils.SInventory.SInventoryItem;
import com.shojabon.mcutils.Utils.SItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

@SocketFunctionDefinition(
        name = "Open GUI",
        type = "gui_open"
)
public class OpenGUIFunction extends SocketFunction {
    @Override
    public void handleMessage(JSONObject message, ClientHandler client, String replyId){
        for(String key : new String[]{"player", "schema", "size", "title", "id"}){
            if(!message.has(key)){
                client.sendReply("error_invalid_args_" + key, null, replyId);
                return;
            }
        }
        try{
            UUID playerUUID = UUID.fromString(message.getString("player"));
            Player player = Bukkit.getPlayer(playerUUID);
            String id = message.getString("id");
            if(player == null){
                client.sendReply("error_invalid_args_player", null, replyId);
                return;
            }

            SocketInventory inv = new SocketInventory(message.getString("title"), message.getInt("size"), Man10Socket.getPlugin(Man10Socket.class));

            for(Object obj : message.getJSONArray("schema")){
                JSONObject item = (JSONObject) obj;
                JSONArray slots = item.getJSONArray("slots");
                if(slots == null || slots.isEmpty()) {
                    client.sendReply("error_invalid_args_schema", null, replyId);
                    return;
                }
                ItemStack itemObject;
                if(item.getJSONObject("item") == null){
                    itemObject = new ItemStack(Material.AIR);
                }else{
                    itemObject = JSONConverter.JSONToItemStack(item.getJSONObject("item"));
                }
                SInventoryItem sItem = new SInventoryItem(itemObject);
                sItem.clickable(false);
                sItem.setAsyncEvent(e -> {
                    JSONObject data = new JSONObject();
                    data.put("player", e.getWhoClicked().getUniqueId());
                    data.put("rawSlot", e.getRawSlot());
                    data.put("clickType", e.getClick().name());
                    data.put("action", e.getAction().name());
                    data.put("id", id);
                    Man10Socket.sendEvent("gui_click", data);
                });
                for(Object slot : slots){
                    inv.setItem((int)slot, sItem);
                }
            }

            inv.setOnCloseEvent(e -> {
                JSONObject data = new JSONObject();
                data.put("player", e.getPlayer().getUniqueId());
                data.put("id", id);
                Man10Socket.sendEvent("gui_close", data);
                SocketInventory.activeSessions.remove(id);
            });
            SocketInventory.activeSessions.put(id, inv);
            inv.open(player);

            client.sendReply("success", null, replyId);
        }catch (Exception e){
            e.printStackTrace();
            client.sendReply("error_internal", null, replyId);
        }
    }
}
