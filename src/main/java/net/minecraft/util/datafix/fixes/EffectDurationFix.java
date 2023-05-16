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

public class EffectDurationFix extends DataFix {
   private static final Set<String> a = Set.of("minecraft:potion", "minecraft:splash_potion", "minecraft:lingering_potion", "minecraft:tipped_arrow");

   public EffectDurationFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      Type<?> var1 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<Pair<String, String>> var2 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      OpticFinder<?> var3 = var1.findField("tag");
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped("EffectDurationEntity", var0.getType(DataConverterTypes.q), var0x -> var0x.update(DSL.remainderFinder(), this::c)),
         new TypeRewriteRule[]{
            this.fixTypeEverywhereTyped("EffectDurationPlayer", var0.getType(DataConverterTypes.b), var0x -> var0x.update(DSL.remainderFinder(), this::c)),
            this.fixTypeEverywhereTyped("EffectDurationItem", var1, var2x -> {
               Optional<Pair<String, String>> var3x = var2x.getOptional(var2);
               if (var3x.filter(a::contains).isPresent()) {
                  Optional<? extends Typed<?>> var4x = var2x.getOptionalTyped(var3);
                  if (var4x.isPresent()) {
                     Dynamic<?> var5 = (Dynamic)((Typed)var4x.get()).get(DSL.remainderFinder());
                     Typed<?> var6 = ((Typed)var4x.get()).set(DSL.remainderFinder(), var5.update("CustomPotionEffects", this::b));
                     return var2x.set(var3, var6);
                  }
               }
      
               return var2x;
            })
         }
      );
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      return var0.update("FactorCalculationData", var1x -> {
         int var2 = var1x.get("effect_changed_timestamp").asInt(-1);
         var1x = var1x.remove("effect_changed_timestamp");
         int var3 = var0.get("Duration").asInt(-1);
         int var4 = var2 - var3;
         return var1x.set("ticks_active", var1x.createInt(var4));
      });
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      return var0.createList(var0.asStream().map(this::a));
   }

   private Dynamic<?> c(Dynamic<?> var0) {
      var0 = var0.update("Effects", this::b);
      var0 = var0.update("ActiveEffects", this::b);
      return var0.update("CustomPotionEffects", this::b);
   }
}
