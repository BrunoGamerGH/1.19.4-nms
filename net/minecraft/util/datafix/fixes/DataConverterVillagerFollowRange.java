package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterVillagerFollowRange extends DataConverterNamedEntity {
   private static final double a = 16.0;
   private static final double b = 48.0;

   public DataConverterVillagerFollowRange(Schema var0) {
      super(var0, false, "Villager Follow Range Fix", DataConverterTypes.q, "minecraft:villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), DataConverterVillagerFollowRange::a);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return var0.update(
         "Attributes",
         var1 -> var0.createList(
               var1.asStream()
                  .map(
                     var0xx -> var0xx.get("Name").asString("").equals("generic.follow_range") && var0xx.get("Base").asDouble(0.0) == 16.0
                           ? var0xx.set("Base", var0xx.createDouble(48.0))
                           : var0xx
                  )
            )
      );
   }
}
