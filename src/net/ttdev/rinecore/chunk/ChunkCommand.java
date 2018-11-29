package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.Main;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class ChunkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {

            player.sendMessage("/chunk rent <name> <time: day, week, month> - Rent the chunk you are standing in.");
            player.sendMessage("/chunk unrent <name> - Stop renting a chunk of chunk.");
            player.sendMessage("/chunk buy <name> - Buy the chunk you are standing in.");
            player.sendMessage("/chunk list - List all owned chunk.");

            return true;
        }

        if (args[0].equalsIgnoreCase("rent")) {

            Chunk chunk = player.getLocation().getChunk();

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.ownsChunk(chunk)) {
                player.sendMessage(ChatColor.RED + "You already own this chunk.");
                return true;
            }

            String chunkName;
            try {
                chunkName = args[1];
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You need to provide a name for the rented chunk.");
                player.sendMessage(ChatColor.RED + "Example: /chunk rent <name> <time: day, week, month>");
                return true;
            }

            if (rPlayer.ownsChunkWithName(chunkName)) {
                player.sendMessage(ChatColor.RED + "You already own a chunk with that name.");
                return true;
            }

            RentTime rentTime;
            try {
                rentTime = RentTime.valueOf(args[2].toUpperCase());
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You need to provide a time for the rented chunk.");
                player.sendMessage(ChatColor.RED + "Example: /chunk rent <name> <time: day, week, month>");
                return true;
            }

            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            RentedChunk rentedChunk = new RentedChunk(player.getUniqueId(), chunkName, chunkX, chunkZ, rentTime.getSeconds());

            Main.getRentTimeManager().add(rentedChunk);
            Serializer.saveChunk(FileDirectories.LAND_CHUNKS, rentedChunk);

            player.sendMessage(ChatColor.GREEN + "Chunk rent successful.");

            return true;
        }

        if (args[0].equalsIgnoreCase("buy")) {

            Chunk chunk = player.getLocation().getChunk();

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.ownsChunk(chunk)) {
                player.sendMessage(ChatColor.RED + "You already own this chunk.");
                return true;
            }

            String chunkName;
            try {
                chunkName = args[1];
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You need to provide a name for the bought chunk.");
                return true;
            }

            if (rPlayer.ownsChunkWithName(chunkName)) {
                player.sendMessage(ChatColor.RED + "You already own a chunk with that name.");
                return true;
            }

            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            AbstractChunk ownedChunk = new OwnedChunk(player.getUniqueId(), chunkName, chunkX, chunkZ);

            Serializer.saveChunk(FileDirectories.LAND_CHUNKS, ownedChunk);

            player.sendMessage(ChatColor.GREEN + "Chunk purchase successful.");

            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {

            List<AbstractChunk> chunks;
            chunks = Serializer.loadChunks(player.getUniqueId(), FileDirectories.LAND_CHUNKS);

            chunks.forEach(landChunk -> player.sendMessage("Name: " + landChunk.getName() +
                    ", X: " + landChunk.getChunkX() +
                    ", Z: " + landChunk.getChunkZ()));

            return true;
        }

        return false;
    }
}