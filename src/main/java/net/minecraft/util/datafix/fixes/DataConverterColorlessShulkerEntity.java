package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class DataConverterColorlessShulkerEntity extends DataConverterNamedEntity {
   public DataConverterColorlessShulkerEntity(Schema var0, boolean var1) {
      super(var0, var1, "Colorless shulker entity fix", DataConverterTypes.q, "minecraft:shulker");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), var0x -> var0x.get("Color").asInt(0) == 10 ? var0x.set("Color", var0x.createByte((byte)16)) : var0x);
   }
}
