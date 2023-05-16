package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public class DataConverterChunkLightRemove extends DataFix {
   public DataConverterChunkLightRemove(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = var0.findFieldType("Level");
      OpticFinder<?> var2 = DSL.fieldFinder("Level", var1);
      return this.fixTypeEverywhereTyped(
         "ChunkLightRemoveFix",
         var0,
         this.getOutputSchema().getType(DataConverterTypes.c),
         var1x -> var1x.updateTyped(var2, var0xx -> var0xx.update(DSL.remainderFinder(), var0xxx -> var0xxx.remove("isLightOn")))
      );
   }
}
