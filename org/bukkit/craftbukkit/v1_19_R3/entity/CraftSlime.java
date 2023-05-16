package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntitySlime;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftMob implements Slime, CraftEnemy {
   public CraftSlime(CraftServer server, EntitySlime entity) {
      super(server, entity);
   }

   public int getSize() {
      return this.getHandle().fU();
   }

   public void setSize(int size) {
      this.getHandle().a(size, true);
   }

   public EntitySlime getHandle() {
      return (EntitySlime)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSlime";
   }

   @Override
   public EntityType getType() {
      return EntityType.SLIME;
   }
}
