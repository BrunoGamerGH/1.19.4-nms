package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public class WorldGenSettingsDisallowOldCustomWorldsFix extends DataFix {
   public WorldGenSettingsDisallowOldCustomWorldsFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.A);
      OpticFinder<?> var1 = var0.findField("dimensions");
      return this.fixTypeEverywhereTyped(
         "WorldGenSettingsDisallowOldCustomWorldsFix_" + this.getOutputSchema().getVersionKey(), var0, var1x -> var1x.updateTyped(var1, var0xx -> {
               var0xx.write().map(var0xxx -> var0xxx.getMapValues().map(var0xxxx -> {
                     var0xxxx.forEach((var0xxxxx, var1xx) -> {
                        if (var1xx.get("type").asString().result().isEmpty()) {
                           throw new IllegalStateException("Unable load old custom worlds.");
                        }
                     });
                     return var0xxxx;
                  }));
               return var0xx;
            })
      );
   }
}
