package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class DataConverterSchemaV1486 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1486(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var1.put("minecraft:cod", var1.remove("minecraft:cod_mob"));
      var1.put("minecraft:salmon", var1.remove("minecraft:salmon_mob"));
      return var1;
   }
}
