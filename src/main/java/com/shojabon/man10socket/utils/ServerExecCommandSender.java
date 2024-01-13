package com.shojabon.man10socket.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.*;

public class ServerExecCommandSender implements RemoteConsoleCommandSender {

    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(1);
    private static final ConsoleCommandSender CONSOLE_COMMAND_SENDER = Bukkit.getConsoleSender();

    public final StringJoiner messageBuffer = new StringJoiner("\n");

    public CompletableFuture<String> executeCommand(String command, TimeUnit messagingUnit) {
        Future<Boolean> commandFuture = Bukkit.getScheduler().callSyncMethod(
                Bukkit.getPluginManager().getPlugin("Man10Socket"),
                () -> Bukkit.dispatchCommand(this, command)
        );

        CompletableFuture<String> future = new CompletableFuture<>();
        EXECUTOR.schedule(() -> {
            try {
                commandFuture.get(5, TimeUnit.SECONDS);
                future.complete(messageBuffer.toString());
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                future.completeExceptionally(new RuntimeException(e));
            }
        }, 0, messagingUnit);
        return future;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        //TODO should probably cover other control characters besides just section signs
        messageBuffer.add(ChatColor.stripColor(message));
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String msg : messages)
            sendMessage(msg);
    }

    // Paper
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        sendMessage(s);
    }

    // Paper
    public void sendMessage(@Nullable UUID uuid, @NotNull String[] strings) {
        sendMessage(strings);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return CONSOLE_COMMAND_SENDER.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int arg1) {
        return CONSOLE_COMMAND_SENDER.addAttachment(plugin, arg1);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String arg1, boolean arg2) {
        return CONSOLE_COMMAND_SENDER.addAttachment(plugin, arg1, arg2);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String arg1, boolean arg2, int arg3) {
        return CONSOLE_COMMAND_SENDER.addAttachment(plugin, arg1, arg2, arg3);
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return CONSOLE_COMMAND_SENDER.getEffectivePermissions();
    }

    @Override
    public boolean hasPermission(String arg0) {
        return CONSOLE_COMMAND_SENDER.hasPermission(arg0);
    }

    @Override
    public boolean hasPermission(Permission arg0) {
        return CONSOLE_COMMAND_SENDER.hasPermission(arg0);
    }

    @Override
    public boolean isPermissionSet(String arg0) {
        return CONSOLE_COMMAND_SENDER.isPermissionSet(arg0);
    }

    @Override
    public boolean isPermissionSet(Permission arg0) {
        return CONSOLE_COMMAND_SENDER.isPermissionSet(arg0);
    }

    @Override
    public void recalculatePermissions() {
        CONSOLE_COMMAND_SENDER.recalculatePermissions();
    }

    @Override
    public void removeAttachment(PermissionAttachment arg0) {
        CONSOLE_COMMAND_SENDER.removeAttachment(arg0);
    }

    @Override
    public boolean isOp() {
        return CONSOLE_COMMAND_SENDER.isOp();
    }

    @Override
    public void setOp(boolean arg0) {
        CONSOLE_COMMAND_SENDER.setOp(arg0);
    }

    @Override
    public String getName() {
        return CONSOLE_COMMAND_SENDER.getName();
    }

    @Override
    public Server getServer() {
        return CONSOLE_COMMAND_SENDER.getServer();
    }

    public void sendRawMessage(String raw) {
        CONSOLE_COMMAND_SENDER.sendRawMessage(raw);
    }

    // Paper
    public void sendRawMessage(@Nullable UUID uuid, @NotNull String raw) {
        CONSOLE_COMMAND_SENDER.sendRawMessage(uuid, raw);
    }

    @SuppressWarnings("ConstantConditions")
    public CommandSender.Spigot spigot() {
        try {
            return (CommandSender.Spigot) CommandSender.class.getMethod("spigot").invoke(CONSOLE_COMMAND_SENDER);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public @NotNull Component name() {
        return null;
    }

}
