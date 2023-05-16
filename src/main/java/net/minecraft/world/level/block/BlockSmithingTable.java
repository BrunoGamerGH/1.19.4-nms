package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerSmithing;
import net.minecraft.world.inventory.LegacySmithingMenu;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockSmithingTable extends BlockWorkbench {
   private static final IChatBaseComponent a = IChatBaseComponent.c("container.upgrade");

   protected BlockSmithingTable(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      return new TileInventory(
         (var2x, var3x, var4) -> (Container)(var1.G().b(FeatureFlags.c)
               ? new ContainerSmithing(var2x, var3x, ContainerAccess.a(var1, var2))
               : new LegacySmithingMenu(var2x, var3x, ContainerAccess.a(var1, var2))),
         a
      );
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         var3.a(var0.b(var1, var2));
         var3.a(StatisticList.aF);
         return EnumInteractionResult.b;
      }
   }
}
