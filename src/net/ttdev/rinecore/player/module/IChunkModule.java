package net.ttdev.rinecore.player.module;

import net.ttdev.rinecore.chunk.AbstractChunk;
import net.ttdev.rinecore.chunk.OwnedChunk;
import net.ttdev.rinecore.chunk.RentedChunk;
import org.bukkit.Chunk;

import java.util.Collection;

public interface IChunkModule {

    boolean ownsChunk(Chunk chunk);

    boolean ownsChunkWithName(String name);

    Collection<AbstractChunk> getChunks();

    Collection<RentedChunk> getRentedLandChunks();

    Collection<OwnedChunk> getBoughtLandChunks();
}
