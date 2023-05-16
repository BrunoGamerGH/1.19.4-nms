package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class CherryFoliagePlacer extends WorldGenFoilagePlacer {
   public static final Codec<CherryFoliagePlacer> a = RecordCodecBuilder.create(
      var0 -> b(var0)
            .and(
               var0.group(
                  IntProvider.b(4, 16).fieldOf("height").forGetter(var0x -> var0x.b),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("wide_bottom_layer_hole_chance").forGetter(var0x -> var0x.c),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("corner_hole_chance").forGetter(var0x -> var0x.c),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter(var0x -> var0x.h),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter(var0x -> var0x.i)
               )
            )
            .apply(var0, CherryFoliagePlacer::new)
   );
   private final IntProvider b;
   private final float c;
   private final float g;
   private final float h;
   private final float i;

   public CherryFoliagePlacer(IntProvider var0, IntProvider var1, IntProvider var2, float var3, float var4, float var5, float var6) {
      super(var0, var1);
      this.b = var2;
      this.c = var3;
      this.g = var4;
      this.h = var5;
      this.i = var6;
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.k;
   }

   @Override
   protected void a(
      VirtualLevelReadable var0,
      WorldGenFoilagePlacer.b var1,
      RandomSource var2,
      WorldGenFeatureTreeConfiguration var3,
      int var4,
      WorldGenFoilagePlacer.a var5,
      int var6,
      int var7,
      int var8
   ) {
      boolean var9 = var5.c();
      BlockPosition var10 = var5.a().b(var8);
      int var11 = var7 + var5.b() - 1;
      this.a(var0, var1, var2, var3, var10, var11 - 2, var6 - 3, var9);
      this.a(var0, var1, var2, var3, var10, var11 - 1, var6 - 4, var9);

      for(int var12 = var6 - 5; var12 >= 0; --var12) {
         this.a(var0, var1, var2, var3, var10, var11, var12, var9);
      }

      this.a(var0, var1, var2, var3, var10, var11, -1, var9, this.h, this.i);
      this.a(var0, var1, var2, var3, var10, var11 - 1, -2, var9, this.h, this.i);
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return this.b.a(var0);
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var2 == -1 && (var1 == var4 || var3 == var4) && var0.i() < this.c) {
         return true;
      } else {
         boolean var6 = var1 == var4 && var3 == var4;
         boolean var7 = var4 > 2;
         if (var7) {
            return var6 || var1 + var3 > var4 * 2 - 2 && var0.i() < this.g;
         } else {
            return var6 && var0.i() < this.g;
         }
      }
   }
}
