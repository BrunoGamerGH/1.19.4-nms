package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerFurnaceFurnace;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityFurnaceFurnace extends TileEntityFurnace {
   public TileEntityFurnaceFurnace(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.a, var0, var1, Recipes.b);
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.furnace");
   }

   @Override
   protected Container a(int var0, PlayerInventory var1) {
      return new ContainerFurnaceFurnace(var0, var1, this, this.n);
   }
}
