package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;

public class DataConverterMapId extends DataFix {
   public DataConverterMapId(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.h);
      OpticFinder<?> var1 = var0.findField("data");
      return this.fixTypeEverywhereTyped(
         "Map id fix",
         var0,
         var1x -> {
            Optional<? extends Typed<?>> var2x = var1x.getOptionalTyped(var1);
            return var2x.isPresent()
               ? var1x
               : var1x.update(DSL.remainderFinder(), var0xx -> var0xx.createMap(ImmutableMap.of(var0xx.createString("data"), var0xx)));
         }
      );
   }
}
