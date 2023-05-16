package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.EntityParrot;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Parrot.Variant;

public class CraftParrot extends CraftTameableAnimal implements Parrot {
   public CraftParrot(CraftServer server, EntityParrot parrot) {
      super(server, parrot);
   }

   public EntityParrot getHandle() {
      return (EntityParrot)this.entity;
   }

   public Variant getVariant() {
      return Variant.values()[this.getHandle().ga().ordinal()];
   }

   public void setVariant(Variant variant) {
      Preconditions.checkArgument(variant != null, "variant");
      this.getHandle().a(EntityParrot.Variant.a(variant.ordinal()));
   }

   @Override
   public String toString() {
      return "CraftParrot";
   }

   @Override
   public EntityType getType() {
      return EntityType.PARROT;
   }

   public boolean isDancing() {
      return this.getHandle().fZ();
   }
}
