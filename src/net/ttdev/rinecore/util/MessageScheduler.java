package net.ttdev.rinecore.util;

import net.ttdev.rinecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageScheduler {

    public static final int TWO_SECONDS = 20 * 2;

    public static void sendLater(String message, long delay, Player player) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.sendMessage(message), delay);
    }
}
