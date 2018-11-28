package net.ttdev.rinecore.economy;

import java.util.UUID;

public class RentedLandChunk extends LandChunk {

    private int timeLeft;

    public RentedLandChunk(UUID owner, String name, int chunkX, int chunkZ, int timeLeft) {
        super(owner, name, chunkX, chunkZ);
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
