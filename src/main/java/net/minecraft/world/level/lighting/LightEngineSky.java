package net.minecraft.world.level.lighting;

import java.util.Locale;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.apache.commons.lang3.mutable.MutableInt;

public final class LightEngineSky extends LightEngineLayer<LightEngineStorageSky.a, LightEngineStorageSky> {
   private static final EnumDirection[] f = EnumDirection.values();
   private static final EnumDirection[] g = new EnumDirection[]{EnumDirection.c, EnumDirection.d, EnumDirection.e, EnumDirection.f};

   public LightEngineSky(ILightAccess var0) {
      super(var0, EnumSkyBlock.a, new LightEngineStorageSky(var0));
   }

   @Override
   protected int b(long var0, long var2, int var4) {
      if (var2 == Long.MAX_VALUE || var0 == Long.MAX_VALUE) {
         return 15;
      } else if (var4 >= 15) {
         return var4;
      } else {
         MutableInt var5 = new MutableInt();
         IBlockData var6 = this.a(var2, var5);
         if (var5.getValue() >= 15) {
            return 15;
         } else {
            int var7 = BlockPosition.a(var0);
            int var8 = BlockPosition.b(var0);
            int var9 = BlockPosition.c(var0);
            int var10 = BlockPosition.a(var2);
            int var11 = BlockPosition.b(var2);
            int var12 = BlockPosition.c(var2);
            int var13 = Integer.signum(var10 - var7);
            int var14 = Integer.signum(var11 - var8);
            int var15 = Integer.signum(var12 - var9);
            EnumDirection var16 = EnumDirection.a(var13, var14, var15);
            if (var16 == null) {
               throw new IllegalStateException(String.format(Locale.ROOT, "Light was spread in illegal direction %d, %d, %d", var13, var14, var15));
            } else {
               IBlockData var17 = this.a(var0, null);
               VoxelShape var18 = this.a(var17, var0, var16);
               VoxelShape var19 = this.a(var6, var2, var16.g());
               if (VoxelShapes.b(var18, var19)) {
                  return 15;
               } else {
                  boolean var20 = var7 == var10 && var9 == var12;
                  boolean var21 = var20 && var8 > var11;
                  return var21 && var4 == 0 && var5.getValue() == 0 ? 0 : var4 + Math.max(1, var5.getValue());
               }
            }
         }
      }
   }

   @Override
   protected void a(long var0, int var2, boolean var3) {
      long var4 = SectionPosition.e(var0);
      int var6 = BlockPosition.b(var0);
      int var7 = SectionPosition.b(var6);
      int var8 = SectionPosition.a(var6);
      int var9;
      if (var7 != 0) {
         var9 = 0;
      } else {
         int var10 = 0;

         while(!this.d.g(SectionPosition.a(var4, 0, -var10 - 1, 0)) && this.d.a(var8 - var10 - 1)) {
            ++var10;
         }

         var9 = var10;
      }

      long var10 = BlockPosition.a(var0, 0, -1 - var9 * 16, 0);
      long var12 = SectionPosition.e(var10);
      if (var4 == var12 || this.d.g(var12)) {
         this.b(var0, var10, var2, var3);
      }

      long var14 = BlockPosition.a(var0, EnumDirection.b);
      long var16 = SectionPosition.e(var14);
      if (var4 == var16 || this.d.g(var16)) {
         this.b(var0, var14, var2, var3);
      }

      for(EnumDirection var21 : g) {
         int var22 = 0;

         do {
            long var23 = BlockPosition.a(var0, var21.j(), -var22, var21.l());
            long var25 = SectionPosition.e(var23);
            if (var4 == var25) {
               this.b(var0, var23, var2, var3);
               break;
            }

            if (this.d.g(var25)) {
               long var27 = BlockPosition.a(var0, 0, -var22, 0);
               this.b(var27, var23, var2, var3);
            }
         } while(++var22 > var9 * 16);
      }
   }

   @Override
   protected int a(long var0, long var2, int var4) {
      int var5 = var4;
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

            int var18;
            if (var17 != null) {
               var18 = this.a(var17, var13);
            } else {
               if (var12 == EnumDirection.a) {
                  continue;
               }

               var18 = 15 - this.d.e(var13, true);
            }

            int var19 = this.b(var13, var0, var18);
            if (var5 > var19) {
               var5 = var19;
            }

            if (var5 == 0) {
               return var5;
            }
         }
      }

      return var5;
   }

   @Override
   protected void f(long var0) {
      this.d.d();
      long var2 = SectionPosition.e(var0);
      if (this.d.g(var2)) {
         super.f(var0);
      } else {
         for(var0 = BlockPosition.e(var0); !this.d.g(var2) && !this.d.m(var2); var0 = BlockPosition.a(var0, 0, 16, 0)) {
            var2 = SectionPosition.a(var2, EnumDirection.b);
         }

         if (this.d.g(var2)) {
            super.f(var0);
         }
      }
   }

   @Override
   public String b(long var0) {
      return super.b(var0) + (this.d.m(var0) ? "*" : "");
   }
}
