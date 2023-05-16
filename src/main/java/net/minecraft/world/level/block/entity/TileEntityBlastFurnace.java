package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerBlastFurnace;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityBlastFurnace extends TileEntityFurnace {
   public TileEntityBlastFurnace(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.C, var0, var1, Recipes.c);
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.blast_furnace");
   }

   @Override
   protected int a(ItemStack var0) {
      return super.a(var0) / 2;
   }

   @Override
   protected Container a(int var0, PlayerInventory var1) {
      return new ContainerBlastFurnace(var0, var1, this, this.n);
   }
}
