package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterStriderGravity extends DataConverterNamedEntity {
   public DataConverterStriderGravity(Schema var0, boolean var1) {
      super(var0, var1, "StriderGravityFix", DataConverterTypes.q, "minecraft:strider");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.get("NoGravity").asBoolean(false) ? var0.set("NoGravity", var0.createBoolean(false)) : var0;
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
