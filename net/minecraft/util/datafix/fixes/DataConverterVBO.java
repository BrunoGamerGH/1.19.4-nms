package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class DataConverterVBO extends DataFix {
   public DataConverterVBO(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsForceVBOFix",
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(DSL.remainderFinder(), var0x -> var0x.set("useVbo", var0x.createString("true")))
      );
   }
}
