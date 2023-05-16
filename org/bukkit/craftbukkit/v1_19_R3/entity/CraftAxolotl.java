package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Axolotl.Variant;

public class CraftAxolotl extends CraftAnimals implements Axolotl {
   public CraftAxolotl(CraftServer server, net.minecraft.world.entity.animal.axolotl.Axolotl entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.axolotl.Axolotl getHandle() {
      return (net.minecraft.world.entity.animal.axolotl.Axolotl)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.AXOLOTL;
   }

   @Override
   public String toString() {
      return "CraftAxolotl";
   }

   public boolean isPlayingDead() {
      return this.getHandle().fY();
   }

   public void setPlayingDead(boolean playingDead) {
      this.getHandle().x(playingDead);
   }

   public Variant getVariant() {
      return Variant.values()[this.getHandle().fS().ordinal()];
   }

   public void setVariant(Variant variant) {
      Preconditions.checkArgument(variant != null, "variant");
      this.getHandle().a(net.minecraft.world.entity.animal.axolotl.Axolotl.Variant.a(variant.ordinal()));
   }
}
