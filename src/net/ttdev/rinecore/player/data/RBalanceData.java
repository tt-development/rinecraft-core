package net.ttdev.rinecore.player.data;

import net.ttdev.rinecore.file.Serializer;
import net.ttdev.rinecore.util.FileDirectories;

import java.util.UUID;

public final class RBalanceData extends RPlayerData implements IBalanceData {

    private int balance;

    public RBalanceData(UUID uuid) {
        super(uuid);
        balance = Serializer.loadBalance(uuid, FileDirectories.BALANCES);
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public int setBalance(int amount) {
        Serializer.saveBalance(uuid, balance = amount, FileDirectories.BALANCES);
        return balance;
    }

    @Override
    public int changeBalance(int amount) {
        Serializer.saveBalance(uuid, balance += amount, FileDirectories.BALANCES);
        return balance;
    }
}
