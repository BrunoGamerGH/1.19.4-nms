package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.EntityMushroomCow;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.MushroomCow.Variant;

public class CraftMushroomCow extends CraftCow implements MushroomCow {
   public CraftMushroomCow(CraftServer server, EntityMushroomCow entity) {
      super(server, entity);
   }

   public EntityMushroomCow getHandle() {
      return (EntityMushroomCow)this.entity;
   }

   public Variant getVariant() {
      return Variant.values()[this.getHandle().r().ordinal()];
   }

   public void setVariant(Variant variant) {
      Preconditions.checkArgument(variant != null, "variant");
      this.getHandle().a(EntityMushroomCow.Type.values()[variant.ordinal()]);
   }

   @Override
   public String toString() {
      return "CraftMushroomCow";
   }

   @Override
   public EntityType getType() {
      return EntityType.MUSHROOM_COW;
   }
}
