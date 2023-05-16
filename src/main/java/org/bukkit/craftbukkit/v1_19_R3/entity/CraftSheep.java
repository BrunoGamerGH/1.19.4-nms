package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.item.EnumColor;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep {
   public CraftSheep(CraftServer server, EntitySheep entity) {
      super(server, entity);
   }

   public DyeColor getColor() {
      return DyeColor.getByWoolData((byte)this.getHandle().r().a());
   }

   public void setColor(DyeColor color) {
      this.getHandle().b(EnumColor.a(color.getWoolData()));
   }

   public boolean isSheared() {
      return this.getHandle().w();
   }

   public void setSheared(boolean flag) {
      this.getHandle().w(flag);
   }

   public EntitySheep getHandle() {
      return (EntitySheep)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSheep";
   }

   @Override
   public EntityType getType() {
      return EntityType.SHEEP;
   }
}
