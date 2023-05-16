package net.minecraft.world.item.armortrim;

import java.util.Optional;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TrimPatterns {
   public static final ResourceKey<TrimPattern> a = a("sentry");
   public static final ResourceKey<TrimPattern> b = a("dune");
   public static final ResourceKey<TrimPattern> c = a("coast");
   public static final ResourceKey<TrimPattern> d = a("wild");
   public static final ResourceKey<TrimPattern> e = a("ward");
   public static final ResourceKey<TrimPattern> f = a("eye");
   public static final ResourceKey<TrimPattern> g = a("vex");
   public static final ResourceKey<TrimPattern> h = a("tide");
   public static final ResourceKey<TrimPattern> i = a("snout");
   public static final ResourceKey<TrimPattern> j = a("rib");
   public static final ResourceKey<TrimPattern> k = a("spire");

   public static void a(BootstapContext<TrimPattern> var0) {
   }

   public static void b(BootstapContext<TrimPattern> var0) {
      a(var0, Items.wr, a);
      a(var0, Items.ws, b);
      a(var0, Items.wt, c);
      a(var0, Items.wu, d);
      a(var0, Items.wv, e);
      a(var0, Items.ww, f);
      a(var0, Items.wx, g);
      a(var0, Items.wy, h);
      a(var0, Items.wz, i);
      a(var0, Items.wA, j);
      a(var0, Items.wB, k);
   }

   public static Optional<Holder.c<TrimPattern>> a(IRegistryCustom var0, ItemStack var1) {
      return var0.d(Registries.aC).h().filter(var1x -> var1.a(var1x.a().b())).findFirst();
   }

   private static void a(BootstapContext<TrimPattern> var0, Item var1, ResourceKey<TrimPattern> var2) {
      TrimPattern var3 = new TrimPattern(var2.a(), BuiltInRegistries.i.d(var1), IChatBaseComponent.c(SystemUtils.a("trim_pattern", var2.a())));
      var0.a(var2, var3);
   }

   private static ResourceKey<TrimPattern> a(String var0) {
      return ResourceKey.a(Registries.aC, new MinecraftKey(var0));
   }
}
