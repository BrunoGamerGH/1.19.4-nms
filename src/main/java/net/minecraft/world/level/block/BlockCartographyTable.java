package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerCartography;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockCartographyTable extends Block {
   private static final IChatBaseComponent a = IChatBaseComponent.c("container.cartography_table");

   protected BlockCartographyTable(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         var3.a(var0.b(var1, var2));
         var3.a(StatisticList.aw);
         return EnumInteractionResult.b;
      }
   }

   @Nullable
   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      return new TileInventory((var2x, var3x, var4) -> new ContainerCartography(var2x, var3x, ContainerAccess.a(var1, var2)), a);
   }
}
