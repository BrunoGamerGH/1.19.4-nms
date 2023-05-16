package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseMule;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Horse.Variant;

public class CraftMule extends CraftChestedHorse implements Mule {
   public CraftMule(CraftServer server, EntityHorseMule entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftMule";
   }

   @Override
   public EntityType getType() {
      return EntityType.MULE;
   }

   public Variant getVariant() {
      return Variant.MULE;
   }
}
