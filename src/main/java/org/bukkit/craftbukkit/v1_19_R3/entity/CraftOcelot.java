package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityOcelot;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;

public class CraftOcelot extends CraftAnimals implements Ocelot {
   public CraftOcelot(CraftServer server, EntityOcelot ocelot) {
      super(server, ocelot);
   }

   public EntityOcelot getHandle() {
      return (EntityOcelot)this.entity;
   }

   public boolean isTrusting() {
      return this.getHandle().w();
   }

   public void setTrusting(boolean trust) {
      this.getHandle().w(trust);
   }

   public Type getCatType() {
      return Type.WILD_OCELOT;
   }

   public void setCatType(Type type) {
      throw new UnsupportedOperationException("Cats are now a different entity!");
   }

   @Override
   public String toString() {
      return "CraftOcelot";
   }

   @Override
   public EntityType getType() {
      return EntityType.OCELOT;
   }
}
