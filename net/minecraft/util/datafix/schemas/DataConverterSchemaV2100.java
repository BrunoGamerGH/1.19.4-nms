package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV2100 extends DataConverterSchemaNamed {
   public DataConverterSchemaV2100(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DataConverterSchemaV100.a(var0));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      a(var0, var1, "minecraft:bee");
      a(var0, var1, "minecraft:bee_stinger");
      return var1;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerBlockEntities(var0);
      var0.register(var1, "minecraft:beehive", () -> DSL.optionalFields("Bees", DSL.list(DSL.optionalFields("EntityData", DataConverterTypes.p.in(var0)))));
      return var1;
   }
}
