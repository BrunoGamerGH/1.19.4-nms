package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1470 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1470(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(var1, var2, () -> DataConverterSchemaV100.a(var0));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      a(var0, var1, "minecraft:turtle");
      a(var0, var1, "minecraft:cod_mob");
      a(var0, var1, "minecraft:tropical_fish");
      a(var0, var1, "minecraft:salmon_mob");
      a(var0, var1, "minecraft:puffer_fish");
      a(var0, var1, "minecraft:phantom");
      a(var0, var1, "minecraft:dolphin");
      a(var0, var1, "minecraft:drowned");
      var0.register(var1, "minecraft:trident", var1x -> DSL.optionalFields("inBlockState", DataConverterTypes.n.in(var0)));
      return var1;
   }
}
