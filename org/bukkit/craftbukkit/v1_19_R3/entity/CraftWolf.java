package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.item.EnumColor;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class CraftWolf extends CraftTameableAnimal implements Wolf {
   public CraftWolf(CraftServer server, EntityWolf wolf) {
      super(server, wolf);
   }

   public boolean isAngry() {
      return this.getHandle().R_();
   }

   public void setAngry(boolean angry) {
      if (angry) {
         this.getHandle().c();
      } else {
         this.getHandle().N_();
      }
   }

   public EntityWolf getHandle() {
      return (EntityWolf)this.entity;
   }

   @Override
   public EntityType getType() {
      return EntityType.WOLF;
   }

   public DyeColor getCollarColor() {
      return DyeColor.getByWoolData((byte)this.getHandle().gb().a());
   }

   public void setCollarColor(DyeColor color) {
      this.getHandle().a(EnumColor.a(color.getWoolData()));
   }

   public boolean isWet() {
      return this.getHandle().fZ();
   }

   public float getTailAngle() {
      return this.getHandle().ga();
   }

   public boolean isInterested() {
      return this.getHandle().gc();
   }

   public void setInterested(boolean flag) {
      this.getHandle().A(flag);
   }
}
