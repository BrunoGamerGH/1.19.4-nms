package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public abstract class ItemStackTagFix extends DataFix {
   private final String a;
   private final Predicate<String> b;

   public ItemStackTagFix(Schema var0, String var1, Predicate<String> var2) {
      super(var0, false);
      this.a = var1;
      this.b = var2;
   }

   public final TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<Pair<String, String>> var1 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      OpticFinder<?> var2 = var0.findField("tag");
      return this.fixTypeEverywhereTyped(
         this.a,
         var0,
         var2x -> {
            Optional<Pair<String, String>> var3x = var2x.getOptional(var1);
            return var3x.isPresent() && this.b.test((String)((Pair)var3x.get()).getSecond())
               ? var2x.updateTyped(var2, var0xx -> var0xx.update(DSL.remainderFinder(), this::a))
               : var2x;
         }
      );
   }

   protected abstract <T> Dynamic<T> a(Dynamic<T> var1);
}
