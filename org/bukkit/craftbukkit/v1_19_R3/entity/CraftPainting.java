package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftArt;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftHanging implements Painting {
   public CraftPainting(CraftServer server, EntityPainting entity) {
      super(server, entity);
   }

   public Art getArt() {
      Holder<PaintingVariant> art = this.getHandle().i();
      return CraftArt.NotchToBukkit(art);
   }

   public boolean setArt(Art art) {
      return this.setArt(art, false);
   }

   public boolean setArt(Art art, boolean force) {
      EntityPainting painting = this.getHandle();
      Holder<PaintingVariant> oldArt = painting.i();
      painting.a(CraftArt.BukkitToNotch(art));
      painting.a(painting.cA());
      if (!force && !this.getHandle().generation && !painting.s()) {
         painting.a(oldArt);
         painting.a(painting.cA());
         return false;
      } else {
         this.update();
         return true;
      }
   }

   @Override
   public boolean setFacingDirection(BlockFace face, boolean force) {
      if (super.setFacingDirection(face, force)) {
         this.update();
         return true;
      } else {
         return false;
      }
   }

   public EntityPainting getHandle() {
      return (EntityPainting)this.entity;
   }

   @Override
   public String toString() {
      return "CraftPainting{art=" + this.getArt() + "}";
   }

   @Override
   public EntityType getType() {
      return EntityType.PAINTING;
   }
}
