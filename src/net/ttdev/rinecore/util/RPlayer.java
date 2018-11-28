package net.ttdev.rinecore.util;

import net.ttdev.rinecore.land.AbstractChunk;
import net.ttdev.rinecore.land.OwnedChunk;
import net.ttdev.rinecore.land.RentedChunk;
import net.ttdev.rinecore.land.Serializer;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class RPlayer {

    private final List<AbstractChunk> chunks;

    public RPlayer(UUID uuid) {
        chunks = Serializer.deserializeLandChunks(uuid, FileDirectories.LAND_CHUNKS);
        System.out.println("Loaded " + chunks.size() + " chunks for " + uuid + ".");
    }

    public boolean ownsChunk(Chunk chunk) {
        return chunks.stream().anyMatch(e -> e.getChunkX() == chunk.getX() && e.getChunkZ() == chunk.getZ());
    }

    public boolean ownsChunkWithName(String name) {
        return chunks.stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    public Collection<AbstractChunk> getChunks() {
        return chunks;
    }

    public Collection<RentedChunk> getRentedLandChunks() {
        return chunks.stream()
                .filter(landChunk -> landChunk instanceof RentedChunk)
                .map(landChunk -> (RentedChunk) landChunk)
                .collect(Collectors.toList());
    }

    public Collection<OwnedChunk> getBoughtLandChunks() {
        return chunks.stream()
                .filter(landChunk -> landChunk instanceof OwnedChunk)
                .map(landChunk -> (OwnedChunk) landChunk)
                .collect(Collectors.toList());
    }
}
