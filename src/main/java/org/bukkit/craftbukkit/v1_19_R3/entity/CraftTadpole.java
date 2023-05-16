package org.bukkit.craftbukkit.v1_19_R3.entity;

import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tadpole;

public class CraftTadpole extends CraftFish implements Tadpole {
   public CraftTadpole(CraftServer server, net.minecraft.world.entity.animal.frog.Tadpole entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.frog.Tadpole getHandle() {
      return (net.minecraft.world.entity.animal.frog.Tadpole)this.entity;
   }

   @Override
   public String toString() {
      return "CraftTadpole";
   }

   @Override
   public EntityType getType() {
      return EntityType.TADPOLE;
   }

   public int getAge() {
      return this.getHandle().bT;
   }

   public void setAge(int age) {
      this.getHandle().bT = age;
   }
}
