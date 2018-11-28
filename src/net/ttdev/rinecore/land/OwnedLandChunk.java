package net.ttdev.rinecore.land;

import java.util.UUID;

public final class OwnedLandChunk extends LandChunk {

    public OwnedLandChunk(UUID owner, String name, int chunkX, int chunkZ) {
        super(owner, name, chunkX, chunkZ);
    }
}