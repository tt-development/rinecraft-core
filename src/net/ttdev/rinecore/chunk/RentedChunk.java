package net.ttdev.rinecore.chunk;

import java.util.UUID;

/**
 * A chunk that can be bought temporarily. Every rented chunk
 * must have a player associated with that who is the temporary
 * owner of the chunk.
 */
public final class RentedChunk extends AbstractChunk implements Comparable<RentedChunk> {

    private int timeLeft;

    public RentedChunk(UUID owner, String name, int chunkX, int chunkZ, int timeLeft) {
        super(owner, name, chunkX, chunkZ);
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void adjustTime(int amount) {
        timeLeft += amount;
    }

    @Override
    public int compareTo(RentedChunk landChunk) {
        return Integer.compare(landChunk.timeLeft, timeLeft);
    }
}
