package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class OptionsAccessibilityOnboardFix extends DataFix {
   public OptionsAccessibilityOnboardFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsAccessibilityOnboardFix",
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(DSL.remainderFinder(), var0x -> var0x.set("onboardAccessibility", var0x.createBoolean(false)))
      );
   }
}
