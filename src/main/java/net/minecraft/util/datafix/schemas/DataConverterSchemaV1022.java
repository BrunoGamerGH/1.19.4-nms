package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1022 extends Schema {
   public DataConverterSchemaV1022(int var0, Schema var1) {
      super(var0, var1);
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(false, DataConverterTypes.y, () -> DSL.constType(DataConverterSchemaNamed.a()));
      var0.registerType(
         false,
         DataConverterTypes.b,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", DataConverterTypes.p.in(var0)),
               "Inventory",
               DSL.list(DataConverterTypes.m.in(var0)),
               "EnderItems",
               DSL.list(DataConverterTypes.m.in(var0)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  DataConverterTypes.p.in(var0),
                  "ShoulderEntityRight",
                  DataConverterTypes.p.in(var0),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(DataConverterTypes.y.in(var0)), "toBeDisplayed", DSL.list(DataConverterTypes.y.in(var0)))
               )
            )
      );
      var0.registerType(false, DataConverterTypes.d, () -> DSL.compoundList(DSL.list(DataConverterTypes.m.in(var0))));
   }
}
