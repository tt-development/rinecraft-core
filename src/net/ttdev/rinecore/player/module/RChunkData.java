package net.ttdev.rinecore.player.module;

import net.ttdev.rinecore.chunk.AbstractChunk;
import net.ttdev.rinecore.chunk.OwnedChunk;
import net.ttdev.rinecore.chunk.RentedChunk;
import net.ttdev.rinecore.file.Serializer;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class RChunkData extends RPlayerModule implements IChunkData {

    private final List<AbstractChunk> chunks;

    public RChunkData(UUID uuid) {
        super(uuid);

        chunks = Serializer.loadChunks(uuid, FileDirectories.CHUNKS);
        System.out.println("Loaded " + chunks.size() + " chunks for " + uuid + ".");
    }

    @Override
    public void addChunk(AbstractChunk chunk) {
        Serializer.saveChunk(FileDirectories.CHUNKS, chunk);
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
