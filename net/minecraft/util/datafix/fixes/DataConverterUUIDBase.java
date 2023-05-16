package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public abstract class DataConverterUUIDBase extends DataFix {
   protected TypeReference a;

   public DataConverterUUIDBase(Schema var0, TypeReference var1) {
      super(var0, false);
      this.a = var1;
   }

   protected Typed<?> a(Typed<?> var0, String var1, Function<Dynamic<?>, Dynamic<?>> var2) {
      Type<?> var3 = this.getInputSchema().getChoiceType(this.a, var1);
      Type<?> var4 = this.getOutputSchema().getChoiceType(this.a, var1);
      return var0.updateTyped(DSL.namedChoice(var1, var3), var4, var1x -> var1x.update(DSL.remainderFinder(), var2));
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0, String var1, String var2) {
      return a(var0, var1).map(var3 -> var0.remove(var1).set(var2, var3));
   }

   protected static Optional<Dynamic<?>> b(Dynamic<?> var0, String var1, String var2) {
      return var0.get(var1).result().flatMap(DataConverterUUIDBase::a).map(var3 -> var0.remove(var1).set(var2, var3));
   }

   protected static Optional<Dynamic<?>> c(Dynamic<?> var0, String var1, String var2) {
      String var3 = var1 + "Most";
      String var4 = var1 + "Least";
      return d(var0, var3, var4).map(var4x -> var0.remove(var3).remove(var4).set(var2, var4x));
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0, String var1) {
      return var0.get(var1).result().flatMap(var1x -> {
         String var2 = var1x.asString(null);
         if (var2 != null) {
            try {
               UUID var3 = UUID.fromString(var2);
               return a(var0, var3.getMostSignificantBits(), var3.getLeastSignificantBits());
            } catch (IllegalArgumentException var4) {
            }
         }

         return Optional.empty();
      });
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0) {
      return d(var0, "M", "L");
   }

   protected static Optional<Dynamic<?>> d(Dynamic<?> var0, String var1, String var2) {
      long var3 = var0.get(var1).asLong(0L);
      long var5 = var0.get(var2).asLong(0L);
      return var3 != 0L && var5 != 0L ? a(var0, var3, var5) : Optional.empty();
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0, long var1, long var3) {
      return Optional.of(var0.createIntList(Arrays.stream(new int[]{(int)(var1 >> 32), (int)var1, (int)(var3 >> 32), (int)var3})));
   }
}
