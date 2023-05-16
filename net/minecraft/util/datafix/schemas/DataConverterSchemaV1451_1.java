package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1451_1 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1451_1(int var0, Schema var1) {
      super(var0, var1);
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(
         false,
         DataConverterTypes.c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(DataConverterTypes.p.in(var0)),
                  "TileEntities",
                  DSL.list(DSL.or(DataConverterTypes.l.in(var0), DSL.remainder())),
                  "TileTicks",
                  DSL.list(DSL.fields("i", DataConverterTypes.r.in(var0))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(DataConverterTypes.n.in(var0))))
               )
            )
      );
   }
}
