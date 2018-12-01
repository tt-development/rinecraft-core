package net.ttdev.rinecore.player;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentedLand;
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
    public void addChunk(AbstractLand chunk) {
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
    public Collection<AbstractLand> getChunks() {
        return chunkData.getChunks();
    }

    @Override
    public Collection<RentedLand> getRentedLandChunks() {
        return chunkData.getRentedLandChunks();
    }

    @Override
    public Collection<OwnedLand> getBoughtLandChunks() {
        return chunkData.getBoughtLandChunks();
    }
}
