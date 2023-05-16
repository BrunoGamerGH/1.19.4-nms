package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class V3327 extends DataConverterSchemaNamed {
   public V3327(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerBlockEntities(var0);
      var0.register(var1, "minecraft:decorated_pot", () -> DSL.optionalFields("shards", DSL.list(DataConverterTypes.s.in(var0))));
      var0.register(var1, "minecraft:suspicious_sand", () -> DSL.optionalFields("item", DataConverterTypes.m.in(var0)));
      return var1;
   }
}
