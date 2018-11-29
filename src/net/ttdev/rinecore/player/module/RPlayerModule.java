package net.ttdev.rinecore.player.module;

import java.util.UUID;

public abstract class RPlayerModule {

    protected final UUID uuid;

    RPlayerModule(UUID uuid) {
        this.uuid = uuid;
    }
}
