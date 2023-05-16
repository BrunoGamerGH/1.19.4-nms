package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V3328 extends DataConverterSchemaNamed {
   public V3328(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var0.registerSimple(var1, "minecraft:interaction");
      return var1;
   }
}
