package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterPOIRebuild extends DataFix {
   public DataConverterPOIRebuild(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> var0 = DSL.named(DataConverterTypes.j.typeName(), DSL.remainderType());
      if (!Objects.equals(var0, this.getInputSchema().getType(DataConverterTypes.j))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI rebuild", var0, var0x -> var0xx -> var0xx.mapSecond(DataConverterPOIRebuild::a));
      }
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      return var0.update("Sections", var0x -> var0x.updateMapValues(var0xx -> var0xx.mapSecond(var0xxx -> var0xxx.remove("Valid"))));
   }
}
