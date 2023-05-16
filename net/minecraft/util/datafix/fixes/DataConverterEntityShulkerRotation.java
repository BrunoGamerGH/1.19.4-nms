package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;

public class DataConverterEntityShulkerRotation extends DataConverterNamedEntity {
   public DataConverterEntityShulkerRotation(Schema var0) {
      super(var0, false, "EntityShulkerRotationFix", DataConverterTypes.q, "minecraft:shulker");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      List<Double> var1 = var0.get("Rotation").asList(var0x -> var0x.asDouble(180.0));
      if (!var1.isEmpty()) {
         var1.set(0, var1.get(0) - 180.0);
         return var0.set("Rotation", var0.createList(var1.stream().map(var0::createDouble)));
      } else {
         return var0;
      }
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
