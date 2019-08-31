package net.Indyuce.mmoitems.version.wrapper;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.Indyuce.mmoitems.api.util.MMORayTraceResult;

public interface VersionWrapper {
	default void spawnParticle(Particle particle, Location loc, Color color) {
		spawnParticle(particle, loc, 1, 0, 0, 0, 0, 1, color);
	}

	default void spawnParticle(Particle particle, Location loc, float size, Color color) {
		spawnParticle(particle, loc, 1, 0, 0, 0, 0, size, color);
	}

	void spawnParticle(Particle particle, Location loc, int amount, double x, double y, double z, double speed, float size, Color color);

	void spawnParticle(Particle particle, Location loc, int amount, double x, double y, double z, double speed, Material material);

	String getName(Enchantment enchant);

	FurnaceRecipe getFurnaceRecipe(String path, ItemStack item, Material material, float exp, int cook);

	default MMORayTraceResult rayTrace(Player player, double range) {
		return rayTrace(player, player.getEyeLocation().getDirection(), range);
	}

	MMORayTraceResult rayTrace(Player player, Vector direction, double range);
}