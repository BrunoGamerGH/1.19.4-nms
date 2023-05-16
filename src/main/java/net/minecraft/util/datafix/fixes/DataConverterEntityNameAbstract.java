package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public abstract class DataConverterEntityNameAbstract extends DataConverterEntityName {
   public DataConverterEntityNameAbstract(String var0, Schema var1, boolean var2) {
      super(var0, var1, var2);
   }

   @Override
   protected Pair<String, Typed<?>> a(String var0, Typed<?> var1) {
      Pair<String, Dynamic<?>> var2 = this.a(var0, (Dynamic<?>)var1.getOrCreate(DSL.remainderFinder()));
      return Pair.of((String)var2.getFirst(), var1.set(DSL.remainderFinder(), (Dynamic)var2.getSecond()));
   }

   protected abstract Pair<String, Dynamic<?>> a(String var1, Dynamic<?> var2);
}
