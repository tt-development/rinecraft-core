package net.ttdev.rinecore.player.module;

import java.util.UUID;

public abstract class RPlayerData {

    protected final UUID uuid;

    RPlayerData(UUID uuid) {
        this.uuid = uuid;
    }
}
