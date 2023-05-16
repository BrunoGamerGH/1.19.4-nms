package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseChestedAbstract;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.ChestedHorse;

public abstract class CraftChestedHorse extends CraftAbstractHorse implements ChestedHorse {
   public CraftChestedHorse(CraftServer server, EntityHorseChestedAbstract entity) {
      super(server, entity);
   }

   public EntityHorseChestedAbstract getHandle() {
      return (EntityHorseChestedAbstract)super.getHandle();
   }

   public boolean isCarryingChest() {
      return this.getHandle().r();
   }

   public void setCarryingChest(boolean chest) {
      if (chest != this.isCarryingChest()) {
         this.getHandle().w(chest);
         this.getHandle().go();
      }
   }
}
