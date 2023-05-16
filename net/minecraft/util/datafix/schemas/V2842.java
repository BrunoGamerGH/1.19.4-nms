package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class V2842 extends DataConverterSchemaNamed {
   public V2842(int var0, Schema var1) {
      super(var0, var1);
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(
         false,
         DataConverterTypes.c,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DataConverterTypes.p.in(var0)),
               "block_entities",
               DSL.list(DSL.or(DataConverterTypes.l.in(var0), DSL.remainder())),
               "block_ticks",
               DSL.list(DSL.fields("i", DataConverterTypes.r.in(var0))),
               "sections",
               DSL.list(
                  DSL.optionalFields(
                     "biomes",
                     DSL.optionalFields("palette", DSL.list(DataConverterTypes.z.in(var0))),
                     "block_states",
                     DSL.optionalFields("palette", DSL.list(DataConverterTypes.n.in(var0)))
                  )
               ),
               "structures",
               DSL.optionalFields("starts", DSL.compoundList(DataConverterTypes.v.in(var0)))
            )
      );
   }
}
