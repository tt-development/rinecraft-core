package net.ttdev.rinecore.command;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentTime;
import net.ttdev.rinecore.chunk.RentedLand;
import net.ttdev.rinecore.file.Serializer;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.FileDirectories;
import net.ttdev.rinecore.util.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class LandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {

            player.sendMessage("/chunk rent <name> <[seconds] or [day, week, month]> - Rent the chunk you are standing in.");
            player.sendMessage("/chunk buy <name> - Buy the chunk you are standing in.");
            player.sendMessage("/chunk duration <name> <duration> - Set the duration of a rented chunk.");
            player.sendMessage("/chunk list - List all owned chunk.");

        } else if (args[0].equalsIgnoreCase("rent")) {

            if (!player.hasPermission(Permissions.LAND_RENT)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

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
                player.sendMessage(ChatColor.RED + "Example: /chunk rent <name> <day, week, month>");
                return true;
            }

            if (rPlayer.ownsChunkWithName(chunkName)) {
                player.sendMessage(ChatColor.RED + "You already own a chunk with that name.");
                return true;
            }

            RentTime rentTime = null;
            try {
                rentTime = RentTime.valueOf(args[2].toUpperCase());
            } catch (Exception e) {
            }

            int rentTimeSeconds;
            try {
                rentTimeSeconds = Integer.parseInt(args[2]);
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "You need to provide a time for the rented chunk.");
                player.sendMessage(ChatColor.RED + "Example: /chunk rent <name> <[seconds] or [day, week, month]>");
                return true;
            }

            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            RentedLand rentedChunk = rentTime == null ?
                    new RentedLand(player.getUniqueId(), chunkName, chunkX, chunkZ, rentTimeSeconds) :
                    new RentedLand(player.getUniqueId(), chunkName, chunkX, chunkZ, rentTime);

            Serializer.saveChunk(rentedChunk, FileDirectories.CHUNKS);

            player.sendMessage(ChatColor.GREEN + "Chunk rent successful.");

        } else if (args[0].equalsIgnoreCase("buy")) {

            if (!player.hasPermission(Permissions.LAND_BUY)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

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
            AbstractLand ownedChunk = new OwnedLand(player.getUniqueId(), chunkName, chunkX, chunkZ);

            Serializer.saveChunk(ownedChunk, FileDirectories.CHUNKS);

            player.sendMessage(ChatColor.GREEN + "Chunk purchase successful.");

        } else if (args[0].equalsIgnoreCase("duration")) {

            if (!player.hasPermission(Permissions.ADMIN)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            final String name;
            final int duration;
            try {
                name = args[1];
                duration = Integer.parseInt(args[2]);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Incorrect syntax! Example: /chunk duration property1 100");
                return true;
            }

            List<AbstractLand> chunks = Serializer.loadChunks(FileDirectories.CHUNKS);

            for (AbstractLand chunk : chunks) {

                if (!chunk.getName().equalsIgnoreCase(name) || !(chunk instanceof RentedLand)) {
                    continue;
                }

                ((RentedLand) chunk).setDuration(duration);
                Serializer.saveChunk(chunk, FileDirectories.CHUNKS);

                player.sendMessage(ChatColor.GREEN + "Changed duration of " + chunk.getName() + " to " + duration + ".");

                return true;
            }

            player.sendMessage(ChatColor.RED + "Couldn't find chunk with name " + name + ".");

        } else if (args[0].equalsIgnoreCase("list")) {

            List<AbstractLand> chunks;
            chunks = Serializer.loadChunks(player.getUniqueId(), FileDirectories.CHUNKS);

            chunks.forEach(landChunk -> player.sendMessage("Name: " + landChunk.getName() +
                    ", X: " + landChunk.getChunkX() +
                    ", Z: " + landChunk.getChunkZ()));
        }

        return true;
    }
}
