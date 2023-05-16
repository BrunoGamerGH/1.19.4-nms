package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;

public class DataConverterMobSpawner extends DataFix {
   public DataConverterMobSpawner(Schema var0, boolean var1) {
      super(var0, var1);
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      if (!"MobSpawner".equals(var0.get("id").asString(""))) {
         return var0;
      } else {
         Optional<String> var1 = var0.get("EntityId").asString().result();
         if (var1.isPresent()) {
            Dynamic<?> var2 = (Dynamic)DataFixUtils.orElse(var0.get("SpawnData").result(), var0.emptyMap());
            var2 = var2.set("id", var2.createString(var1.get().isEmpty() ? "Pig" : var1.get()));
            var0 = var0.set("SpawnData", var2);
            var0 = var0.remove("EntityId");
         }

         Optional<? extends Stream<? extends Dynamic<?>>> var2 = var0.get("SpawnPotentials").asStreamOpt().result();
         if (var2.isPresent()) {
            var0 = var0.set(
               "SpawnPotentials",
               var0.createList(
                  var2.get()
                     .map(
                        var0x -> {
                           Optional<String> var1x = var0x.get("Type").asString().result();
                           if (var1x.isPresent()) {
                              Dynamic<?> var2x = ((Dynamic)DataFixUtils.orElse(var0x.get("Properties").result(), var0x.emptyMap()))
                                 .set("id", var0x.createString((String)var1x.get()));
                              return var0x.set("Entity", var2x).remove("Type").remove("Properties");
                           } else {
                              return var0x;
                           }
                        }
                     )
               )
            );
         }

         return var0;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.u);
      return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(DataConverterTypes.u), var0, var1x -> {
         Dynamic<?> var2 = (Dynamic)var1x.get(DSL.remainderFinder());
         var2 = var2.set("id", var2.createString("MobSpawner"));
         DataResult<? extends Pair<? extends Typed<?>, ?>> var3 = var0.readTyped(this.a(var2));
         return !var3.result().isPresent() ? var1x : (Typed)((Pair)var3.result().get()).getFirst();
      });
   }
}
