package net.ttdev.rinecore.economy;

import java.util.UUID;

public class OwnedLandChunk extends LandChunk {

    public OwnedLandChunk(UUID owner, String name, int chunkX, int chunkZ) {
        super(owner, name, chunkX, chunkZ);
    }
}