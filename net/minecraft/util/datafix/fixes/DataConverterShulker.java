package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterShulker extends DataConverterNamedEntity {
   public DataConverterShulker(Schema var0, boolean var1) {
      super(var0, var1, "EntityShulkerColorFix", DataConverterTypes.q, "minecraft:shulker");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return !var0.get("Color").map(Dynamic::asNumber).result().isPresent() ? var0.set("Color", var0.createByte((byte)10)) : var0;
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
