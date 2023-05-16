package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;

public class DataConverterEntityProjectileOwner extends DataFix {
   public DataConverterEntityProjectileOwner(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      return this.fixTypeEverywhereTyped("EntityProjectileOwner", var0.getType(DataConverterTypes.q), this::a);
   }

   private Typed<?> a(Typed<?> var0) {
      var0 = this.a(var0, "minecraft:egg", this::d);
      var0 = this.a(var0, "minecraft:ender_pearl", this::d);
      var0 = this.a(var0, "minecraft:experience_bottle", this::d);
      var0 = this.a(var0, "minecraft:snowball", this::d);
      var0 = this.a(var0, "minecraft:potion", this::d);
      var0 = this.a(var0, "minecraft:potion", this::c);
      var0 = this.a(var0, "minecraft:llama_spit", this::b);
      var0 = this.a(var0, "minecraft:arrow", this::a);
      var0 = this.a(var0, "minecraft:spectral_arrow", this::a);
      return this.a(var0, "minecraft:trident", this::a);
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      long var1 = var0.get("OwnerUUIDMost").asLong(0L);
      long var3 = var0.get("OwnerUUIDLeast").asLong(0L);
      return this.a(var0, var1, var3).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      OptionalDynamic<?> var1 = var0.get("Owner");
      long var2 = var1.get("OwnerUUIDMost").asLong(0L);
      long var4 = var1.get("OwnerUUIDLeast").asLong(0L);
      return this.a(var0, var2, var4).remove("Owner");
   }

   private Dynamic<?> c(Dynamic<?> var0) {
      OptionalDynamic<?> var1 = var0.get("Potion");
      return var0.set("Item", var1.orElseEmptyMap()).remove("Potion");
   }

   private Dynamic<?> d(Dynamic<?> var0) {
      String var1 = "owner";
      OptionalDynamic<?> var2 = var0.get("owner");
      long var3 = var2.get("M").asLong(0L);
      long var5 = var2.get("L").asLong(0L);
      return this.a(var0, var3, var5).remove("owner");
   }

   private Dynamic<?> a(Dynamic<?> var0, long var1, long var3) {
      String var5 = "OwnerUUID";
      return var1 != 0L && var3 != 0L ? var0.set("OwnerUUID", var0.createIntList(Arrays.stream(a(var1, var3)))) : var0;
   }

   private static int[] a(long var0, long var2) {
      return new int[]{(int)(var0 >> 32), (int)var0, (int)(var2 >> 32), (int)var2};
   }

   private Typed<?> a(Typed<?> var0, String var1, Function<Dynamic<?>, Dynamic<?>> var2) {
      Type<?> var3 = this.getInputSchema().getChoiceType(DataConverterTypes.q, var1);
      Type<?> var4 = this.getOutputSchema().getChoiceType(DataConverterTypes.q, var1);
      return var0.updateTyped(DSL.namedChoice(var1, var3), var4, var1x -> var1x.update(DSL.remainderFinder(), var2));
   }
}
