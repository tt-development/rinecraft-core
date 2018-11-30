package net.ttdev.rinecore.player.module;

public interface IBalanceData {

    int getBalance();

    int setBalance(int amount);

    int changeBalance(int amount);
}
