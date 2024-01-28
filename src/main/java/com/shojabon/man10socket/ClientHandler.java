package com.shojabon.man10socket;

import com.shojabon.man10socket.annotations.SocketFunctionDefinition;
import com.shojabon.man10socket.data.SocketFunction;
import com.shojabon.man10socket.socketfunctions.*;
import com.shojabon.man10socket.socketfunctions.GUI.GUIUpdateFunction;
import com.shojabon.man10socket.socketfunctions.GUI.OpenGUIFunction;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {
    private Socket connectionSocket;
    private UUID clientId;

    private boolean running = true;

    private LinkedBlockingQueue<JSONObject> messageQueue = new LinkedBlockingQueue<>();

    private final ConcurrentHashMap<String, SocketFunction> socketFunctions = new ConcurrentHashMap<>();

    //thread pool executor
    private final ExecutorService executor = Executors.newFixedThreadPool(200);

    public SetNameFunction setNameFunction = new SetNameFunction();
    public SubscribeToEventFunction subscribeToEventFunction = new SubscribeToEventFunction();


    public ClientHandler(Socket socket, UUID clientId) {
        this.connectionSocket = socket;
        this.clientId = clientId;

        registerSocketFunction(new VanillaCommandFunction());
        registerSocketFunction(new SCommandFunction());
        registerSocketFunction(new ReplyFunction());
        registerSocketFunction(new PlayerTellFunction());
        registerSocketFunction(new RegisterCommandFunction());

        registerSocketFunction(new OpenGUIFunction());
        registerSocketFunction(new GUIUpdateFunction());
        registerSocketFunction(setNameFunction);
        registerSocketFunction(subscribeToEventFunction);
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
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder messageBuilder = new StringBuilder();
            InputStream inputStream = connectionSocket.getInputStream();
            // Reading data from the client

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String receivedMessage = new String(buffer, 0, bytesRead);
                messageBuilder.append(receivedMessage);
                if (!messageBuilder.toString().contains("<E>")) continue;
                while (messageBuilder.toString().contains("<E>")){
                    String[] messages = messageBuilder.toString().split("<E>", 2);
                    try{
                        JSONObject message = new JSONObject(messages[0]);
                        executor.submit(() -> handleMessage(message));
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(messages.length == 1){
                            messageBuilder = new StringBuilder();
                        }else{
                            messageBuilder = new StringBuilder(messages[1]);
                        }
                    }
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
            String replyId = null;
            if(message.has("replyId")){
                replyId = message.getString("replyId");
            }
            socketFunctions.get(messageType).handleMessage(message, this, replyId);
        }
    }

    public void sendReply(String status, Object message, String replyId){
        if(replyId == null){
            return;
        }
        JSONObject responseObject = new JSONObject();
        responseObject.put("type", "reply");
        responseObject.put("status", status);
        responseObject.put("data", message);
        responseObject.put("replyId", replyId);
        send(responseObject);
    }

    public void send(JSONObject jsonObject){
        messageQueue.add(jsonObject);
    }

    private void sendToClient(JSONObject jsonObject){
        try {
            DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
            String message = jsonObject.toString() + "<E>";
//            System.out.println("send" + message);
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