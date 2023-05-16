package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Locale;

public class DataConverterAddChoices extends DataFix {
   private final String a;
   private final TypeReference b;

   public DataConverterAddChoices(Schema var0, String var1, TypeReference var2) {
      super(var0, true);
      this.a = var1;
      this.b = var2;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<?> var0 = this.getInputSchema().findChoiceType(this.b);
      TaggedChoiceType<?> var1 = this.getOutputSchema().findChoiceType(this.b);
      return this.a(this.a, var0, var1);
   }

   protected final <K> TypeRewriteRule a(String var0, TaggedChoiceType<K> var1, TaggedChoiceType<?> var2) {
      if (var1.getKeyType() != var2.getKeyType()) {
         throw new IllegalStateException("Could not inject: key type is not the same");
      } else {
         return this.fixTypeEverywhere(var0, var1, var2, var1x -> var1xx -> {
               if (!var2.hasType(var1xx.getFirst())) {
                  throw new IllegalArgumentException(String.format(Locale.ROOT, "Unknown type %s in %s ", var1xx.getFirst(), this.b));
               } else {
                  return var1xx;
               }
            });
      }
   }
}
