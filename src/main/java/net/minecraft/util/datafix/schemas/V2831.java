package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class V2831 extends DataConverterSchemaNamed {
   public V2831(int var0, Schema var1) {
      super(var0, var1);
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(
         true,
         DataConverterTypes.u,
         () -> DSL.optionalFields(
               "SpawnPotentials",
               DSL.list(DSL.fields("data", DSL.fields("entity", DataConverterTypes.p.in(var0)))),
               "SpawnData",
               DSL.fields("entity", DataConverterTypes.p.in(var0))
            )
      );
   }
}
