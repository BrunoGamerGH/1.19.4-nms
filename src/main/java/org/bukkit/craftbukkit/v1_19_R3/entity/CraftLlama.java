package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryLlama;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Llama.Color;
import org.bukkit.inventory.LlamaInventory;

public class CraftLlama extends CraftChestedHorse implements Llama {
   public CraftLlama(CraftServer server, EntityLlama entity) {
      super(server, entity);
   }

   public EntityLlama getHandle() {
      return (EntityLlama)super.getHandle();
   }

   public Color getColor() {
      return Color.values()[this.getHandle().ge().ordinal()];
   }

   public void setColor(Color color) {
      Preconditions.checkArgument(color != null, "color");
      this.getHandle().a(EntityLlama.Variant.a(color.ordinal()));
   }

   public LlamaInventory getInventory() {
      return new CraftInventoryLlama(this.getHandle().cn);
   }

   public int getStrength() {
      return this.getHandle().gc();
   }

   public void setStrength(int strength) {
      Preconditions.checkArgument(1 <= strength && strength <= 5, "strength must be [1,5]");
      if (strength != this.getStrength()) {
         this.getHandle().setStrengthPublic(strength);
         this.getHandle().go();
      }
   }

   public Variant getVariant() {
      return Variant.LLAMA;
   }

   @Override
   public String toString() {
      return "CraftLlama";
   }

   @Override
   public EntityType getType() {
      return EntityType.LLAMA;
   }
}
