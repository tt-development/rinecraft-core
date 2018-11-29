package net.ttdev.rinecore.player;

import net.ttdev.rinecore.chunk.AbstractChunk;
import net.ttdev.rinecore.chunk.OwnedChunk;
import net.ttdev.rinecore.chunk.RentedChunk;
import net.ttdev.rinecore.player.module.BalanceModule;
import net.ttdev.rinecore.player.module.ChunkModule;
import net.ttdev.rinecore.player.module.IBalanceModule;
import net.ttdev.rinecore.player.module.IChunkModule;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.UUID;

public final class RPlayer implements IChunkModule, IBalanceModule {

    private final BalanceModule balanceModule;
    private final ChunkModule chunkModule;

    public RPlayer(UUID uuid) {
        balanceModule = new BalanceModule(uuid);
        chunkModule = new ChunkModule(uuid);
    }

    @Override
    public int getBalance() {
        return balanceModule.getBalance();
    }

    @Override
    public int setBalance(int amount) {
        return balanceModule.setBalance(amount);
    }

    @Override
    public int changeBalance(int amount) {
        return balanceModule.changeBalance(amount);
    }

    @Override
    public boolean ownsChunk(Chunk chunk) {
        return chunkModule.ownsChunk(chunk);
    }

    @Override
    public boolean ownsChunkWithName(String name) {
        return chunkModule.ownsChunkWithName(name);
    }

    @Override
    public Collection<AbstractChunk> getChunks() {
        return chunkModule.getChunks();
    }

    @Override
    public Collection<RentedChunk> getRentedLandChunks() {
        return chunkModule.getRentedLandChunks();
    }

    @Override
    public Collection<OwnedChunk> getBoughtLandChunks() {
        return chunkModule.getBoughtLandChunks();
    }
}
