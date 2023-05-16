package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.EntityZombieVillager;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class CraftVillagerZombie extends CraftZombie implements ZombieVillager {
   public CraftVillagerZombie(CraftServer server, EntityZombieVillager entity) {
      super(server, entity);
   }

   public EntityZombieVillager getHandle() {
      return (EntityZombieVillager)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftVillagerZombie";
   }

   @Override
   public EntityType getType() {
      return EntityType.ZOMBIE_VILLAGER;
   }

   @Override
   public Profession getVillagerProfession() {
      return Profession.valueOf(BuiltInRegistries.z.b(this.getHandle().gd().b()).a().toUpperCase(Locale.ROOT));
   }

   @Override
   public void setVillagerProfession(Profession profession) {
      Validate.notNull(profession);
      this.getHandle().a(this.getHandle().gd().a(BuiltInRegistries.z.a(new MinecraftKey(profession.name().toLowerCase(Locale.ROOT)))));
   }

   public Type getVillagerType() {
      return Type.valueOf(BuiltInRegistries.y.b(this.getHandle().gd().a()).a().toUpperCase(Locale.ROOT));
   }

   public void setVillagerType(Type type) {
      Validate.notNull(type);
      this.getHandle().a(this.getHandle().gd().a(BuiltInRegistries.y.a(CraftNamespacedKey.toMinecraft(type.getKey()))));
   }

   @Override
   public boolean isConverting() {
      return this.getHandle().gc();
   }

   @Override
   public int getConversionTime() {
      Preconditions.checkState(this.isConverting(), "Entity not converting");
      return this.getHandle().ca;
   }

   @Override
   public void setConversionTime(int time) {
      if (time < 0) {
         this.getHandle().ca = -1;
         this.getHandle().aj().b(EntityZombieVillager.c, false);
         this.getHandle().cb = null;
         this.getHandle().removeEffect(MobEffects.e, Cause.CONVERSION);
      } else {
         this.getHandle().a(null, time);
      }
   }

   public OfflinePlayer getConversionPlayer() {
      return this.getHandle().cb == null ? null : Bukkit.getOfflinePlayer(this.getHandle().cb);
   }

   public void setConversionPlayer(OfflinePlayer conversionPlayer) {
      if (this.isConverting()) {
         this.getHandle().cb = conversionPlayer == null ? null : conversionPlayer.getUniqueId();
      }
   }
}
