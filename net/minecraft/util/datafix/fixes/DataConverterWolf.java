package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterWolf extends DataConverterNamedEntity {
   public DataConverterWolf(Schema var0, boolean var1) {
      super(var0, var1, "EntityWolfColorFix", DataConverterTypes.q, "minecraft:wolf");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.update("CollarColor", var0x -> var0x.createByte((byte)(15 - var0x.asInt(0))));
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
