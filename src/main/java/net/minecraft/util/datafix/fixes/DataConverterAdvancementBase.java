package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public class DataConverterAdvancementBase extends DataFix {
   private final String a;
   private final Function<String, String> b;

   public DataConverterAdvancementBase(Schema var0, boolean var1, String var2, Function<String, String> var3) {
      super(var0, var1);
      this.a = var2;
      this.b = var3;
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         this.a, this.getInputSchema().getType(DataConverterTypes.i), var0 -> var0.update(DSL.remainderFinder(), var0x -> var0x.updateMapValues(var1x -> {
                  String var2 = ((Dynamic)var1x.getFirst()).asString("");
                  return var1x.mapFirst(var2x -> var0x.createString(this.b.apply(var2)));
               }))
      );
   }
}
