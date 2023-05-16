package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.level.block.entity.TileEntityChest;

class BlockChest$2$1 implements ITileInventory {
   BlockChest$2$1(Object var0, TileEntityChest var2, TileEntityChest var3, IInventory var4) {
      this.d = var0;
      this.a = var2;
      this.b = var3;
      this.c = var4;
   }

   @Nullable
   @Override
   public Container createMenu(int var0, PlayerInventory var1, EntityHuman var2) {
      if (this.a.d(var2) && this.b.d(var2)) {
         this.a.e(var1.m);
         this.b.e(var1.m);
         return ContainerChest.b(var0, var1, this.c);
      } else {
         return null;
      }
   }

   @Override
   public IChatBaseComponent G_() {
      if (this.a.aa()) {
         return this.a.G_();
      } else {
         return (IChatBaseComponent)(this.b.aa() ? this.b.G_() : IChatBaseComponent.c("container.chestDouble"));
      }
   }
}
