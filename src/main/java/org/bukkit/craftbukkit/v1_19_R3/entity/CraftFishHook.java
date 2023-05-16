package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.FishHook.HookState;

public class CraftFishHook extends CraftProjectile implements FishHook {
   private double biteChance = -1.0;

   public CraftFishHook(CraftServer server, EntityFishingHook entity) {
      super(server, entity);
   }

   public EntityFishingHook getHandle() {
      return (EntityFishingHook)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFishingHook";
   }

   public EntityType getType() {
      return EntityType.FISHING_HOOK;
   }

   public int getMinWaitTime() {
      return this.getHandle().minWaitTime;
   }

   public void setMinWaitTime(int minWaitTime) {
      EntityFishingHook hook = this.getHandle();
      Validate.isTrue(minWaitTime >= 0 && minWaitTime <= this.getMaxWaitTime(), "The minimum wait time should be between 0 and the maximum wait time.");
      hook.minWaitTime = minWaitTime;
   }

   public int getMaxWaitTime() {
      return this.getHandle().maxWaitTime;
   }

   public void setMaxWaitTime(int maxWaitTime) {
      EntityFishingHook hook = this.getHandle();
      Validate.isTrue(
         maxWaitTime >= 0 && maxWaitTime >= this.getMinWaitTime(), "The maximum wait time should be higher than or equal to 0 and the minimum wait time."
      );
      hook.maxWaitTime = maxWaitTime;
   }

   public boolean getApplyLure() {
      return this.getHandle().applyLure;
   }

   public void setApplyLure(boolean applyLure) {
      this.getHandle().applyLure = applyLure;
   }

   public double getBiteChance() {
      EntityFishingHook hook = this.getHandle();
      if (this.biteChance == -1.0) {
         return hook.H.t(new BlockPosition(MathHelper.a(hook.dl()), MathHelper.a(hook.dn()) + 1, MathHelper.a(hook.dr()))) ? 0.0033333333333333335 : 0.002;
      } else {
         return this.biteChance;
      }
   }

   public void setBiteChance(double chance) {
      Validate.isTrue(chance >= 0.0 && chance <= 1.0, "The bite chance must be between 0 and 1.");
      this.biteChance = chance;
   }

   public boolean isInOpenWater() {
      return this.getHandle().i();
   }

   public Entity getHookedEntity() {
      net.minecraft.world.entity.Entity hooked = this.getHandle().o;
      return hooked != null ? hooked.getBukkitEntity() : null;
   }

   public void setHookedEntity(Entity entity) {
      EntityFishingHook hook = this.getHandle();
      hook.o = entity != null ? ((CraftEntity)entity).getHandle() : null;
      hook.aj().b(EntityFishingHook.g, hook.o != null ? hook.o.af() + 1 : 0);
   }

   public boolean pullHookedEntity() {
      EntityFishingHook hook = this.getHandle();
      if (hook.o == null) {
         return false;
      } else {
         hook.c(hook.o);
         return true;
      }
   }

   public HookState getState() {
      return HookState.values()[this.getHandle().p.ordinal()];
   }
}
