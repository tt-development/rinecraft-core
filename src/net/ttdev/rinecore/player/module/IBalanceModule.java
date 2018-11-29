package net.ttdev.rinecore.player.module;

public interface IBalanceModule {

    int getBalance();

    int setBalance(int amount);

    int changeBalance(int amount);
}
