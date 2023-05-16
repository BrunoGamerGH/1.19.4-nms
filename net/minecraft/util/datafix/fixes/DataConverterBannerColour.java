package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterBannerColour extends DataConverterNamedEntity {
   public DataConverterBannerColour(Schema var0, boolean var1) {
      super(var0, var1, "BlockEntityBannerColorFix", DataConverterTypes.l, "minecraft:banner");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      var0 = var0.update("Base", var0x -> var0x.createInt(15 - var0x.asInt(0)));
      return var0.update(
         "Patterns",
         var0x -> (Dynamic)DataFixUtils.orElse(
               var0x.asStreamOpt()
                  .map(var0xx -> var0xx.map(var0xxx -> var0xxx.update("Color", var0xxxx -> var0xxxx.createInt(15 - var0xxxx.asInt(0)))))
                  .map(var0x::createList)
                  .result(),
               var0x
            )
      );
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
