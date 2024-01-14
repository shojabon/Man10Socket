package com.shojabon.man10socket;

import com.shojabon.man10socket.commands.Man10SocketCommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class Man10Socket extends JavaPlugin {

    private ServerSocket welcomeSocket;
    private boolean running = true; // サーバーが実行中かどうかを制御するフラグ

    public static ConcurrentHashMap<UUID, ClientHandler> clients = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new SocketEventHandler(this), this);
        new Man10SocketCommands(this);
        new Thread(() -> {
            try {
                int port = 6789; // サーバーのポート番号
                welcomeSocket = new ServerSocket(port);

                System.out.println("サーバーがポート " + port + " で待機中...");

                while (running) {
                    // クライアントからの接続を待機
                    Socket connectionSocket = welcomeSocket.accept();

                    // 新しいスレッドでクライアントの処理を開始
                    UUID uuid = UUID.randomUUID();
                    ClientHandler handler = new ClientHandler(connectionSocket, uuid);
                    clients.put(uuid, handler);
                    new Thread(handler).start();
                }
            } catch (IOException e) {
                if (running) {
                    // running が false の場合は通常の停止なので、例外を表示しない
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        running = false; // サーバースレッドを停止させる
        try {
            if (welcomeSocket != null && !welcomeSocket.isClosed()) {
                welcomeSocket.close(); // サーバーソケットを閉じる
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(ClientHandler client: clients.values()){
            client.close();
        }
    }

    public static int roundRobin = 0;
    public static void send(JSONObject message){
        // send client round robin
        if(roundRobin >= clients.size()) roundRobin = 0;
        clients.values().toArray(new ClientHandler[0])[roundRobin].send(message);
        roundRobin++;
    }

    public static void sendEvent(String eventName, JSONObject message){
        JSONObject obj = new JSONObject();
        obj.put("type", "event");
        obj.put("event", eventName);
        obj.put("data", message);
        send(obj);
    }
    public static JSONObject getPlayerJSON(Player p){
        Map<String, Object> result = new HashMap<>();
        result.put("name", p.getName());
        result.put("uuid", p.getUniqueId().toString());
        if(p.getAddress() != null) result.put("ipAddress", p.getAddress().getAddress().getHostAddress());
        return new JSONObject(result);
    }

//    @EventHandler
//    public void onInteract(PlayerMoveEvent e){
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("player", getPlayerJSON(e.getPlayer()));
//        data.put("x", e.getTo().getX());
//        data.put("y", e.getTo().getY());
//        data.put("z", e.getTo().getZ());
//        data.put("time", System.currentTimeMillis());
//        JSONObject obj = new JSONObject(data);
//        // send client round robin
//        send(obj);
//
//    }
}
