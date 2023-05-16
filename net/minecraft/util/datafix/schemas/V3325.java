package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class V3325 extends DataConverterSchemaNamed {
   public V3325(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var0.register(var1, "minecraft:item_display", var1x -> DSL.optionalFields("item", DataConverterTypes.m.in(var0)));
      var0.register(var1, "minecraft:block_display", var1x -> DSL.optionalFields("block_state", DataConverterTypes.n.in(var0)));
      var0.registerSimple(var1, "minecraft:text_display");
      return var1;
   }
}
