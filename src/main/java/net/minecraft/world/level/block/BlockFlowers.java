package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockFlowers extends BlockPlant implements SuspiciousEffectHolder {
   protected static final float a = 3.0F;
   protected static final VoxelShape b = Block.a(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
   private final MobEffectList c;
   private final int d;

   public BlockFlowers(MobEffectList var0, int var1, BlockBase.Info var2) {
      super(var2);
      this.c = var0;
      if (var0.a()) {
         this.d = var1;
      } else {
         this.d = var1 * 20;
      }
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      Vec3D var4 = var0.n(var1, var2);
      return b.a(var4.c, var4.d, var4.e);
   }

   @Override
   public MobEffectList b() {
      return this.c;
   }

   @Override
   public int c() {
      return this.d;
   }
}
