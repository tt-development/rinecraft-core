package net.ttdev.rinecore.chunk;

import java.util.UUID;

/**
 * A chunk that can be bought and owned permanently.
 * Every chunk must have a player associated with it who
 * is the <code>owner</code> of the chunk.
 */
public final class OwnedLand extends AbstractLand {

    public OwnedLand(UUID owner, String name, int chunkX, int chunkZ) {
        super(owner, name, chunkX, chunkZ);
    }
}