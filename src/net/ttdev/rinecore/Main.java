package net.ttdev.rinecore;

import net.ttdev.rinecore.chunk.RentTimeDeductor;
import net.ttdev.rinecore.command.BalanceCommand;
import net.ttdev.rinecore.command.ChunkCommand;
import net.ttdev.rinecore.eventhandler.ChunkExpireEventHandler;
import net.ttdev.rinecore.eventhandler.PlayerInteractEventHandler;
import net.ttdev.rinecore.eventhandler.SignChangeEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ttdev.api.general.data.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This is the main class. Currently all the code
 * in here belongs to the playtime component of
 * RineCore and should be moved to another location
 * as soon as possible.
 */
public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChunkExpireEventHandler(), this);
        getServer().getPluginManager().registerEvents(new SignChangeEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEventHandler(), this);

        startPlaytimeClock();

        new RentTimeDeductor().runTaskTimer(this, RentTimeDeductor.DELAY_TICKS, RentTimeDeductor.DELAY_TICKS);

        /* Start checking for AFK players */
        new AFKThread().runTaskTimer(this, 20, 20);

        getServer().getPluginCommand("balance").setExecutor(new BalanceCommand());
        getServer().getPluginCommand("chunk").setExecutor(new ChunkCommand());

        getLogger().info(getName() + " enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(getName() + " disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase("playtime")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        /* if the sender doesn't provide any arguments they
         * are checking their own playtime. */
        if (args.length == 0) {
            sender.sendMessage("Playtime: " + PlayerExtensionsKt.getFormattedPlaytime(player));
        } else {

            /* if the sender provides 1 argument it's assumed
             * they're checking the playtime of another player. */
            Player otherPlayer = Bukkit.getPlayer(args[0]);
            sender.sendMessage(
                    (otherPlayer == null) ? "Couldn't find " + args[0] :
                            otherPlayer.getName() + "'s playtime: " + PlayerExtensionsKt.getFormattedPlaytime(otherPlayer) + "."
            );
        }

        return true;
    }

    public static Main getInstance() {
        return instance;
    }

    /**
     * Changes the format of a player chat message
     * to append the playtime as superscripted numbers
     * in front of the players displayname.
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getDisplayName() +
                superscriptNumbers(PlayerExtensionsKt.getPlaytimeHours(player)) +
                ": " + event.getMessage());
    }

    private void startPlaytimeClock() {

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> PlayerExtensionsKt.setPlaytime(player, PlayerExtensionsKt.getPlaytime(player) + 1));
            }
        }.runTaskTimer(this, 20, 20);
    }

    public static Map<UUID, Long> getPlaytimeEntries() {
        DataStore dataStore = new DataStore(instance);
        return dataStore.loadMap("playtimes", UUID::fromString, Long::parseLong);
    }

    /**
     * Save `entries` to configuration, replacing
     * all previous existing entries.
     */
    public static void setPlaytimeEntries(Map<UUID, Long> entries) {
        Map<String, Long> remapped;
        remapped = entries.keySet().stream().collect(Collectors.toMap(UUID::toString, entries::get));
        DataStore dataStore = new DataStore(instance);
        dataStore.saveMap("playtimes", remapped);
        instance.saveConfig();
    }

    /**
     * All numbers are returned as their superscript
     * counterparts.
     * E.g. 1234 becomes ¹²³⁴
     */
    public String superscriptNumbers(Long numbers) {
        Map<Character, Character> superscriptMap = new HashMap<>();
        superscriptMap.put('1', '\u00B9');
        superscriptMap.put('2', '\u00B2');
        superscriptMap.put('3', '\u00B3');
        superscriptMap.put('4', '\u2074');
        superscriptMap.put('5', '\u2075');
        superscriptMap.put('6', '\u2076');
        superscriptMap.put('7', '\u2077');
        superscriptMap.put('8', '\u2078');
        superscriptMap.put('9', '\u2079');
        superscriptMap.put('0', '\u2070');

        String newSequence = numbers.toString();
        for (Map.Entry<Character, Character> entry : superscriptMap.entrySet()) {
            newSequence = newSequence.replace(entry.getKey(), entry.getValue());
        }

        return newSequence;
    }
}