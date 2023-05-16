package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class DataConverterSchemaV701 extends Schema {
   public DataConverterSchemaV701(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DataConverterSchemaV100.a(var0));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      a(var0, var1, "WitherSkeleton");
      a(var0, var1, "Stray");
      return var1;
   }
}
