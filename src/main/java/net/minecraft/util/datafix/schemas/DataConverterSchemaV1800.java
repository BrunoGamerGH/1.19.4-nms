package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1800 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1800(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var0.register(var1, "minecraft:panda", () -> DataConverterSchemaV100.a(var0));
      var0.register(
         var1, "minecraft:pillager", var1x -> DSL.optionalFields("Inventory", DSL.list(DataConverterTypes.m.in(var0)), DataConverterSchemaV100.a(var0))
      );
      return var1;
   }
}
