package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterItemFrame extends DataConverterNamedEntity {
   public DataConverterItemFrame(Schema var0, boolean var1) {
      super(var0, var1, "EntityItemFrameDirectionFix", DataConverterTypes.q, "minecraft:item_frame");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.set("Facing", var0.createByte(a(var0.get("Facing").asByte((byte)0))));
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }

   private static byte a(byte var0) {
      switch(var0) {
         case 0:
            return 3;
         case 1:
            return 4;
         case 2:
         default:
            return 2;
         case 3:
            return 5;
      }
   }
}
