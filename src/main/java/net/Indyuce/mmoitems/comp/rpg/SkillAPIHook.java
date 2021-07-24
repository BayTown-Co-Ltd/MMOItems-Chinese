package net.Indyuce.mmoitems.comp.rpg;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.event.PlayerLevelUpEvent;
import com.sucy.skill.api.event.SkillDamageEvent;
import com.sucy.skill.api.player.PlayerData;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageHandler;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.api.RegisteredAttack;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class SkillAPIHook implements RPGHandler, Listener, DamageHandler {
    private final Map<Integer, RegisteredAttack> damageInfo = new HashMap<>();

    public SkillAPIHook() {
        MythicLib.plugin.getDamage().registerHandler(this);
    }

    @Override
    public RPGPlayer getInfo(net.Indyuce.mmoitems.api.player.PlayerData data) {
        return new SkillAPIPlayer(data);
    }

    @Override
    public RegisteredAttack getDamage(Entity entity) {
        return damageInfo.get(entity.getEntityId());
    }

    @Override
    public boolean hasDamage(Entity entity) {
        return damageInfo.containsKey(entity.getEntityId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void a(SkillDamageEvent event) {
        damageInfo.put(event.getTarget().getEntityId(), new RegisteredAttack(new AttackResult(event.getDamage(), DamageType.SKILL), event.getDamager()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void c(EntityDamageByEntityEvent event) {
        damageInfo.remove(event.getEntity().getEntityId());
    }

    @EventHandler
    public void b(PlayerLevelUpEvent event) {
        net.Indyuce.mmoitems.api.player.PlayerData.get(event.getPlayerData().getPlayer()).getInventory().scheduleUpdate();
    }

    @Override
    public void refreshStats(net.Indyuce.mmoitems.api.player.PlayerData data) {
    }

    public static class SkillAPIPlayer extends RPGPlayer {
        /*private final PlayerData rpgdata;*/

        public SkillAPIPlayer(net.Indyuce.mmoitems.api.player.PlayerData playerData) {
            super(playerData);

            /*rpgdata = SkillAPI.getPlayerData(playerData.getPlayer());*/
        }

        @Override
        public int getLevel() {
            PlayerData rpgdata = SkillAPI.getPlayerData(getPlayer());
            return rpgdata.hasClass() ? rpgdata.getMainClass().getLevel() : 0;
        }

        @Override
        public String getClassName() {
            PlayerData rpgdata = SkillAPI.getPlayerData(getPlayer());
            return rpgdata.hasClass() ? rpgdata.getMainClass().getData().getName() : "";
        }

        @Override
        public double getMana() {
            PlayerData rpgdata = SkillAPI.getPlayerData(getPlayer());
            return rpgdata.hasClass() ? rpgdata.getMana() : 0;
        }

        @Override
        public double getStamina() {
            return getPlayer().getFoodLevel();
        }

        @Override
        public void setMana(double value) {
            PlayerData rpgdata = SkillAPI.getPlayerData(getPlayer());
            if (rpgdata.hasClass())
                rpgdata.setMana(value);
        }

        @Override
        public void setStamina(double value) {
            getPlayer().setFoodLevel((int) value);
        }
    }
}