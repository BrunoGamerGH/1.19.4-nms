package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public abstract class DataConverterEntityRenameAbstract extends DataFix {
   private final String a;

   public DataConverterEntityRenameAbstract(String var0, Schema var1, boolean var2) {
      super(var1, var2);
      this.a = var0;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> var0 = this.getInputSchema().findChoiceType(DataConverterTypes.q);
      TaggedChoiceType<String> var1 = this.getOutputSchema().findChoiceType(DataConverterTypes.q);
      Type<Pair<String, String>> var2 = DSL.named(DataConverterTypes.o.typeName(), DataConverterSchemaNamed.a());
      if (!Objects.equals(this.getOutputSchema().getType(DataConverterTypes.o), var2)) {
         throw new IllegalStateException("Entity name type is not what was expected.");
      } else {
         return TypeRewriteRule.seq(this.fixTypeEverywhere(this.a, var0, var1, var2x -> var2xx -> var2xx.mapFirst(var2xxx -> {
                  String var3x = this.a(var2xxx);
                  Type<?> var4 = (Type)var0.types().get(var2xxx);
                  Type<?> var5 = (Type)var1.types().get(var3x);
                  if (!var5.equals(var4, true, true)) {
                     throw new IllegalStateException(String.format(Locale.ROOT, "Dynamic type check failed: %s not equal to %s", var5, var4));
                  } else {
                     return var3x;
                  }
               })), this.fixTypeEverywhere(this.a + " for entity name", var2, var0x -> var0xx -> var0xx.mapSecond(this::a)));
      }
   }

   protected abstract String a(String var1);
}
