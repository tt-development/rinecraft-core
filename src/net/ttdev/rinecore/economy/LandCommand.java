package net.ttdev.rinecore.economy;

import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class LandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {

            player.sendMessage("/land rent <name> <time: day, week, month>- Rent the chunk you are standing in.");
            player.sendMessage("/land buy <name> - Buy the chunk you are standing in.");
            player.sendMessage("/land list - List all owned land.");

            return true;
        }

        if (args[0].equalsIgnoreCase("rent")) {

            Chunk chunk = player.getLocation().getChunk();

            UUID owner = player.getUniqueId();

            String rentName;
            try {
                rentName = args[1];
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You need to provide a name for the rented land.");
                player.sendMessage(ChatColor.RED + "Example: /land rent <name> <time: day, week, month>");
                return true;
            }

            RentTime rentTime;
            try {
                rentTime = RentTime.valueOf(args[2].toUpperCase());
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You need to provide a time for the rented land.");
                player.sendMessage(ChatColor.RED + "Example: /land rent <name> <time: day, week, month>");
                return true;
            }

            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            LandChunk landChunk = new RentedLandChunk(owner, rentName, chunkX, chunkZ, rentTime.getSeconds());

            Serializer.serializeLandChunk(FileDirectories.LAND_CHUNKS, landChunk);

            player.sendMessage(ChatColor.GREEN + "Land rent successful.");

            return true;
        }

        if (args[0].equalsIgnoreCase("buy")) {

            Chunk chunk = player.getLocation().getChunk();

            UUID owner = player.getUniqueId();

            String landName;
            try {
                landName = args[1];
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You need to provide a name for the bought chunk.");
                return true;
            }

            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            LandChunk landChunk = new OwnedLandChunk(owner, landName, chunkX, chunkZ);

            Serializer.serializeLandChunk(FileDirectories.LAND_CHUNKS, landChunk);

            player.sendMessage(ChatColor.GREEN + "Land purchase successful.");

            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {

            List<LandChunk> landChunks;
            landChunks = Serializer.deserializeLandChunks(player.getUniqueId(), FileDirectories.LAND_CHUNKS);

            landChunks.forEach(landChunk -> player.sendMessage("Name: " + landChunk.getName() +
                    ", X: " + landChunk.getChunkX() +
                    ", Z: " + landChunk.getChunkZ()));

            return true;
        }

        return false;
    }
}
