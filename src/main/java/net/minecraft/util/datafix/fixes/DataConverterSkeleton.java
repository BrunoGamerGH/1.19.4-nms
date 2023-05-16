package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterSkeleton extends DataConverterEntityNameAbstract {
   public DataConverterSkeleton(Schema var0, boolean var1) {
      super("EntitySkeletonSplitFix", var0, var1);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var0, Dynamic<?> var1) {
      if (Objects.equals(var0, "Skeleton")) {
         int var2 = var1.get("SkeletonType").asInt(0);
         if (var2 == 1) {
            var0 = "WitherSkeleton";
         } else if (var2 == 2) {
            var0 = "Stray";
         }
      }

      return Pair.of(var0, var1);
   }
}
