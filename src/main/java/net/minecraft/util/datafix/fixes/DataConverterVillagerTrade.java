package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterVillagerTrade extends DataConverterNamedEntity {
   public DataConverterVillagerTrade(Schema var0, boolean var1) {
      super(var0, var1, "Villager trade fix", DataConverterTypes.q, "minecraft:villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      OpticFinder<?> var1 = var0.getType().findField("Offers");
      OpticFinder<?> var2 = var1.type().findField("Recipes");
      Type<?> var3 = var2.type();
      if (!(var3 instanceof ListType)) {
         throw new IllegalStateException("Recipes are expected to be a list.");
      } else {
         ListType<?> var4 = (ListType)var3;
         Type<?> var5 = var4.getElement();
         OpticFinder<?> var6 = DSL.typeFinder(var5);
         OpticFinder<?> var7 = var5.findField("buy");
         OpticFinder<?> var8 = var5.findField("buyB");
         OpticFinder<?> var9 = var5.findField("sell");
         OpticFinder<Pair<String, String>> var10 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
         Function<Typed<?>, Typed<?>> var11 = var1x -> this.a(var10, var1x);
         return var0.updateTyped(
            var1,
            var6x -> var6x.updateTyped(
                  var2, var5xx -> var5xx.updateTyped(var6, var4xxx -> var4xxx.updateTyped(var7, var11).updateTyped(var8, var11).updateTyped(var9, var11))
               )
         );
      }
   }

   private Typed<?> a(OpticFinder<Pair<String, String>> var0, Typed<?> var1) {
      return var1.update(var0, var0x -> var0x.mapSecond(var0xx -> Objects.equals(var0xx, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : var0xx));
   }
}
