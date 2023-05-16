package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.SystemUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.WorldGenFeaturePillagerOutpostPoolPiece;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructureJigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public class Beardifier implements DensityFunctions.c {
   public static final int a = 12;
   private static final int f = 24;
   private static final float[] g = SystemUtils.a(new float[13824], var0 -> {
      for(int var1 = 0; var1 < 24; ++var1) {
         for(int var2 = 0; var2 < 24; ++var2) {
            for(int var3 = 0; var3 < 24; ++var3) {
               var0[var1 * 24 * 24 + var2 * 24 + var3] = (float)b(var2 - 12, var3 - 12, var1 - 12);
            }
         }
      }
   });
   private final ObjectListIterator<Beardifier.a> h;
   private final ObjectListIterator<WorldGenFeatureDefinedStructureJigsawJunction> i;

   public static Beardifier a(StructureManager var0, ChunkCoordIntPair var1) {
      int var2 = var1.d();
      int var3 = var1.e();
      ObjectList<Beardifier.a> var4 = new ObjectArrayList(10);
      ObjectList<WorldGenFeatureDefinedStructureJigsawJunction> var5 = new ObjectArrayList(32);
      var0.a(var1, var0x -> var0x.d() != TerrainAdjustment.a).forEach(var5x -> {
         TerrainAdjustment var6 = var5x.h().d();

         for(StructurePiece var8 : var5x.i()) {
            if (var8.a(var1, 12)) {
               if (var8 instanceof WorldGenFeaturePillagerOutpostPoolPiece var9) {
                  WorldGenFeatureDefinedStructurePoolTemplate.Matching var10 = var9.b().e();
                  if (var10 == WorldGenFeatureDefinedStructurePoolTemplate.Matching.b) {
                     var4.add(new Beardifier.a(var9.f(), var6, var9.d()));
                  }

                  for(WorldGenFeatureDefinedStructureJigsawJunction var12 : var9.e()) {
                     int var13 = var12.a();
                     int var14 = var12.c();
                     if (var13 > var2 - 12 && var14 > var3 - 12 && var13 < var2 + 15 + 12 && var14 < var3 + 15 + 12) {
                        var5.add(var12);
                     }
                  }
               } else {
                  var4.add(new Beardifier.a(var8.f(), var6, 0));
               }
            }
         }
      });
      return new Beardifier(var4.iterator(), var5.iterator());
   }

   @VisibleForTesting
   public Beardifier(ObjectListIterator<Beardifier.a> var0, ObjectListIterator<WorldGenFeatureDefinedStructureJigsawJunction> var1) {
      this.h = var0;
      this.i = var1;
   }

   @Override
   public double a(DensityFunction.b var0) {
      int var1 = var0.a();
      int var2 = var0.b();
      int var3 = var0.c();

      double var4;
      double var10001;
      for(var4 = 0.0; this.h.hasNext(); var4 += var10001) {
         Beardifier.a var6 = (Beardifier.a)this.h.next();
         StructureBoundingBox var7 = var6.a();
         int var8 = var6.c();
         int var9 = Math.max(0, Math.max(var7.g() - var1, var1 - var7.j()));
         int var10 = Math.max(0, Math.max(var7.i() - var3, var3 - var7.l()));
         int var11 = var7.h() + var8;
         int var12 = var2 - var11;

         int var13 = switch(var6.b()) {
            case a -> 0;
            case b, c -> var12;
            case d -> Math.max(0, Math.max(var11 - var2, var2 - var7.k()));
         };
         switch(var6.b()) {
            case a:
               var10001 = 0.0;
               break;
            case b:
               var10001 = a(var9, var13, var10);
               break;
            case c:
            case d:
               var10001 = a(var9, var13, var10, var12) * 0.8;
               break;
            default:
               throw new IncompatibleClassChangeError();
         }
      }

      this.h.back(Integer.MAX_VALUE);

      while(this.i.hasNext()) {
         WorldGenFeatureDefinedStructureJigsawJunction var6 = (WorldGenFeatureDefinedStructureJigsawJunction)this.i.next();
         int var7 = var1 - var6.a();
         int var8 = var2 - var6.b();
         int var9 = var3 - var6.c();
         var4 += a(var7, var8, var9, var8) * 0.4;
      }

      this.i.back(Integer.MAX_VALUE);
      return var4;
   }

   @Override
   public double a() {
      return Double.NEGATIVE_INFINITY;
   }

   @Override
   public double b() {
      return Double.POSITIVE_INFINITY;
   }

   private static double a(int var0, int var1, int var2) {
      double var3 = MathHelper.f((double)var0, (double)var1 / 2.0, (double)var2);
      return MathHelper.a(var3, 0.0, 6.0, 1.0, 0.0);
   }

   private static double a(int var0, int var1, int var2, int var3) {
      int var4 = var0 + 12;
      int var5 = var1 + 12;
      int var6 = var2 + 12;
      if (a(var4) && a(var5) && a(var6)) {
         double var7 = (double)var3 + 0.5;
         double var9 = MathHelper.e((double)var0, var7, (double)var2);
         double var11 = -var7 * MathHelper.g(var9 / 2.0) / 2.0;
         return var11 * (double)g[var6 * 24 * 24 + var4 * 24 + var5];
      } else {
         return 0.0;
      }
   }

   private static boolean a(int var0) {
      return var0 >= 0 && var0 < 24;
   }

   private static double b(int var0, int var1, int var2) {
      return a(var0, (double)var1 + 0.5, var2);
   }

   private static double a(int var0, double var1, int var3) {
      double var4 = MathHelper.e((double)var0, var1, (double)var3);
      return Math.pow(Math.E, -var4 / 16.0);
   }

   @VisibleForTesting
   public static record a(StructureBoundingBox box, TerrainAdjustment terrainAdjustment, int groundLevelDelta) {
      private final StructureBoundingBox a;
      private final TerrainAdjustment b;
      private final int c;

      public a(StructureBoundingBox var0, TerrainAdjustment var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }
}
