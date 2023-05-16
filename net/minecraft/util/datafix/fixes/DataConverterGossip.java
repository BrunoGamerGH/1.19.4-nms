package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterGossip extends DataConverterNamedEntity {
   public DataConverterGossip(Schema var0, String var1) {
      super(var0, false, "Gossip for for " + var1, DataConverterTypes.q, var1);
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(
         DSL.remainderFinder(),
         var0x -> var0x.update(
               "Gossips",
               var0xx -> (Dynamic)DataFixUtils.orElse(
                     var0xx.asStreamOpt()
                        .result()
                        .map(var0xxx -> var0xxx.map(var0xxxx -> (Dynamic)DataConverterUUIDBase.c(var0xxxx, "Target", "Target").orElse(var0xxxx)))
                        .map(var0xx::createList),
                     var0xx
                  )
            )
      );
   }
}
