package net.minecraft.world;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ITileEntityContainer;

public final class TileInventory implements ITileInventory {
   private final IChatBaseComponent a;
   private final ITileEntityContainer b;

   public TileInventory(ITileEntityContainer var0, IChatBaseComponent var1) {
      this.b = var0;
      this.a = var1;
   }

   @Override
   public IChatBaseComponent G_() {
      return this.a;
   }

   @Override
   public Container createMenu(int var0, PlayerInventory var1, EntityHuman var2) {
      return this.b.createMenu(var0, var1, var2);
   }
}
