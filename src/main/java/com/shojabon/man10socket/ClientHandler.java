package com.shojabon.man10socket;

import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data_class.SocketFunction;
import com.shojabon.man10socket.handlers.SCommandHandler;
import com.shojabon.man10socket.handlers.VanillaCommandHandler;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements Runnable {
    private Socket connectionSocket;
    private UUID clientId;

    private boolean running = true;

    private LinkedBlockingQueue<JSONObject> messageQueue = new LinkedBlockingQueue<>();

    private final ConcurrentHashMap<String, SocketFunction> socketFunctions = new ConcurrentHashMap<>();


    public ClientHandler(Socket socket, UUID clientId) {
        this.connectionSocket = socket;
        this.clientId = clientId;

        registerSocketFunction(new VanillaCommandHandler());
        registerSocketFunction(new SCommandHandler());
    }

    private void registerSocketFunction(SocketFunction function){
        SocketFunctionDefinition definition = function.getClass().getAnnotation(SocketFunctionDefinition.class);
        if(definition == null) return;
        socketFunctions.put(definition.type(), function);
    }

    @Override
    public void run() {

        new Thread(() -> {
            while (running) {
                try {
                    // キューからメッセージを取り出して送信
                    JSONObject message = messageQueue.poll(1000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                    if (message != null) {
                        sendToClient(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            StringBuilder messageBuilder = new StringBuilder();
            int character;
            while ((character = inFromClient.read()) != -1) {
                // 文字を追加
                messageBuilder.append((char) character);

                // メッセージの終端を確認
                if (messageBuilder.toString().endsWith("<E>")) {
                    // '<E>' を除去して処理
                    String message = messageBuilder.substring(0, messageBuilder.length() - 3);

                    try {
                        // JSONObject に変換
                        JSONObject jsonObject = new JSONObject(message);
                        handleMessage(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // StringBuilder をリセット
                    messageBuilder = new StringBuilder();
                }
            }
            close();
        } catch (IOException e) {
            System.out.println("Client Disconnected");
            close();
        }
    }

    private void handleMessage(JSONObject message){
        String messageType = message.getString("type");
        if(socketFunctions.containsKey(messageType)){
            socketFunctions.get(messageType).handleMessage(message, this);
        }
    }

    public void send(JSONObject jsonObject){
        messageQueue.add(jsonObject);
    }

    private void sendToClient(JSONObject jsonObject){
        try {
            DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
            String message = jsonObject.toString() + "<E>";
            System.out.println("send" + message);
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8); // UTF-8エンコーディングを使用
            outToServer.write(messageBytes); // バイト配列を書き込む
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close(){
        try {
            connectionSocket.close();
            running = false;
            Man10Socket.clients.remove(clientId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}