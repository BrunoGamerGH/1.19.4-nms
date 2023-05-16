package net.minecraft.world.level.block;

import com.google.common.collect.UnmodifiableIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockTall extends Block implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockSprawling.a;
   public static final BlockStateBoolean b = BlockSprawling.b;
   public static final BlockStateBoolean c = BlockSprawling.c;
   public static final BlockStateBoolean d = BlockSprawling.d;
   public static final BlockStateBoolean e = BlockProperties.C;
   protected static final Map<EnumDirection, BlockStateBoolean> f = BlockSprawling.g
      .entrySet()
      .stream()
      .filter(var0 -> var0.getKey().o().d())
      .collect(SystemUtils.a());
   protected final VoxelShape[] g;
   protected final VoxelShape[] h;
   private final Object2IntMap<IBlockData> i = new Object2IntOpenHashMap();

   protected BlockTall(float var0, float var1, float var2, float var3, float var4, BlockBase.Info var5) {
      super(var5);
      this.g = this.a(var0, var1, var4, 0.0F, var4);
      this.h = this.a(var0, var1, var2, 0.0F, var3);
      UnmodifiableIterator var7 = this.D.a().iterator();

      while(var7.hasNext()) {
         IBlockData var7x = (IBlockData)var7.next();
         this.g(var7x);
      }
   }

   protected VoxelShape[] a(float var0, float var1, float var2, float var3, float var4) {
      float var5 = 8.0F - var0;
      float var6 = 8.0F + var0;
      float var7 = 8.0F - var1;
      float var8 = 8.0F + var1;
      VoxelShape var9 = Block.a((double)var5, 0.0, (double)var5, (double)var6, (double)var2, (double)var6);
      VoxelShape var10 = Block.a((double)var7, (double)var3, 0.0, (double)var8, (double)var4, (double)var8);
      VoxelShape var11 = Block.a((double)var7, (double)var3, (double)var7, (double)var8, (double)var4, 16.0);
      VoxelShape var12 = Block.a(0.0, (double)var3, (double)var7, (double)var8, (double)var4, (double)var8);
      VoxelShape var13 = Block.a((double)var7, (double)var3, (double)var7, 16.0, (double)var4, (double)var8);
      VoxelShape var14 = VoxelShapes.a(var10, var13);
      VoxelShape var15 = VoxelShapes.a(var11, var12);
      VoxelShape[] var16 = new VoxelShape[]{
         VoxelShapes.a(),
         var11,
         var12,
         var15,
         var10,
         VoxelShapes.a(var11, var10),
         VoxelShapes.a(var12, var10),
         VoxelShapes.a(var15, var10),
         var13,
         VoxelShapes.a(var11, var13),
         VoxelShapes.a(var12, var13),
         VoxelShapes.a(var15, var13),
         var14,
         VoxelShapes.a(var11, var14),
         VoxelShapes.a(var12, var14),
         VoxelShapes.a(var15, var14)
      };

      for(int var17 = 0; var17 < 16; ++var17) {
         var16[var17] = VoxelShapes.a(var9, var16[var17]);
      }

      return var16;
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return !var0.c(e);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.h[this.g(var0)];
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.g[this.g(var0)];
   }

   private static int a(EnumDirection var0) {
      return 1 << var0.e();
   }

   protected int g(IBlockData var0) {
      return this.i.computeIntIfAbsent(var0, var0x -> {
         int var1x = 0;
         if (var0x.c(a)) {
            var1x |= a(EnumDirection.c);
         }

         if (var0x.c(b)) {
            var1x |= a(EnumDirection.f);
         }

         if (var0x.c(c)) {
            var1x |= a(EnumDirection.d);
         }

         if (var0x.c(d)) {
            var1x |= a(EnumDirection.e);
         }

         return var1x;
      });
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(e) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      switch(var1) {
         case c:
            return var0.a(a, var0.c(c)).a(b, var0.c(d)).a(c, var0.c(a)).a(d, var0.c(b));
         case d:
            return var0.a(a, var0.c(b)).a(b, var0.c(c)).a(c, var0.c(d)).a(d, var0.c(a));
         case b:
            return var0.a(a, var0.c(d)).a(b, var0.c(a)).a(c, var0.c(b)).a(d, var0.c(c));
         default:
            return var0;
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      switch(var1) {
         case b:
            return var0.a(a, var0.c(c)).a(c, var0.c(a));
         case c:
            return var0.a(b, var0.c(d)).a(d, var0.c(b));
         default:
            return super.a(var0, var1);
      }
   }
}
