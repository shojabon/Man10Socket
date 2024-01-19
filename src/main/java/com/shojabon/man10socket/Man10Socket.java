package com.shojabon.man10socket;

import com.shojabon.man10socket.commands.Man10SocketCommands;
import com.shojabon.man10socket.socketfunctions.ReplyFunction;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public final class Man10Socket extends JavaPlugin {

    private ServerSocket welcomeSocket;
    public boolean running = true; // サーバーが実行中かどうかを制御するフラグ

    public static ConcurrentHashMap<UUID, ClientHandler> clients = new ConcurrentHashMap<>();

    final static BlockingQueue<JSONObject> sendQueue = new LinkedBlockingQueue<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new SocketEventHandler(this), this);
        new Man10SocketCommands(this);

        startServer();
    }

    public void startServer(){
        new Thread(() -> {
            try {
                int port = getConfig().getInt("listeningPort"); // サーバーのポート番号
                welcomeSocket = new ServerSocket(port);

                System.out.println("サーバーがポート " + port + " で待機中...");

                while (running) {
                    // クライアントからの接続を待機
                    Socket connectionSocket = welcomeSocket.accept();

                    // 新しいスレッドでクライアントの処理を開始
                    UUID uuid = UUID.randomUUID();
                    ClientHandler handler = new ClientHandler(connectionSocket, uuid);
                    clients.put(uuid, handler);
                    Man10Socket.sendEvent("server_connected", new JSONObject());
                    new Thread(handler).start();
                }
            } catch (IOException e) {
                if (running) {
                    // running が false の場合は通常の停止なので、例外を表示しない
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (running) {
                try {
                    // キューからメッセージを取り出して送信
                    JSONObject message = sendQueue.poll(1000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                    if (message == null) continue;
                    this.sendInternal(message);
                } catch (Exception e) {
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

    private void sendInternal(JSONObject message){
        // send client round robin
        if(clients.isEmpty()) return;
        for(ClientHandler client: clients.values()){
            client.send(message);
            return;
        }
    }

    public static JSONObject send(JSONObject message){
        return send(message, false, null);
    }

    public static JSONObject send(JSONObject message, Boolean reply){
        return send(message, reply, null);
    }

    public static JSONObject send(JSONObject message, Consumer<JSONObject> callback){
        return send(message, false, callback);
    }

    private static JSONObject send(JSONObject message, Boolean reply, Consumer<JSONObject> callback){
        if (callback != null || reply) {
            String replyId = UUID.randomUUID().toString();
            message.put("replyId", replyId);

            if (callback != null) {
                ReplyFunction.replyFunctions.put(replyId, callback);
                sendQueue.add(message);
            } else {
                // Declare lock as a final object to be able to use it in the synchronized block
                final Object lock = new Object();
                ReplyFunction.replyLocks.put(replyId, lock);
                sendQueue.add(message);

                synchronized (lock) {
                    try {
                        lock.wait(1000); // Wait for 1 second or until notified
                        JSONObject replyData = ReplyFunction.replyData.remove(replyId); // Remove and return the reply data
                        return replyData;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Properly handle the InterruptedException
                        return null;
                    }
                }
            }
        }else{
            sendQueue.add(message);
        }
        return null;
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
