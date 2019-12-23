package net.Indyuce.mmoitems.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.Ability;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.api.player.PlayerStats.CachedStats;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.mmogroup.mmolib.api.DamageType;

public class Sky_Smash extends Ability {
	public Sky_Smash() {
		super(CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK);

		addModifier("cooldown", 10);
		addModifier("damage", 3);
		addModifier("knock-up", 1);
		addModifier("mana", 0);
		addModifier("stamina", 0);
	}

	@Override
	public void whenCast(CachedStats stats, LivingEntity target, AbilityData data, ItemAttackResult result) {
		double damage = data.getModifier("damage");
		double knockUp = data.getModifier("knock-up");

		stats.getPlayer().getWorld().playSound(stats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 2, .5f);
		stats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 254));
		Location loc = stats.getPlayer().getEyeLocation().add(stats.getPlayer().getEyeLocation().getDirection().multiply(3));
		loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 0);
		loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 16, 0, 0, 0, .1);

		for (Entity entity : MMOUtils.getNearbyChunkEntities(loc))
			if (MMOUtils.canDamage(stats.getPlayer(), entity) && entity.getLocation().distanceSquared(loc) < 10) {
				new ItemAttackResult(damage, DamageType.SKILL, DamageType.PHYSICAL).applyEffectsAndDamage(stats, null, (LivingEntity) entity);
				Location loc1 = stats.getPlayer().getEyeLocation().clone();
				loc1.setPitch(-70);
				((LivingEntity) entity).setVelocity(loc1.getDirection().multiply(1.2 * knockUp));
			}
	}
}
