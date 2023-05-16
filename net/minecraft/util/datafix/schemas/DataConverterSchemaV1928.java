package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1928 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1928(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static TypeTemplate a(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(DataConverterTypes.m.in(var0)), "HandItems", DSL.list(DataConverterTypes.m.in(var0)));
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> a(var0));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var1.remove("minecraft:illager_beast");
      a(var0, var1, "minecraft:ravager");
      return var1;
   }
}
