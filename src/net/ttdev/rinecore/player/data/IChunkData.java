package net.ttdev.rinecore.player.data;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentedLand;
import org.bukkit.Chunk;

import java.util.Collection;

public interface IChunkData {

    void addChunk(AbstractLand chunk);

    boolean ownsChunk(Chunk chunk);

    boolean ownsChunkWithName(String name);

    Collection<AbstractLand> getChunks();

    Collection<RentedLand> getRentedLandChunks();

    Collection<OwnedLand> getBoughtLandChunks();
}
