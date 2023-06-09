package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Locale;
import java.util.Optional;

public class DataConverterLang extends DataFix {
   public DataConverterLang(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsLowerCaseLanguageFix", this.getInputSchema().getType(DataConverterTypes.e), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               Optional<String> var1 = var0x.get("lang").asString().result();
               return var1.isPresent() ? var0x.set("lang", var0x.createString(var1.get().toLowerCase(Locale.ROOT))) : var0x;
            })
      );
   }
}
