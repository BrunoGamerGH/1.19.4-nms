package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;

public class DataConverterObjectiveRenderType extends DataFix {
   public DataConverterObjectiveRenderType(Schema var0, boolean var1) {
      super(var0, var1);
   }

   private static String a(String var0) {
      return var0.equals("health") ? "hearts" : "integer";
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.w);
      return this.fixTypeEverywhereTyped("ObjectiveRenderTypeFix", var0, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> {
            Optional<String> var1x = var0xx.get("RenderType").asString().result();
            if (var1x.isEmpty()) {
               String var2 = var0xx.get("CriteriaName").asString("");
               String var3 = a(var2);
               return var0xx.set("RenderType", var0xx.createString(var3));
            } else {
               return var0xx;
            }
         }));
   }
}
