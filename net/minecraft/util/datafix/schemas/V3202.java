package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V3202 extends DataConverterSchemaNamed {
   public V3202(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerBlockEntities(var0);
      var0.registerSimple(var1, "minecraft:hanging_sign");
      return var1;
   }
}
