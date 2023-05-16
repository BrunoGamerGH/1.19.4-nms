package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class BlendingDataFix extends DataFix {
   private final String a;
   private static final Set<String> b = Set.of("minecraft:empty", "minecraft:structure_starts", "minecraft:structure_references", "minecraft:biomes");

   public BlendingDataFix(Schema var0) {
      super(var0, false);
      this.a = "Blending Data Fix v" + var0.getVersionKey();
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.c);
      return this.fixTypeEverywhereTyped(this.a, var0, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> a(var0xx, var0xx.get("__context"))));
   }

   private static Dynamic<?> a(Dynamic<?> var0, OptionalDynamic<?> var1) {
      var0 = var0.remove("blending_data");
      boolean var2 = "minecraft:overworld".equals(var1.get("dimension").asString().result().orElse(""));
      Optional<? extends Dynamic<?>> var3 = var0.get("Status").result();
      if (var2 && var3.isPresent()) {
         String var4 = DataConverterSchemaNamed.a(((Dynamic)var3.get()).asString("empty"));
         Optional<? extends Dynamic<?>> var5 = var0.get("below_zero_retrogen").result();
         if (!b.contains(var4)) {
            var0 = a(var0, 384, -64);
         } else if (var5.isPresent()) {
            Dynamic<?> var6 = (Dynamic)var5.get();
            String var7 = DataConverterSchemaNamed.a(var6.get("target_status").asString("empty"));
            if (!b.contains(var7)) {
               var0 = a(var0, 256, 0);
            }
         }
      }

      return var0;
   }

   private static Dynamic<?> a(Dynamic<?> var0, int var1, int var2) {
      return var0.set(
         "blending_data",
         var0.createMap(
            Map.of(
               var0.createString("min_section"),
               var0.createInt(SectionPosition.a(var2)),
               var0.createString("max_section"),
               var0.createInt(SectionPosition.a(var2 + var1))
            )
         )
      );
   }
}
