package net.ttdev.rinecore.player.data;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentedLand;
import net.ttdev.rinecore.file.Serializer;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class RChunkData extends RPlayerData implements IChunkData {

    private final List<AbstractLand> chunks;

    public RChunkData(UUID uuid) {
        super(uuid);
        chunks = Serializer.loadChunks(uuid, FileDirectories.CHUNKS);
    }

    @Override
    public void addChunk(AbstractLand chunk) {
        Serializer.saveChunk(chunk, FileDirectories.CHUNKS);
    }

    public boolean ownsChunk(Chunk chunk) {
        return chunks.stream().anyMatch(e -> e.getChunkX() == chunk.getX() && e.getChunkZ() == chunk.getZ());
    }

    public boolean ownsChunkWithName(String name) {
        return chunks.stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    public Collection<AbstractLand> getChunks() {
        return chunks;
    }

    public Collection<RentedLand> getRentedLandChunks() {
        return chunks.stream()
                .filter(landChunk -> landChunk instanceof RentedLand)
                .map(landChunk -> (RentedLand) landChunk)
                .collect(Collectors.toList());
    }

    public Collection<OwnedLand> getBoughtLandChunks() {
        return chunks.stream()
                .filter(landChunk -> landChunk instanceof OwnedLand)
                .map(landChunk -> (OwnedLand) landChunk)
                .collect(Collectors.toList());
    }
}
