package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.apache.commons.lang3.mutable.MutableInt;

public final class LightEngineBlock extends LightEngineLayer<LightEngineStorageBlock.a, LightEngineStorageBlock> {
   private static final EnumDirection[] f = EnumDirection.values();
   private final BlockPosition.MutableBlockPosition g = new BlockPosition.MutableBlockPosition();

   public LightEngineBlock(ILightAccess var0) {
      super(var0, EnumSkyBlock.b, new LightEngineStorageBlock(var0));
   }

   private int d(long var0) {
      int var2 = BlockPosition.a(var0);
      int var3 = BlockPosition.b(var0);
      int var4 = BlockPosition.c(var0);
      IBlockAccess var5 = this.b.c(SectionPosition.a(var2), SectionPosition.a(var4));
      return var5 != null ? var5.h(this.g.d(var2, var3, var4)) : 0;
   }

   @Override
   protected int b(long var0, long var2, int var4) {
      if (var2 == Long.MAX_VALUE) {
         return 15;
      } else if (var0 == Long.MAX_VALUE) {
         return var4 + 15 - this.d(var2);
      } else if (var4 >= 15) {
         return var4;
      } else {
         int var5 = Integer.signum(BlockPosition.a(var2) - BlockPosition.a(var0));
         int var6 = Integer.signum(BlockPosition.b(var2) - BlockPosition.b(var0));
         int var7 = Integer.signum(BlockPosition.c(var2) - BlockPosition.c(var0));
         EnumDirection var8 = EnumDirection.a(var5, var6, var7);
         if (var8 == null) {
            return 15;
         } else {
            MutableInt var9 = new MutableInt();
            IBlockData var10 = this.a(var2, var9);
            if (var9.getValue() >= 15) {
               return 15;
            } else {
               IBlockData var11 = this.a(var0, null);
               VoxelShape var12 = this.a(var11, var0, var8);
               VoxelShape var13 = this.a(var10, var2, var8.g());
               return VoxelShapes.b(var12, var13) ? 15 : var4 + Math.max(1, var9.getValue());
            }
         }
      }
   }

   @Override
   protected void a(long var0, int var2, boolean var3) {
      long var4 = SectionPosition.e(var0);

      for(EnumDirection var9 : f) {
         long var10 = BlockPosition.a(var0, var9);
         long var12 = SectionPosition.e(var10);
         if (var4 == var12 || this.d.g(var12)) {
            this.b(var0, var10, var2, var3);
         }
      }
   }

   @Override
   protected int a(long var0, long var2, int var4) {
      int var5 = var4;
      if (Long.MAX_VALUE != var2) {
         int var6 = this.b(Long.MAX_VALUE, var0, 0);
         if (var4 > var6) {
            var5 = var6;
         }

         if (var5 == 0) {
            return var5;
         }
      }

      long var6 = SectionPosition.e(var0);
      NibbleArray var8 = this.d.a(var6, true);

      for(EnumDirection var12 : f) {
         long var13 = BlockPosition.a(var0, var12);
         if (var13 != var2) {
            long var15 = SectionPosition.e(var13);
            NibbleArray var17;
            if (var6 == var15) {
               var17 = var8;
            } else {
               var17 = this.d.a(var15, true);
            }

            if (var17 != null) {
               int var18 = this.b(var13, var0, this.a(var17, var13));
               if (var5 > var18) {
                  var5 = var18;
               }

               if (var5 == 0) {
                  return var5;
               }
            }
         }
      }

      return var5;
   }

   @Override
   public void a(BlockPosition var0, int var1) {
      this.d.d();
      this.a(Long.MAX_VALUE, var0.a(), 15 - var1, true);
   }
}
