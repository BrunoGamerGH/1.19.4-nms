package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;

public class VariantRenameFix extends DataConverterNamedEntity {
   private final Map<String, String> a;

   public VariantRenameFix(Schema var0, String var1, TypeReference var2, String var3, Map<String, String> var4) {
      super(var0, false, var1, var2, var3);
      this.a = var4;
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(
         DSL.remainderFinder(),
         var0x -> var0x.update(
               "variant",
               var0xx -> (Dynamic)DataFixUtils.orElse(var0xx.asString().map(var1x -> var0xx.createString(this.a.getOrDefault(var1x, var1x))).result(), var0xx)
            )
      );
   }
}
