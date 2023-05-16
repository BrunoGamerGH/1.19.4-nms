package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.animal.horse.HorseColor;
import net.minecraft.world.entity.animal.horse.HorseStyle;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.HorseInventory;

public class CraftHorse extends CraftAbstractHorse implements Horse {
   public CraftHorse(CraftServer server, EntityHorse entity) {
      super(server, entity);
   }

   public EntityHorse getHandle() {
      return (EntityHorse)super.getHandle();
   }

   public Variant getVariant() {
      return Variant.HORSE;
   }

   public Color getColor() {
      return Color.values()[this.getHandle().r().a()];
   }

   public void setColor(Color color) {
      Validate.notNull(color, "Color cannot be null");
      this.getHandle().a(HorseColor.a(color.ordinal()), this.getHandle().fS());
   }

   public Style getStyle() {
      return Style.values()[this.getHandle().fS().a()];
   }

   public void setStyle(Style style) {
      Validate.notNull(style, "Style cannot be null");
      this.getHandle().a(this.getHandle().r(), HorseStyle.a(style.ordinal()));
   }

   public boolean isCarryingChest() {
      return false;
   }

   public void setCarryingChest(boolean chest) {
      throw new UnsupportedOperationException("Not supported.");
   }

   public HorseInventory getInventory() {
      return new CraftInventoryHorse(this.getHandle().cn);
   }

   @Override
   public String toString() {
      return "CraftHorse{variant=" + this.getVariant() + ", owner=" + this.getOwner() + 125;
   }

   @Override
   public EntityType getType() {
      return EntityType.HORSE;
   }
}
