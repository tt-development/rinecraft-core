package net.ttdev.rinecore.chunk;

import java.util.UUID;

/**
 * A chunk that can be bought temporarily. Every rented chunk
 * must have a player associated with that who is the temporary
 * owner of the chunk.
 */
public final class RentedLand extends AbstractLand implements Comparable<RentedLand> {

    private int duration;

    public RentedLand(UUID owner, String name, int chunkX, int chunkZ, RentTime rentTime) {
        super(owner, name, chunkX, chunkZ);
        this.duration = rentTime.getSeconds();
    }

    public RentedLand(UUID owner, String name, int chunkX, int chunkZ, int duration) {
        super(owner, name, chunkX, chunkZ);
        this.duration = duration;
    }

    public boolean hasExpired() {
        return duration <= 0;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void changeDuration(int duration) {
        this.duration += duration;
    }

    @Override
    public int compareTo(RentedLand landChunk) {
        return Integer.compare(landChunk.duration, duration);
    }
}
