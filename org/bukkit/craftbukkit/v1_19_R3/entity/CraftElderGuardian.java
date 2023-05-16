package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityGuardianElder;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EntityType;

public class CraftElderGuardian extends CraftGuardian implements ElderGuardian {
   public CraftElderGuardian(CraftServer server, EntityGuardianElder entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftElderGuardian";
   }

   @Override
   public EntityType getType() {
      return EntityType.ELDER_GUARDIAN;
   }

   @Override
   public boolean isElder() {
      return true;
   }
}
