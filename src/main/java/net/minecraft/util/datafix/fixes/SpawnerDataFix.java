package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;

public class SpawnerDataFix extends DataFix {
   public SpawnerDataFix(Schema var0) {
      super(var0, true);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.u);
      Type<?> var1 = this.getOutputSchema().getType(DataConverterTypes.u);
      OpticFinder<?> var2 = var0.findField("SpawnData");
      Type<?> var3 = var1.findField("SpawnData").type();
      OpticFinder<?> var4 = var0.findField("SpawnPotentials");
      Type<?> var5 = var1.findField("SpawnPotentials").type();
      return this.fixTypeEverywhereTyped(
         "Fix mob spawner data structure",
         var0,
         var1,
         var4x -> var4x.updateTyped(var2, var3, var1xx -> this.a(var3, var1xx)).updateTyped(var4, var5, var1xx -> this.b(var5, var1xx))
      );
   }

   private <T> Typed<T> a(Type<T> var0, Typed<?> var1) {
      DynamicOps<?> var2 = var1.getOps();
      return new Typed(var0, var2, Pair.of(var1.getValue(), new Dynamic(var2)));
   }

   private <T> Typed<T> b(Type<T> var0, Typed<?> var1) {
      DynamicOps<?> var2 = var1.getOps();
      List<?> var3 = (List)var1.getValue();
      List<?> var4 = var3.stream().map(var1x -> {
         Pair<Object, Dynamic<?>> var2x = (Pair)var1x;
         int var3x = ((Dynamic)var2x.getSecond()).get("Weight").asNumber().result().orElse(1).intValue();
         Dynamic<?> var4x = new Dynamic(var2);
         var4x = var4x.set("weight", var4x.createInt(var3x));
         Dynamic<?> var5x = ((Dynamic)var2x.getSecond()).remove("Weight").remove("Entity");
         return Pair.of(Pair.of(var2x.getFirst(), var5x), var4x);
      }).toList();
      return new Typed(var0, var2, var4);
   }
}
