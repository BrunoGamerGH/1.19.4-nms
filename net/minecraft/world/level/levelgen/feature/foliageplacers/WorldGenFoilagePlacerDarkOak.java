package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public class WorldGenFoilagePlacerDarkOak extends WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacerDarkOak> a = RecordCodecBuilder.create(var0 -> b(var0).apply(var0, WorldGenFoilagePlacerDarkOak::new));

   public WorldGenFoilagePlacerDarkOak(IntProvider var0, IntProvider var1) {
      super(var0, var1);
   }

   @Override
   protected WorldGenFoilagePlacers<?> a() {
      return WorldGenFoilagePlacers.i;
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
      BlockPosition var9 = var5.a().b(var8);
      boolean var10 = var5.c();
      if (var10) {
         this.a(var0, var1, var2, var3, var9, var7 + 2, -1, var10);
         this.a(var0, var1, var2, var3, var9, var7 + 3, 0, var10);
         this.a(var0, var1, var2, var3, var9, var7 + 2, 1, var10);
         if (var2.h()) {
            this.a(var0, var1, var2, var3, var9, var7, 2, var10);
         }
      } else {
         this.a(var0, var1, var2, var3, var9, var7 + 2, -1, var10);
         this.a(var0, var1, var2, var3, var9, var7 + 1, 0, var10);
      }
   }

   @Override
   public int a(RandomSource var0, int var1, WorldGenFeatureTreeConfiguration var2) {
      return 4;
   }

   @Override
   protected boolean b(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      return var2 != 0 || !var5 || var1 != -var4 && var1 < var4 || var3 != -var4 && var3 < var4 ? super.b(var0, var1, var2, var3, var4, var5) : true;
   }

   @Override
   protected boolean a(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var2 == -1 && !var5) {
         return var1 == var4 && var3 == var4;
      } else if (var2 == 1) {
         return var1 + var3 > var4 * 2 - 2;
      } else {
         return false;
      }
   }
}
