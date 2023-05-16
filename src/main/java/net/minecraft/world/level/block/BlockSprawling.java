package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockSprawling extends Block {
   private static final EnumDirection[] i = EnumDirection.values();
   public static final BlockStateBoolean a = BlockProperties.L;
   public static final BlockStateBoolean b = BlockProperties.M;
   public static final BlockStateBoolean c = BlockProperties.N;
   public static final BlockStateBoolean d = BlockProperties.O;
   public static final BlockStateBoolean e = BlockProperties.J;
   public static final BlockStateBoolean f = BlockProperties.K;
   public static final Map<EnumDirection, BlockStateBoolean> g = ImmutableMap.copyOf(SystemUtils.a(Maps.newEnumMap(EnumDirection.class), var0 -> {
      var0.put(EnumDirection.c, a);
      var0.put(EnumDirection.f, b);
      var0.put(EnumDirection.d, c);
      var0.put(EnumDirection.e, d);
      var0.put(EnumDirection.b, e);
      var0.put(EnumDirection.a, f);
   }));
   protected final VoxelShape[] h;

   protected BlockSprawling(float var0, BlockBase.Info var1) {
      super(var1);
      this.h = this.a(var0);
   }

   private VoxelShape[] a(float var0) {
      float var1 = 0.5F - var0;
      float var2 = 0.5F + var0;
      VoxelShape var3 = Block.a(
         (double)(var1 * 16.0F), (double)(var1 * 16.0F), (double)(var1 * 16.0F), (double)(var2 * 16.0F), (double)(var2 * 16.0F), (double)(var2 * 16.0F)
      );
      VoxelShape[] var4 = new VoxelShape[i.length];

      for(int var5 = 0; var5 < i.length; ++var5) {
         EnumDirection var6 = i[var5];
         var4[var5] = VoxelShapes.a(
            0.5 + Math.min((double)(-var0), (double)var6.j() * 0.5),
            0.5 + Math.min((double)(-var0), (double)var6.k() * 0.5),
            0.5 + Math.min((double)(-var0), (double)var6.l() * 0.5),
            0.5 + Math.max((double)var0, (double)var6.j() * 0.5),
            0.5 + Math.max((double)var0, (double)var6.k() * 0.5),
            0.5 + Math.max((double)var0, (double)var6.l() * 0.5)
         );
      }

      VoxelShape[] var5 = new VoxelShape[64];

      for(int var6 = 0; var6 < 64; ++var6) {
         VoxelShape var7 = var3;

         for(int var8 = 0; var8 < i.length; ++var8) {
            if ((var6 & 1 << var8) != 0) {
               var7 = VoxelShapes.a(var7, var4[var8]);
            }
         }

         var5[var6] = var7;
      }

      return var5;
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return false;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.h[this.h(var0)];
   }

   protected int h(IBlockData var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < i.length; ++var2) {
         if (var0.c(g.get(i[var2]))) {
            var1 |= 1 << var2;
         }
      }

      return var1;
   }
}
