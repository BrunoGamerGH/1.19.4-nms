package net.minecraft.world.level.storage.loot.parameters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;

public class LootContextParameterSets {
   private static final BiMap<MinecraftKey, LootContextParameterSet> n = HashBiMap.create();
   public static final LootContextParameterSet a = a("empty", var0 -> {
   });
   public static final LootContextParameterSet b = a("chest", var0 -> var0.a(LootContextParameters.f).b(LootContextParameters.a));
   public static final LootContextParameterSet c = a("command", var0 -> var0.a(LootContextParameters.f).b(LootContextParameters.a));
   public static final LootContextParameterSet d = a("selector", var0 -> var0.a(LootContextParameters.f).a(LootContextParameters.a));
   public static final LootContextParameterSet e = a("fishing", var0 -> var0.a(LootContextParameters.f).a(LootContextParameters.i).b(LootContextParameters.a));
   public static final LootContextParameterSet f = a(
      "entity",
      var0 -> var0.a(LootContextParameters.a)
            .a(LootContextParameters.f)
            .a(LootContextParameters.c)
            .b(LootContextParameters.d)
            .b(LootContextParameters.e)
            .b(LootContextParameters.b)
   );
   public static final LootContextParameterSet g = a("archaeology", var0 -> var0.a(LootContextParameters.f).b(LootContextParameters.a));
   public static final LootContextParameterSet h = a("gift", var0 -> var0.a(LootContextParameters.f).a(LootContextParameters.a));
   public static final LootContextParameterSet i = a("barter", var0 -> var0.a(LootContextParameters.a));
   public static final LootContextParameterSet j = a("advancement_reward", var0 -> var0.a(LootContextParameters.a).a(LootContextParameters.f));
   public static final LootContextParameterSet k = a("advancement_entity", var0 -> var0.a(LootContextParameters.a).a(LootContextParameters.f));
   public static final LootContextParameterSet l = a(
      "generic",
      var0 -> var0.a(LootContextParameters.a)
            .a(LootContextParameters.b)
            .a(LootContextParameters.c)
            .a(LootContextParameters.d)
            .a(LootContextParameters.e)
            .a(LootContextParameters.f)
            .a(LootContextParameters.g)
            .a(LootContextParameters.h)
            .a(LootContextParameters.i)
            .a(LootContextParameters.j)
   );
   public static final LootContextParameterSet m = a(
      "block",
      var0 -> var0.a(LootContextParameters.g)
            .a(LootContextParameters.f)
            .a(LootContextParameters.i)
            .b(LootContextParameters.a)
            .b(LootContextParameters.h)
            .b(LootContextParameters.j)
   );

   private static LootContextParameterSet a(String var0, Consumer<LootContextParameterSet.Builder> var1) {
      LootContextParameterSet.Builder var2 = new LootContextParameterSet.Builder();
      var1.accept(var2);
      LootContextParameterSet var3 = var2.a();
      MinecraftKey var4 = new MinecraftKey(var0);
      LootContextParameterSet var5 = (LootContextParameterSet)n.put(var4, var3);
      if (var5 != null) {
         throw new IllegalStateException("Loot table parameter set " + var4 + " is already registered");
      } else {
         return var3;
      }
   }

   @Nullable
   public static LootContextParameterSet a(MinecraftKey var0) {
      return (LootContextParameterSet)n.get(var0);
   }

   @Nullable
   public static MinecraftKey a(LootContextParameterSet var0) {
      return (MinecraftKey)n.inverse().get(var0);
   }
}
