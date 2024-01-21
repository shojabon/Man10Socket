package com.shojabon.man10socket;

import com.shojabon.man10socket.utils.JSONConverter;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SocketEventHandler implements @NotNull Listener {
    Man10Socket main;
    public SocketEventHandler(Man10Socket main){
        this.main = main;
    }

//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent e){
//        JSONObject data = new JSONObject();
//        data.put("player", e.getPlayer().getUniqueId());
//        data.put("location", JSONConverter.locationToJSON(e.getTo()));
//        Man10Socket.sendEvent("player_move", data);
//    }
//
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent e){
//        JSONObject data = new JSONObject();
//        data.put("player", e.getPlayer().getUniqueId());
//        data.put("action", e.getAction().name());
//        data.put("interactionPoint", JSONConverter.locationToJSON(e.getInteractionPoint()));
//        data.put("clickedBlock", JSONConverter.locationToJSON(e.getClickedBlock().getLocation()));
//        data.put("clickedBlockMaterial", e.getClickedBlock().getType().name());
//        data.put("blockFace", e.getBlockFace().name());
//        data.put("hand", e.getHand().name());
//        data.put("item", JSONConverter.itemStackToJSON(e.getItem()));
//        Man10Socket.sendEvent("player_interact", data);
//    }
//
    @EventHandler
    public void playerCommandEvent(PlayerCommandPreprocessEvent e){
        JSONObject data = new JSONObject();
        data.put("player", e.getPlayer().getUniqueId());
        data.put("command", e.getMessage());
        Man10Socket.sendEvent("player_command_send", data);
//        e.setCancelled(true);
    }
//
//    @EventHandler
//    public void inventoryClickEvent(InventoryClickEvent e){
//        JSONObject data = new JSONObject();
//        data.put("player", e.getWhoClicked().getUniqueId());
//        data.put("action", e.getAction().name());
//        data.put("clickType", e.getAction());
//        data.put("item", JSONConverter.itemStackToJSON(e.getCurrentItem()));
//        data.put("slot", e.getSlot());
//        data.put("slotType", e.getSlotType().name());
//        data.put("clickedInventory", e.getClickedInventory().getType().name());
//        data.put("cursor", JSONConverter.itemStackToJSON(e.getCursor()));
//        data.put("rawSlot", e.getRawSlot());
//        data.put("inventoryName", e.getView().getTitle());
//        Man10Socket.sendEvent("inventory_click", data);
//    }
//
//    @EventHandler
//    public void inventoryCloseEvent(InventoryCloseEvent e){
//        JSONObject data = new JSONObject();
//        data.put("player", e.getPlayer().getUniqueId());
//        data.put("inventoryName", e.getView().getTitle());
//        Man10Socket.sendEvent("inventory_close", data);
//    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        JSONObject data = new JSONObject();
        data.put("player", JSONConverter.playerToJSON(e.getPlayer()));
        Man10Socket.sendEvent("player_join", data);
    }
    @EventHandler
    public void playerQuit(PlayerQuitEvent e){
        JSONObject data = new JSONObject();
        data.put("player", JSONConverter.playerToJSON(e.getPlayer()));
        Man10Socket.sendEvent("player_quit", data);
    }

    @EventHandler
    public void onInteractSignEvent(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;
        if(!e.getClickedBlock().getType().name().contains("SIGN")) return;
        JSONObject data = new JSONObject();
        data.put("player", e.getPlayer().getUniqueId());
        data.put("location", JSONConverter.locationToJSON(e.getClickedBlock().getLocation()));
        data.put("blockType", e.getClickedBlock().getType().name());
        data.put("action", e.getAction().name());
        Man10Socket.sendEvent("player_interact_sign", data);
    }
}
