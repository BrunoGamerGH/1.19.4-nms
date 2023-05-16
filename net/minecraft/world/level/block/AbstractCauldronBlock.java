package net.minecraft.world.level.block;

import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public abstract class AbstractCauldronBlock extends Block {
   private static final int c = 2;
   private static final int d = 4;
   private static final int e = 3;
   private static final int f = 2;
   protected static final int a = 4;
   private static final VoxelShape g = a(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
   protected static final VoxelShape b = VoxelShapes.a(
      VoxelShapes.b(),
      VoxelShapes.a(a(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), a(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), a(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), g),
      OperatorBoolean.e
   );
   private final Map<Item, CauldronInteraction> h;

   public AbstractCauldronBlock(BlockBase.Info var0, Map<Item, CauldronInteraction> var1) {
      super(var0);
      this.h = var1;
   }

   protected double a(IBlockData var0) {
      return 0.0;
   }

   protected boolean a(IBlockData var0, BlockPosition var1, Entity var2) {
      return var2.dn() < (double)var1.v() + this.a(var0) && var2.cD().e > (double)var1.v() + 0.25;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      ItemStack var6 = var3.b(var4);
      CauldronInteraction var7 = this.h.get(var6.c());
      return var7.interact(var0, var1, var2, var3, var4, var6);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return g;
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   public abstract boolean c(IBlockData var1);

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      BlockPosition var4 = PointedDripstoneBlock.a((World)var1, var2);
      if (var4 != null) {
         FluidType var5 = PointedDripstoneBlock.a(var1, var4);
         if (var5 != FluidTypes.a && this.a(var5)) {
            this.a(var0, var1, var2, var5);
         }
      }
   }

   protected boolean a(FluidType var0) {
      return false;
   }

   protected void a(IBlockData var0, World var1, BlockPosition var2, FluidType var3) {
   }
}
