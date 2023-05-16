package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class AddFlagIfNotPresentFix extends DataFix {
   private final String a;
   private final boolean b;
   private final String c;
   private final TypeReference d;

   public AddFlagIfNotPresentFix(Schema var0, TypeReference var1, String var2, boolean var3) {
      super(var0, true);
      this.b = var3;
      this.c = var2;
      this.a = "AddFlagIfNotPresentFix_" + this.c + "=" + this.b + " for " + var0.getVersionKey();
      this.d = var1;
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(this.d);
      return this.fixTypeEverywhereTyped(
         this.a,
         var0,
         var0x -> var0x.update(
               DSL.remainderFinder(),
               var0xx -> var0xx.set(this.c, (Dynamic)DataFixUtils.orElseGet(var0xx.get(this.c).result(), () -> var0xx.createBoolean(this.b)))
            )
      );
   }
}
