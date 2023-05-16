package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public abstract class DataConverterNamedEntity extends DataFix {
   private final String a;
   private final String b;
   private final TypeReference c;

   public DataConverterNamedEntity(Schema var0, boolean var1, String var2, TypeReference var3, String var4) {
      super(var0, var1);
      this.a = var2;
      this.c = var3;
      this.b = var4;
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<?> var0 = DSL.namedChoice(this.b, this.getInputSchema().getChoiceType(this.c, this.b));
      return this.fixTypeEverywhereTyped(
         this.a,
         this.getInputSchema().getType(this.c),
         this.getOutputSchema().getType(this.c),
         var1x -> var1x.updateTyped(var0, this.getOutputSchema().getChoiceType(this.c, this.b), this::a)
      );
   }

   protected abstract Typed<?> a(Typed<?> var1);
}
