package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.stream.Collectors;

public class DataConverterKeybind2 extends DataFix {
   public DataConverterKeybind2(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsKeyTranslationFix",
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(DSL.remainderFinder(), var0x -> (Dynamic)var0x.getMapValues().map(var1 -> var0x.createMap(var1.entrySet().stream().map(var1x -> {
                     if (((Dynamic)var1x.getKey()).asString("").startsWith("key_")) {
                        String var2 = ((Dynamic)var1x.getValue()).asString("");
                        if (!var2.startsWith("key.mouse") && !var2.startsWith("scancode.")) {
                           return Pair.of((Dynamic)var1x.getKey(), var0x.createString("key.keyboard." + var2.substring("key.".length())));
                        }
                     }
   
                     return Pair.of((Dynamic)var1x.getKey(), (Dynamic)var1x.getValue());
                  }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))).result().orElse(var0x))
      );
   }
}
