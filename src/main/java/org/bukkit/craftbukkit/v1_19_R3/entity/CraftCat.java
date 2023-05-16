package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.item.EnumColor;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Cat.Type;

public class CraftCat extends CraftTameableAnimal implements Cat {
   public CraftCat(CraftServer server, EntityCat entity) {
      super(server, entity);
   }

   public EntityCat getHandle() {
      return (EntityCat)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.CAT;
   }

   @Override
   public String toString() {
      return "CraftCat";
   }

   public Type getCatType() {
      return Type.values()[BuiltInRegistries.ai.a(this.getHandle().fZ())];
   }

   public void setCatType(Type type) {
      Preconditions.checkArgument(type != null, "Cannot have null Type");
      this.getHandle().a(BuiltInRegistries.ai.a(type.ordinal()));
   }

   public DyeColor getCollarColor() {
      return DyeColor.getByWoolData((byte)this.getHandle().gc().a());
   }

   public void setCollarColor(DyeColor color) {
      this.getHandle().a(EnumColor.a(color.getWoolData()));
   }
}
