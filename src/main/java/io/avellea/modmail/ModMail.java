package io.avellea.modmail;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class ModMail implements CommandExecutor {


    private static void sendWebhookMessage(String webhookUrl, String SenderName, String SenderUUID, String message) {
        if(!webhookUrl.isEmpty()) {
            try {
                final HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
                connection.setDoOutput(true);
                try(final OutputStream outputStream = connection.getOutputStream()) {
                    String result = String.format("{\"username\": \"%s - ModMail\", \"avatar_url\": \"https://minotar.net/avatar/%s\", \"content\": \"%s\"}", SenderName, SenderUUID, message);
                    outputStream.write((result).getBytes(StandardCharsets.UTF_8));
                }
                connection.getInputStream();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /modmail <message>");
            return true;
        }
        String message = String.join(" ", args);
        Bukkit.getServer().getLogger().info(message);
        sender.sendMessage(ChatColor.GREEN + "[ModMail] Message sent!");

        if(sender instanceof Player){
            Player player = (Player) sender;

            String SenderName = player.getName();
            String SenderUUID = player.getUniqueId().toString();

            String webhookUrl = App.instance.getConfig().getString("Discord_Webhook_URL");

            sendWebhookMessage(webhookUrl, SenderName, SenderUUID, message);    

        }
        return true;
    }
}
