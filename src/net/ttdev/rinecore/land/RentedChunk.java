package net.ttdev.rinecore.land;

import java.util.UUID;

public final class RentedChunk extends AbstractChunk implements Comparable<RentedChunk> {

    private int timeLeft;

    public RentedChunk(UUID owner, String name, int chunkX, int chunkZ, int timeLeft) {
        super(owner, name, chunkX, chunkZ);
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void adjustTimeLeft(int amount) {
        timeLeft += amount;
    }

    @Override
    public int compareTo(RentedChunk landChunk) {
        return Integer.compare(landChunk.timeLeft, timeLeft);
    }
}
