package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterChunkStatus extends DataFix {
   public DataConverterChunkStatus(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = var0.findFieldType("Level");
      OpticFinder<?> var2 = DSL.fieldFinder("Level", var1);
      return this.fixTypeEverywhereTyped(
         "ChunkStatusFix", var0, this.getOutputSchema().getType(DataConverterTypes.c), var1x -> var1x.updateTyped(var2, var0xx -> {
               Dynamic<?> var1xx = (Dynamic)var0xx.get(DSL.remainderFinder());
               String var2x = var1xx.get("Status").asString("empty");
               if (Objects.equals(var2x, "postprocessed")) {
                  var1xx = var1xx.set("Status", var1xx.createString("fullchunk"));
               }
   
               return var0xx.set(DSL.remainderFinder(), var1xx);
            })
      );
   }
}
