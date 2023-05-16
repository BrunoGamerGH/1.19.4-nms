package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterBlockEntityKeepPacked extends DataConverterNamedEntity {
   public DataConverterBlockEntityKeepPacked(Schema var0, boolean var1) {
      super(var0, var1, "BlockEntityKeepPacked", DataConverterTypes.l, "DUMMY");
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return var0.set("keepPacked", var0.createBoolean(true));
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), DataConverterBlockEntityKeepPacked::a);
   }
}
