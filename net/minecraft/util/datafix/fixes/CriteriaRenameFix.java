package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.UnaryOperator;

public class CriteriaRenameFix extends DataFix {
   private final String a;
   private final String b;
   private final UnaryOperator<String> c;

   public CriteriaRenameFix(Schema var0, String var1, String var2, UnaryOperator<String> var3) {
      super(var0, false);
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(this.a, this.getInputSchema().getType(DataConverterTypes.i), var0 -> var0.update(DSL.remainderFinder(), this::a));
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      return var0.update(
         this.b,
         var0x -> var0x.update(
               "criteria",
               var0xx -> var0xx.updateMapValues(
                     var0xxx -> var0xxx.mapFirst(
                           var0xxxx -> (Dynamic)DataFixUtils.orElse(
                                 var0xxxx.asString().map(var1x -> var0xxxx.createString(this.c.apply(var1x))).result(), var0xxxx
                              )
                        )
                  )
            )
      );
   }
}
