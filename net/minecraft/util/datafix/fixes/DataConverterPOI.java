package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DataConverterPOI extends DataFix {
   public DataConverterPOI(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> var0 = DSL.named(DataConverterTypes.j.typeName(), DSL.remainderType());
      if (!Objects.equals(var0, this.getInputSchema().getType(DataConverterTypes.j))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI reorganization", var0, var0x -> var0xx -> var0xx.mapSecond(DataConverterPOI::a));
      }
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      Map<Dynamic<T>, Dynamic<T>> var1 = Maps.newHashMap();

      for(int var2 = 0; var2 < 16; ++var2) {
         String var3 = String.valueOf(var2);
         Optional<Dynamic<T>> var4 = var0.get(var3).result();
         if (var4.isPresent()) {
            Dynamic<T> var5 = (Dynamic)var4.get();
            Dynamic<T> var6 = var0.createMap(ImmutableMap.of(var0.createString("Records"), var5));
            var1.put(var0.createInt(var2), var6);
            var0 = var0.remove(var3);
         }
      }

      return var0.set("Sections", var0.createMap(var1));
   }
}
