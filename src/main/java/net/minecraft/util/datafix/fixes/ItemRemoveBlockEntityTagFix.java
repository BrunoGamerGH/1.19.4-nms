package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class ItemRemoveBlockEntityTagFix extends DataFix {
   private final Set<String> a;

   public ItemRemoveBlockEntityTagFix(Schema var0, boolean var1, Set<String> var2) {
      super(var0, var1);
      this.a = var2;
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<Pair<String, String>> var1 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      OpticFinder<?> var2 = var0.findField("tag");
      OpticFinder<?> var3 = var2.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped("ItemRemoveBlockEntityTagFix", var0, var3x -> {
         Optional<Pair<String, String>> var4x = var3x.getOptional(var1);
         if (var4x.isPresent() && this.a.contains(((Pair)var4x.get()).getSecond())) {
            Optional<? extends Typed<?>> var5 = var3x.getOptionalTyped(var2);
            if (var5.isPresent()) {
               Typed<?> var6 = (Typed)var5.get();
               Optional<? extends Typed<?>> var7 = var6.getOptionalTyped(var3);
               if (var7.isPresent()) {
                  Optional<? extends Dynamic<?>> var8 = var6.write().result();
                  Dynamic<?> var9 = var8.isPresent() ? (Dynamic)var8.get() : (Dynamic)var6.get(DSL.remainderFinder());
                  Dynamic<?> var10 = var9.remove("BlockEntityTag");
                  Optional<? extends Pair<? extends Typed<?>, ?>> var11 = var2.type().readTyped(var10).result();
                  if (var11.isEmpty()) {
                     return var3x;
                  }

                  return var3x.set(var2, (Typed)((Pair)var11.get()).getFirst());
               }
            }
         }

         return var3x;
      });
   }
}
