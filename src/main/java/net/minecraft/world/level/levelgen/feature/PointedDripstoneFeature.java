package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;

public class PointedDripstoneFeature extends WorldGenerator<PointedDripstoneConfiguration> {
   public PointedDripstoneFeature(Codec<PointedDripstoneConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<PointedDripstoneConfiguration> var0) {
      GeneratorAccess var1 = var0.b();
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      PointedDripstoneConfiguration var4 = var0.f();
      Optional<EnumDirection> var5 = a(var1, var2, var3);
      if (var5.isEmpty()) {
         return false;
      } else {
         BlockPosition var6 = var2.a(var5.get().g());
         a(var1, var3, var6, var4);
         int var7 = var3.i() < var4.b && DripstoneUtils.c(var1.a_(var2.a(var5.get()))) ? 2 : 1;
         DripstoneUtils.a(var1, var2, var5.get(), var7, false);
         return true;
      }
   }

   private static Optional<EnumDirection> a(GeneratorAccess var0, BlockPosition var1, RandomSource var2) {
      boolean var3 = DripstoneUtils.b(var0.a_(var1.c()));
      boolean var4 = DripstoneUtils.b(var0.a_(var1.d()));
      if (var3 && var4) {
         return Optional.of(var2.h() ? EnumDirection.a : EnumDirection.b);
      } else if (var3) {
         return Optional.of(EnumDirection.a);
      } else {
         return var4 ? Optional.of(EnumDirection.b) : Optional.empty();
      }
   }

   private static void a(GeneratorAccess var0, RandomSource var1, BlockPosition var2, PointedDripstoneConfiguration var3) {
      DripstoneUtils.c(var0, var2);

      for(EnumDirection var5 : EnumDirection.EnumDirectionLimit.a) {
         if (!(var1.i() > var3.c)) {
            BlockPosition var6 = var2.a(var5);
            DripstoneUtils.c(var0, var6);
            if (!(var1.i() > var3.d)) {
               BlockPosition var7 = var6.a(EnumDirection.b(var1));
               DripstoneUtils.c(var0, var7);
               if (!(var1.i() > var3.e)) {
                  BlockPosition var8 = var7.a(EnumDirection.b(var1));
                  DripstoneUtils.c(var0, var8);
               }
            }
         }
      }
   }
}
