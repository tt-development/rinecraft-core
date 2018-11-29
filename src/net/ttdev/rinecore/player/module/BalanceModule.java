package net.ttdev.rinecore.player.module;

import java.util.UUID;

public final class BalanceModule extends RPlayerModule implements IBalanceModule {

    private int balance = 0;

    public BalanceModule(UUID uuid) {
        super(uuid);
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public int setBalance(int amount) {
        return balance = amount;
    }

    @Override
    public int changeBalance(int amount) {
        return balance += amount;
    }
}
