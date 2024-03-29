package com.shojabon.man10socket.utils;

import com.shojabon.mcutils.Utils.SItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONConverter {

    public static JSONObject locationToJSON(@Nullable  Location loc){
        if (loc == null) return null;
        JSONObject result = new JSONObject();
        result.put("x", loc.getX());
        result.put("y", loc.getY());
        result.put("z", loc.getZ());
        result.put("world", loc.getWorld().getName());
        result.put("pitch", loc.getPitch());
        result.put("yaw", loc.getYaw());
        return result;
    }

    public static JSONObject itemStackToJSON(ItemStack item){
        if (item == null) return null;
        SItemStack sItemStack = new SItemStack(item);
        JSONObject itemData = new JSONObject();

        itemData.put("typeBase64", sItemStack.getItemTypeBase64(true));
        itemData.put("typeMd5", sItemStack.getItemTypeMD5(true));
        itemData.put("displayName", sItemStack.getDisplayName());
        itemData.put("amount", sItemStack.getAmount());
        itemData.put("material", sItemStack.getType().name());
        itemData.put("maxDamage", sItemStack.getMaxDamage());
        itemData.put("maxStackSize", sItemStack.getMaxStackSize());

        List<String> lore = new ArrayList<>();
        int customModelData = -1;
        if(sItemStack.build().hasItemMeta()){
            lore = sItemStack.getLore();
            customModelData = sItemStack.getCustomModelData();
        }
        itemData.put("lore", lore);
        itemData.put("customModelData", customModelData);
        return itemData;
    }

    public static JSONObject playerToJSON(Player p){
        JSONObject result = new JSONObject();
        result.put("name", p.getName());
        result.put("uuid", p.getUniqueId().toString());
        if(p.getAddress() != null) result.put("ipAddress", p.getAddress().getAddress().getHostAddress());
        return result;
    }

    public static ItemStack JSONToItemStack(JSONObject data){
        SItemStack sItemStack = null;
        if(data.has("typeBase64")) {
            sItemStack = SItemStack.fromBase64(data.getString("typeBase64"));
        }else if(data.has("material")){
            sItemStack = new SItemStack(Material.valueOf(data.getString("material")));
        }
        if(sItemStack == null) return null;

        if(data.has("amount")) sItemStack.setAmount(data.getInt("amount"));
        if(data.has("displayName")) sItemStack.setDisplayName(data.getString("displayName"));
        if(data.has("lore")) {
            for(Object obj : data.getJSONArray("lore")){
                sItemStack.addLore((String) obj);
            }
        }
        if(data.has("customModelData")) sItemStack.setCustomModelData(data.getInt("customModelData"));
        return sItemStack.build();
    }

}
