package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class FilteredSignsFix extends DataConverterNamedEntity {
   public FilteredSignsFix(Schema var0) {
      super(var0, false, "Remove filtered text from signs", DataConverterTypes.l, "minecraft:sign");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), var0x -> var0x.remove("FilteredText1").remove("FilteredText2").remove("FilteredText3").remove("FilteredText4"));
   }
}
