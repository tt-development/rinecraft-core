package net.ttdev.rinecore.chunk;

import java.util.UUID;

public final class OwnedChunk extends AbstractChunk {

    public OwnedChunk(UUID owner, String name, int chunkX, int chunkZ) {
        super(owner, name, chunkX, chunkZ);
    }
}