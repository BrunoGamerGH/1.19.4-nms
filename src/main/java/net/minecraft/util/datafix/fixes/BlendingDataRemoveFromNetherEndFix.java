package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;

public class BlendingDataRemoveFromNetherEndFix extends DataFix {
   public BlendingDataRemoveFromNetherEndFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.c);
      return this.fixTypeEverywhereTyped(
         "BlendingDataRemoveFromNetherEndFix", var0, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> a(var0xx, var0xx.get("__context")))
      );
   }

   private static Dynamic<?> a(Dynamic<?> var0, OptionalDynamic<?> var1) {
      boolean var2 = "minecraft:overworld".equals(var1.get("dimension").asString().result().orElse(""));
      return var2 ? var0 : var0.remove("blending_data");
   }
}
