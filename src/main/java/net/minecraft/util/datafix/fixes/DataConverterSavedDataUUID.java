package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import org.slf4j.Logger;

public class DataConverterSavedDataUUID extends DataConverterUUIDBase {
   private static final Logger b = LogUtils.getLogger();

   public DataConverterSavedDataUUID(Schema var0) {
      super(var0, DataConverterTypes.h);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "SavedDataUUIDFix",
         this.getInputSchema().getType(this.a),
         var0 -> var0.updateTyped(
               var0.getType().findField("data"),
               var0x -> var0x.update(
                     DSL.remainderFinder(),
                     var0xx -> var0xx.update(
                           "Raids",
                           var0xxx -> var0xxx.createList(
                                 var0xxx.asStream()
                                    .map(
                                       var0xxxx -> var0xxxx.update(
                                             "HeroesOfTheVillage",
                                             var0xxxxx -> var0xxxxx.createList(
                                                   var0xxxxx.asStream().map(var0xxxxxx -> (Dynamic)d(var0xxxxxx, "UUIDMost", "UUIDLeast").orElseGet(() -> {
                                                         b.warn("HeroesOfTheVillage contained invalid UUIDs.");
                                                         return var0xxxxxx;
                                                      }))
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }
}
