package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Locale;

public abstract class DataConverterEntityName extends DataFix {
   protected final String a;

   public DataConverterEntityName(String var0, Schema var1, boolean var2) {
      super(var1, var2);
      this.a = var0;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> var0 = this.getInputSchema().findChoiceType(DataConverterTypes.q);
      TaggedChoiceType<String> var1 = this.getOutputSchema().findChoiceType(DataConverterTypes.q);
      return this.fixTypeEverywhere(
         this.a,
         var0,
         var1,
         var2x -> var3x -> {
               String var4 = (String)var3x.getFirst();
               Type<?> var5 = (Type)var0.types().get(var4);
               Pair<String, Typed<?>> var6 = this.a(var4, this.a(var3x.getSecond(), var2x, var5));
               Type<?> var7 = (Type)var1.types().get(var6.getFirst());
               if (!var7.equals(((Typed)var6.getSecond()).getType(), true, true)) {
                  throw new IllegalStateException(
                     String.format(Locale.ROOT, "Dynamic type check failed: %s not equal to %s", var7, ((Typed)var6.getSecond()).getType())
                  );
               } else {
                  return Pair.of((String)var6.getFirst(), ((Typed)var6.getSecond()).getValue());
               }
            }
      );
   }

   private <A> Typed<A> a(Object var0, DynamicOps<?> var1, Type<A> var2) {
      return new Typed(var2, var1, var0);
   }

   protected abstract Pair<String, Typed<?>> a(String var1, Typed<?> var2);
}
