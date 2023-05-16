package net.minecraft.data.loot.packs;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;
import net.minecraft.resources.MinecraftKey;

public class UpdateOneTwentyBuiltInLootTables {
   private static final Set<MinecraftKey> c = Sets.newHashSet();
   private static final Set<MinecraftKey> d = Collections.unmodifiableSet(c);
   public static final MinecraftKey a = a("archaeology/desert_well");
   public static final MinecraftKey b = a("archaeology/desert_pyramid");

   private static MinecraftKey a(String var0) {
      return a(new MinecraftKey(var0));
   }

   private static MinecraftKey a(MinecraftKey var0) {
      if (c.add(var0)) {
         return var0;
      } else {
         throw new IllegalArgumentException(var0 + " is already a registered built-in loot table");
      }
   }

   public static Set<MinecraftKey> a() {
      return d;
   }
}
