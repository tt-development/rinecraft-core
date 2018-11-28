package net.ttdev.rinecore.land;

import java.util.UUID;

public final class RentedLandChunk extends LandChunk implements Comparable<RentedLandChunk> {

    private int timeLeft;

    public RentedLandChunk(UUID owner, String name, int chunkX, int chunkZ, int timeLeft) {
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
    public boolean equals(Object obj) {
        if (!(obj instanceof RentedLandChunk)) {
            return false;
        }

        RentedLandChunk rentedLandChunk = (RentedLandChunk) obj;
        return rentedLandChunk.getOwner().equals(super.getOwner()) &&
                rentedLandChunk.getName().equals(super.getName());
    }

    @Override
    public int compareTo(RentedLandChunk landChunk) {
        return Integer.compare(landChunk.timeLeft, timeLeft);
    }
}
