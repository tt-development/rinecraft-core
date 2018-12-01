package net.ttdev.rinecore.chunk;

import java.util.UUID;

/**
 * Representation of a <code>Chunk</code> with a name, and owned by a player.
 * This class is strictly used for containing data and isn't intended
 * for other purposes.
 */
public abstract class AbstractLand {

    private UUID owner;
    private String name;
    private String world;
    private int chunkX, chunkZ;

    /**
     * Create a new <code>AbstractChunk</code> located at <code>chunkX</code>
     * and <code>chunkZ</code>, and owned by <code>owner</code>.
     * @param owner
     * @param name
     * @param chunkX
     * @param chunkZ
     */
    public AbstractLand(UUID owner, String name, int chunkX, int chunkZ) {
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

    public String getWorld() {
        return world;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof AbstractLand) {
            AbstractLand otherChunk = (AbstractLand) obj;
            return otherChunk.owner.equals(owner) && otherChunk.name.equals(name);
        }

        return false;
    }
}
