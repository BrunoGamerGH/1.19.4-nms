package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterCatType extends DataConverterNamedEntity {
   public DataConverterCatType(Schema var0, boolean var1) {
      super(var0, var1, "CatTypeFix", DataConverterTypes.q, "minecraft:cat");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.get("CatType").asInt(0) == 9 ? var0.set("CatType", var0.createInt(10)) : var0;
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
