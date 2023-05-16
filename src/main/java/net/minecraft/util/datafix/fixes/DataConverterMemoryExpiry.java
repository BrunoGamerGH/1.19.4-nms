package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class DataConverterMemoryExpiry extends DataConverterNamedEntity {
   public DataConverterMemoryExpiry(Schema var0, String var1) {
      super(var0, false, "Memory expiry data fix (" + var1 + ")", DataConverterTypes.q, var1);
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.update("Brain", this::b);
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      return var0.update("memories", this::c);
   }

   private Dynamic<?> c(Dynamic<?> var0) {
      return var0.updateMapValues(this::a);
   }

   private Pair<Dynamic<?>, Dynamic<?>> a(Pair<Dynamic<?>, Dynamic<?>> var0) {
      return var0.mapSecond(this::d);
   }

   private Dynamic<?> d(Dynamic<?> var0) {
      return var0.createMap(ImmutableMap.of(var0.createString("value"), var0));
   }
}
