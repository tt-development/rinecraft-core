package net.ttdev.rinecore.util;

import net.ttdev.rinecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionScheduler {

    public static final int TWO_SECONDS = 20 * 2;

    public static void sendMessageLater(String message, long delay, Player player) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.sendMessage(message), delay);
    }

    public static void doLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), runnable, delay);
    }
}
