package net.ttdev.rinecore.util;

import net.ttdev.rinecore.land.LandChunk;
import net.ttdev.rinecore.land.Serializer;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.UUID;

public class LandChunkHelper {

    private static boolean isAlreadyOwned(UUID player, LandChunk landChunk) {
        List<LandChunk> landChunks = Serializer.deserializeLandChunks(player, FileDirectories.LAND_CHUNKS);
        return landChunks.stream().anyMatch(chunk -> chunk.equals(landChunk));
    }

    private static boolean isNameTaken(UUID player, LandChunk landChunk) {
        List<LandChunk> landChunks = Serializer.deserializeLandChunks(player, FileDirectories.LAND_CHUNKS);
        return landChunks.stream().anyMatch(chunk -> chunk.sameName(landChunk));
    }

    public static String checkNameAndOwnage(UUID player, LandChunk landChunk) {

        if (isAlreadyOwned(player, landChunk)) {
            return ChatColor.RED + "You already own this land.";
        }
        if (isNameTaken(player, landChunk)) {
            return ChatColor.RED + "You already own land with this name.";
        }

        return null;
    }
}
