package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterSettingRename extends DataFix {
   private final String a;
   private final String b;
   private final String c;

   public DataConverterSettingRename(Schema var0, boolean var1, String var2, String var3, String var4) {
      super(var0, var1);
      this.a = var2;
      this.b = var3;
      this.c = var4;
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         this.a,
         this.getInputSchema().getType(DataConverterTypes.e),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> (Dynamic)DataFixUtils.orElse(var0x.get(this.b).result().map(var1x -> var0x.set(this.c, var1x).remove(this.b)), var0x)
            )
      );
   }
}
