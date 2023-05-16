package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.EntityInsentient;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftSound;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.loot.LootTable;

public abstract class CraftMob extends CraftLivingEntity implements Mob {
   public CraftMob(CraftServer server, EntityInsentient entity) {
      super(server, entity);
   }

   public void setTarget(LivingEntity target) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot set target during world generation");
      EntityInsentient entity = this.getHandle();
      if (target == null) {
         entity.setTarget(null, null, false);
      } else if (target instanceof CraftLivingEntity) {
         entity.setTarget(((CraftLivingEntity)target).getHandle(), null, false);
      }
   }

   public CraftLivingEntity getTarget() {
      return this.getHandle().P_() == null ? null : (CraftLivingEntity)this.getHandle().P_().getBukkitEntity();
   }

   public void setAware(boolean aware) {
      this.getHandle().aware = aware;
   }

   public boolean isAware() {
      return this.getHandle().aware;
   }

   public Sound getAmbientSound() {
      SoundEffect sound = this.getHandle().getAmbientSound0();
      return sound != null ? CraftSound.getBukkit(sound) : null;
   }

   public EntityInsentient getHandle() {
      return (EntityInsentient)this.entity;
   }

   @Override
   public String toString() {
      return "CraftMob";
   }

   public void setLootTable(LootTable table) {
      this.getHandle().ca = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());
   }

   public LootTable getLootTable() {
      NamespacedKey key = CraftNamespacedKey.fromMinecraft(this.getHandle().et());
      return Bukkit.getLootTable(key);
   }

   public void setSeed(long seed) {
      this.getHandle().cb = seed;
   }

   public long getSeed() {
      return this.getHandle().cb;
   }
}
