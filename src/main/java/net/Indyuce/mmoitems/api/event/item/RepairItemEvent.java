package net.Indyuce.mmoitems.api.event.item;

import org.bukkit.event.HandlerList;

import net.Indyuce.mmoitems.api.event.PlayerDataEvent;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.mmogroup.mmolib.api.item.NBTItem;

public class RepairItemEvent extends PlayerDataEvent {
	private static final HandlerList handlers = new HandlerList();

	private final MMOItem consumable;
	private final NBTItem target;

	private int repaired;

	/**
	 * Called when a player repairs an item using a consumable
	 * 
	 * @param playerData
	 *            Player repairing the item
	 * @param consumable
	 *            Consumable used to repair the item
	 * @param target
	 *            Item being repaired
	 * @param repaired
	 *            Amount of durability being repaired
	 */
	public RepairItemEvent(PlayerData playerData, MMOItem consumable, NBTItem target, int repaired) {
		super(playerData);

		this.consumable = consumable;
		this.target = target;
		this.repaired = repaired;
	}

	public MMOItem getConsumable() {
		return consumable;
	}

	public NBTItem getTargetItem() {
		return target;
	}

	public int getRepaired() {
		return repaired;
	}

	public void setRepaired(int repaired) {
		this.repaired = repaired;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}