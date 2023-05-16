package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class V3083 extends DataConverterSchemaNamed {
   public V3083(int var0, Schema var1) {
      super(var0, var1);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(
         var1,
         var2,
         () -> DSL.optionalFields(
               "ArmorItems",
               DSL.list(DataConverterTypes.m.in(var0)),
               "HandItems",
               DSL.list(DataConverterTypes.m.in(var0)),
               "listener",
               DSL.optionalFields("event", DSL.optionalFields("game_event", DataConverterTypes.t.in(var0)))
            )
      );
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      a(var0, var1, "minecraft:allay");
      return var1;
   }
}
