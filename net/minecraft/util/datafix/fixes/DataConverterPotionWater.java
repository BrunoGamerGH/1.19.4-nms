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
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterPotionWater extends DataFix {
   public DataConverterPotionWater(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<Pair<String, String>> var1 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      OpticFinder<?> var2 = var0.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWaterPotionFix",
         var0,
         var2x -> {
            Optional<Pair<String, String>> var3x = var2x.getOptional(var1);
            if (var3x.isPresent()) {
               String var4 = (String)((Pair)var3x.get()).getSecond();
               if ("minecraft:potion".equals(var4)
                  || "minecraft:splash_potion".equals(var4)
                  || "minecraft:lingering_potion".equals(var4)
                  || "minecraft:tipped_arrow".equals(var4)) {
                  Typed<?> var5 = var2x.getOrCreateTyped(var2);
                  Dynamic<?> var6 = (Dynamic)var5.get(DSL.remainderFinder());
                  if (!var6.get("Potion").asString().result().isPresent()) {
                     var6 = var6.set("Potion", var6.createString("minecraft:water"));
                  }
   
                  return var2x.set(var2, var5.set(DSL.remainderFinder(), var6));
               }
            }
   
            return var2x;
         }
      );
   }
}
