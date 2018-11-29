package net.ttdev.rinecore.util;

import net.ttdev.rinecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageScheduler {

    public static void sendLater(String message, Player player, long delay) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.sendMessage(message), delay);
    }
}
