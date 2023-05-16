package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerLoom;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockLoom extends BlockFacingHorizontal {
   private static final IChatBaseComponent a = IChatBaseComponent.c("container.loom");

   protected BlockLoom(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         var3.a(var0.b(var1, var2));
         var3.a(StatisticList.ax);
         return EnumInteractionResult.b;
      }
   }

   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      return new TileInventory((var2x, var3x, var4) -> new ContainerLoom(var2x, var3x, ContainerAccess.a(var1, var2)), a);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(aD, var0.g().g());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(aD);
   }
}
