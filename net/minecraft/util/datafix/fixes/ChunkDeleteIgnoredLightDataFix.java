package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class ChunkDeleteIgnoredLightDataFix extends DataFix {
   public ChunkDeleteIgnoredLightDataFix(Schema var0) {
      super(var0, true);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      OpticFinder<?> var1 = var0.findField("sections");
      return this.fixTypeEverywhereTyped(
         "ChunkDeleteIgnoredLightDataFix",
         var0,
         var1x -> {
            boolean var2x = ((Dynamic)var1x.get(DSL.remainderFinder())).get("isLightOn").asBoolean(false);
            return !var2x
               ? var1x.updateTyped(var1, var0xx -> var0xx.update(DSL.remainderFinder(), var0xxx -> var0xxx.remove("BlockLight").remove("SkyLight")))
               : var1x;
         }
      );
   }
}
