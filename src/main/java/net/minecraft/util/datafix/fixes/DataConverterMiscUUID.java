package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import org.slf4j.Logger;

public class DataConverterMiscUUID extends DataConverterUUIDBase {
   private static final Logger b = LogUtils.getLogger();

   public DataConverterMiscUUID(Schema var0) {
      super(var0, DataConverterTypes.a);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelUUIDFix",
         this.getInputSchema().getType(this.a),
         var0 -> var0.updateTyped(DSL.remainderFinder(), var0x -> var0x.update(DSL.remainderFinder(), var0xx -> {
                  var0xx = this.d(var0xx);
                  var0xx = this.c(var0xx);
                  return this.b(var0xx);
               }))
      );
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      return (Dynamic<?>)a(var0, "WanderingTraderId", "WanderingTraderId").orElse(var0);
   }

   private Dynamic<?> c(Dynamic<?> var0) {
      return var0.update(
         "DimensionData",
         var0x -> var0x.updateMapValues(
               var0xx -> var0xx.mapSecond(var0xxx -> var0xxx.update("DragonFight", var0xxxx -> (Dynamic)c(var0xxxx, "DragonUUID", "Dragon").orElse(var0xxxx)))
            )
      );
   }

   private Dynamic<?> d(Dynamic<?> var0) {
      return var0.update(
         "CustomBossEvents",
         var0x -> var0x.updateMapValues(
               var0xx -> var0xx.mapSecond(
                     var0xxx -> var0xxx.update(
                           "Players", var1x -> var0xxx.createList(var1x.asStream().map(var0xxxxx -> (Dynamic)a(var0xxxxx).orElseGet(() -> {
                                    b.warn("CustomBossEvents contains invalid UUIDs.");
                                    return var0xxxxx;
                                 })))
                        )
                  )
            )
      );
   }
}
