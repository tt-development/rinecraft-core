package net.ttdev.rinecore.player;

import net.ttdev.rinecore.chunk.AbstractChunk;
import net.ttdev.rinecore.chunk.OwnedChunk;
import net.ttdev.rinecore.chunk.RentedChunk;
import net.ttdev.rinecore.player.module.IBalanceData;
import net.ttdev.rinecore.player.module.IChunkData;
import net.ttdev.rinecore.player.module.RBalanceData;
import net.ttdev.rinecore.player.module.RChunkData;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.UUID;

public final class RPlayer implements IChunkData, IBalanceData {

    private final UUID uuid;

    private final RBalanceData balanceData;
    private final RChunkData chunkData;

    public RPlayer(UUID uuid) {
        this.uuid = uuid;
        balanceData = new RBalanceData(uuid);
        chunkData = new RChunkData(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getBalance() {
        return balanceData.getBalance();
    }

    @Override
    public int setBalance(int amount) {
        return balanceData.setBalance(amount);
    }

    @Override
    public int changeBalance(int amount) {
        return balanceData.changeBalance(amount);
    }

    @Override
    public void addChunk(AbstractChunk chunk) {
        chunkData.addChunk(chunk);
    }

    @Override
    public boolean ownsChunk(Chunk chunk) {
        return chunkData.ownsChunk(chunk);
    }

    @Override
    public boolean ownsChunkWithName(String name) {
        return chunkData.ownsChunkWithName(name);
    }

    @Override
    public Collection<AbstractChunk> getChunks() {
        return chunkData.getChunks();
    }

    @Override
    public Collection<RentedChunk> getRentedLandChunks() {
        return chunkData.getRentedLandChunks();
    }

    @Override
    public Collection<OwnedChunk> getBoughtLandChunks() {
        return chunkData.getBoughtLandChunks();
    }
}
