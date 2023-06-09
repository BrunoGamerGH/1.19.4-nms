package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityIllagerAbstract;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Illager;

public class CraftIllager extends CraftRaider implements Illager {
   public CraftIllager(CraftServer server, EntityIllagerAbstract entity) {
      super(server, entity);
   }

   public EntityIllagerAbstract getHandle() {
      return (EntityIllagerAbstract)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftIllager";
   }
}
