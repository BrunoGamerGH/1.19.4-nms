package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;

public class DataConverterChunkStatus2 extends DataFix {
   private static final Map<String, String> a = ImmutableMap.builder()
      .put("structure_references", "empty")
      .put("biomes", "empty")
      .put("base", "surface")
      .put("carved", "carvers")
      .put("liquid_carved", "liquid_carvers")
      .put("decorated", "features")
      .put("lighted", "light")
      .put("mobs_spawned", "spawn")
      .put("finalized", "heightmaps")
      .put("fullchunk", "full")
      .build();

   public DataConverterChunkStatus2(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = var0.findFieldType("Level");
      OpticFinder<?> var2 = DSL.fieldFinder("Level", var1);
      return this.fixTypeEverywhereTyped(
         "ChunkStatusFix2", var0, this.getOutputSchema().getType(DataConverterTypes.c), var1x -> var1x.updateTyped(var2, var0xx -> {
               Dynamic<?> var1xx = (Dynamic)var0xx.get(DSL.remainderFinder());
               String var2x = var1xx.get("Status").asString("empty");
               String var3x = a.getOrDefault(var2x, "empty");
               return Objects.equals(var2x, var3x) ? var0xx : var0xx.set(DSL.remainderFinder(), var1xx.set("Status", var1xx.createString(var3x)));
            })
      );
   }
}
