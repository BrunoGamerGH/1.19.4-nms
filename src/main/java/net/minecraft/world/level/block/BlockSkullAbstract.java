package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;

public abstract class BlockSkullAbstract extends BlockTileEntity implements Equipable {
   private final BlockSkull.a a;

   public BlockSkullAbstract(BlockSkull.a var0, BlockBase.Info var1) {
      super(var1);
      this.a = var0;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntitySkull(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      if (var0.B) {
         boolean var3 = var1.a(Blocks.gN) || var1.a(Blocks.gO) || var1.a(Blocks.gP) || var1.a(Blocks.gQ);
         if (var3) {
            return a(var2, TileEntityTypes.p, TileEntitySkull::a);
         }
      }

      return null;
   }

   public BlockSkull.a b() {
      return this.a;
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   @Override
   public EnumItemSlot g() {
      return EnumItemSlot.f;
   }
}
