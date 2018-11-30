package net.ttdev.rinecore.chunk;

import java.util.UUID;

/**
 * A chunk that can be bought temporarily. Every rented chunk
 * must have a player associated with that who is the temporary
 * owner of the chunk.
 */
public final class RentedChunk extends AbstractChunk implements Comparable<RentedChunk> {

    private int duration;

    public RentedChunk(UUID owner, String name, int chunkX, int chunkZ, RentTime rentTime) {
        super(owner, name, chunkX, chunkZ);
        this.duration = rentTime.getSeconds();
    }

    public RentedChunk(UUID owner, String name, int chunkX, int chunkZ, int duration) {
        super(owner, name, chunkX, chunkZ);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void changeDuration(int amount) {
        duration += amount;
    }

    @Override
    public int compareTo(RentedChunk landChunk) {
        return Integer.compare(landChunk.duration, duration);
    }
}
