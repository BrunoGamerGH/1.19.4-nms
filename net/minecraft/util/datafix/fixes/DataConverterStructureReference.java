package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class DataConverterStructureReference extends DataFix {
   public DataConverterStructureReference(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.v);
      return this.fixTypeEverywhereTyped("Structure Reference Fix", var0, var0x -> var0x.update(DSL.remainderFinder(), DataConverterStructureReference::a));
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      return var0.update("references", var0x -> var0x.createInt(var0x.asNumber().map(Number::intValue).result().filter(var0xx -> var0xx > 0).orElse(1)));
   }
}
