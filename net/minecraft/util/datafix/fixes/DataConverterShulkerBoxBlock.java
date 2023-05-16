package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class DataConverterShulkerBoxBlock extends DataConverterNamedEntity {
   public DataConverterShulkerBoxBlock(Schema var0, boolean var1) {
      super(var0, var1, "BlockEntityShulkerBoxColorFix", DataConverterTypes.l, "minecraft:shulker_box");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), var0x -> var0x.remove("Color"));
   }
}
