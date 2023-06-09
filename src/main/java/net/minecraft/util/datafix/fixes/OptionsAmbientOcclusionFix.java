package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class OptionsAmbientOcclusionFix extends DataFix {
   public OptionsAmbientOcclusionFix(Schema var0) {
      super(var0, false);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsAmbientOcclusionFix",
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> (Dynamic)DataFixUtils.orElse(var0x.get("ao").asString().map(var1 -> var0x.set("ao", var0x.createString(a(var1)))).result(), var0x)
            )
      );
   }

   private static String a(String var0) {
      return switch(var0) {
         case "0" -> "false";
         case "1", "2" -> "true";
         default -> var0;
      };
   }
}
