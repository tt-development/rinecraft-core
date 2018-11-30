package net.ttdev.rinecore.api.sign;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.ttdev.rinecore.chunk.sign.RentSign;

public class RentSignCreatedEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private boolean canceled;
	
	private RentSign rentSign;
	private Player player;
	
	public RentSignCreatedEvent(RentSign rentSign) {
		this.rentSign = rentSign;
	}
	
	public RentSignCreatedEvent(RentSign rentSign, Player player) {
		this.rentSign = rentSign;
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public RentSign getRentSign() {
		return this.rentSign;
	}

	@Override
	public boolean isCancelled() {
		return this.canceled;
	}

	@Override
	public void setCancelled(boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
