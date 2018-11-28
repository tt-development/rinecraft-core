package net.ttdev.rinecore.economy;

import java.util.UUID;

/**
 * Representation of a <code>Chunk</code> with a name, and owned by a player.
 * This class is strictly used for containing data and isn't intended
 * for other purposes.
 */
public abstract class LandChunk {

    private UUID owner;
    private String name;
    private int chunkX, chunkZ;

    /**
     * Create a new <code>LandChunk</code> located at <code>chunkX</code>
     * and <code>chunkZ</code>, and owned by <code>owner</code>.
     * @param owner
     * @param name
     * @param chunkX
     * @param chunkZ
     */
    public LandChunk(UUID owner, String name, int chunkX, int chunkZ) {
        this.owner = owner;
        this.name = name;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
