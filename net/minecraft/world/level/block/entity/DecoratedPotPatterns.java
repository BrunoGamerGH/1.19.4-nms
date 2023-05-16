package net.minecraft.world.level.block.entity;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DecoratedPotPatterns {
   public static final String a = "decorated_pot_base";
   public static final ResourceKey<String> b = a("decorated_pot_base");
   public static final String c = "decorated_pot_side";
   public static final String d = "pottery_pattern_archer";
   public static final String e = "pottery_pattern_prize";
   public static final String f = "pottery_pattern_arms_up";
   public static final String g = "pottery_pattern_skull";
   public static final ResourceKey<String> h = a("decorated_pot_side");
   public static final ResourceKey<String> i = a("pottery_pattern_archer");
   public static final ResourceKey<String> j = a("pottery_pattern_prize");
   public static final ResourceKey<String> k = a("pottery_pattern_arms_up");
   public static final ResourceKey<String> l = a("pottery_pattern_skull");
   private static final Map<Item, ResourceKey<String>> m = Map.ofEntries(
      Map.entry(Items.wC, i), Map.entry(Items.wD, j), Map.entry(Items.wE, k), Map.entry(Items.wF, l), Map.entry(Items.pT, h)
   );

   private static ResourceKey<String> a(String var0) {
      return ResourceKey.a(Registries.am, new MinecraftKey(var0));
   }

   public static MinecraftKey a(ResourceKey<String> var0) {
      return var0.a().d("entity/decorated_pot/");
   }

   @Nullable
   public static ResourceKey<String> a(Item var0) {
      return m.get(var0);
   }

   public static String a(IRegistry<String> var0) {
      IRegistry.a(var0, i, "pottery_pattern_archer");
      IRegistry.a(var0, j, "pottery_pattern_prize");
      IRegistry.a(var0, k, "pottery_pattern_arms_up");
      IRegistry.a(var0, l, "pottery_pattern_skull");
      IRegistry.a(var0, h, "decorated_pot_side");
      return IRegistry.a(var0, b, "decorated_pot_base");
   }
}
