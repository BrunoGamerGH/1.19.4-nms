package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityExperienceOrb;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {
   public CraftExperienceOrb(CraftServer server, EntityExperienceOrb entity) {
      super(server, entity);
   }

   public int getExperience() {
      return this.getHandle().i;
   }

   public void setExperience(int value) {
      this.getHandle().i = value;
   }

   public EntityExperienceOrb getHandle() {
      return (EntityExperienceOrb)this.entity;
   }

   @Override
   public String toString() {
      return "CraftExperienceOrb";
   }

   public EntityType getType() {
      return EntityType.EXPERIENCE_ORB;
   }
}
