package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityPhantom;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;

public class CraftPhantom extends CraftFlying implements Phantom, CraftEnemy {
   public CraftPhantom(CraftServer server, EntityPhantom entity) {
      super(server, entity);
   }

   public EntityPhantom getHandle() {
      return (EntityPhantom)super.getHandle();
   }

   public int getSize() {
      return this.getHandle().q();
   }

   public void setSize(int sz) {
      this.getHandle().b(sz);
   }

   @Override
   public String toString() {
      return "CraftPhantom";
   }

   @Override
   public EntityType getType() {
      return EntityType.PHANTOM;
   }
}
