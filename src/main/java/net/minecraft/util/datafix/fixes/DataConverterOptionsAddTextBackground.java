package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterOptionsAddTextBackground extends DataFix {
   public DataConverterOptionsAddTextBackground(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsAddTextBackgroundFix",
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> (Dynamic)DataFixUtils.orElse(
                     var0x.get("chatOpacity").asString().map(var1x -> var0x.set("textBackgroundOpacity", var0x.createDouble(this.a(var1x)))).result(), var0x
                  )
            )
      );
   }

   private double a(String var0) {
      try {
         double var1 = 0.9 * Double.parseDouble(var0) + 0.1;
         return var1 / 2.0;
      } catch (NumberFormatException var4) {
         return 0.5;
      }
   }
}
