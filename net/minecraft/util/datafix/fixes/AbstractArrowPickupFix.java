package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public class AbstractArrowPickupFix extends DataFix {
   public AbstractArrowPickupFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      return this.fixTypeEverywhereTyped("AbstractArrowPickupFix", var0.getType(DataConverterTypes.q), this::a);
   }

   private Typed<?> a(Typed<?> var0) {
      var0 = this.a(var0, "minecraft:arrow", AbstractArrowPickupFix::a);
      var0 = this.a(var0, "minecraft:spectral_arrow", AbstractArrowPickupFix::a);
      return this.a(var0, "minecraft:trident", AbstractArrowPickupFix::a);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      if (var0.get("pickup").result().isPresent()) {
         return var0;
      } else {
         boolean var1 = var0.get("player").asBoolean(true);
         return var0.set("pickup", var0.createByte((byte)(var1 ? 1 : 0))).remove("player");
      }
   }

   private Typed<?> a(Typed<?> var0, String var1, Function<Dynamic<?>, Dynamic<?>> var2) {
      Type<?> var3 = this.getInputSchema().getChoiceType(DataConverterTypes.q, var1);
      Type<?> var4 = this.getOutputSchema().getChoiceType(DataConverterTypes.q, var1);
      return var0.updateTyped(DSL.namedChoice(var1, var3), var4, var1x -> var1x.update(DSL.remainderFinder(), var2));
   }
}
