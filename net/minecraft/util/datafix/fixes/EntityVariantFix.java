package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;
import java.util.function.IntFunction;

public class EntityVariantFix extends DataConverterNamedEntity {
   private final String a;
   private final IntFunction<String> b;

   public EntityVariantFix(Schema var0, String var1, TypeReference var2, String var3, String var4, IntFunction<String> var5) {
      super(var0, false, var1, var2, var3);
      this.a = var4;
      this.b = var5;
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0, String var1, String var2, Function<Dynamic<T>, Dynamic<T>> var3) {
      return var0.map(var4 -> {
         DynamicOps<T> var5 = var0.getOps();
         Function<T, T> var6 = var2xx -> (T)((Dynamic)var3.apply((T)(new Dynamic(var5, var2xx)))).getValue();
         return var5.get(var4, var1).map(var4x -> var5.set(var4, var2, var6.apply((T)var4x))).result().orElse(var4);
      });
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(
         DSL.remainderFinder(),
         var0x -> a(
               var0x,
               this.a,
               "variant",
               var0xx -> (Dynamic)DataFixUtils.orElse(var0xx.asNumber().map(var1x -> var0xx.createString(this.b.apply(var1x.intValue()))).result(), var0xx)
            )
      );
   }
}
