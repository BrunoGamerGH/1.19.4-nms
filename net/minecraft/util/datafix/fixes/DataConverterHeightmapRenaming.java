package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class DataConverterHeightmapRenaming extends DataFix {
   public DataConverterHeightmapRenaming(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      OpticFinder<?> var1 = var0.findField("Level");
      return this.fixTypeEverywhereTyped(
         "HeightmapRenamingFix", var0, var1x -> var1x.updateTyped(var1, var0xx -> var0xx.update(DSL.remainderFinder(), this::a))
      );
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      Optional<? extends Dynamic<?>> var1 = var0.get("Heightmaps").result();
      if (!var1.isPresent()) {
         return var0;
      } else {
         Dynamic<?> var2 = (Dynamic)var1.get();
         Optional<? extends Dynamic<?>> var3 = var2.get("LIQUID").result();
         if (var3.isPresent()) {
            var2 = var2.remove("LIQUID");
            var2 = var2.set("WORLD_SURFACE_WG", (Dynamic)var3.get());
         }

         Optional<? extends Dynamic<?>> var4 = var2.get("SOLID").result();
         if (var4.isPresent()) {
            var2 = var2.remove("SOLID");
            var2 = var2.set("OCEAN_FLOOR_WG", (Dynamic)var4.get());
            var2 = var2.set("OCEAN_FLOOR", (Dynamic)var4.get());
         }

         Optional<? extends Dynamic<?>> var5 = var2.get("LIGHT").result();
         if (var5.isPresent()) {
            var2 = var2.remove("LIGHT");
            var2 = var2.set("LIGHT_BLOCKING", (Dynamic)var5.get());
         }

         Optional<? extends Dynamic<?>> var6 = var2.get("RAIN").result();
         if (var6.isPresent()) {
            var2 = var2.remove("RAIN");
            var2 = var2.set("MOTION_BLOCKING", (Dynamic)var6.get());
            var2 = var2.set("MOTION_BLOCKING_NO_LEAVES", (Dynamic)var6.get());
         }

         return var0.set("Heightmaps", var2);
      }
   }
}
