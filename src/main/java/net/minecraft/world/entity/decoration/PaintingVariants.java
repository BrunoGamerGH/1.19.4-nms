package net.minecraft.world.entity.decoration;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public class PaintingVariants {
   public static final ResourceKey<PaintingVariant> a = a("kebab");
   public static final ResourceKey<PaintingVariant> b = a("aztec");
   public static final ResourceKey<PaintingVariant> c = a("alban");
   public static final ResourceKey<PaintingVariant> d = a("aztec2");
   public static final ResourceKey<PaintingVariant> e = a("bomb");
   public static final ResourceKey<PaintingVariant> f = a("plant");
   public static final ResourceKey<PaintingVariant> g = a("wasteland");
   public static final ResourceKey<PaintingVariant> h = a("pool");
   public static final ResourceKey<PaintingVariant> i = a("courbet");
   public static final ResourceKey<PaintingVariant> j = a("sea");
   public static final ResourceKey<PaintingVariant> k = a("sunset");
   public static final ResourceKey<PaintingVariant> l = a("creebet");
   public static final ResourceKey<PaintingVariant> m = a("wanderer");
   public static final ResourceKey<PaintingVariant> n = a("graham");
   public static final ResourceKey<PaintingVariant> o = a("match");
   public static final ResourceKey<PaintingVariant> p = a("bust");
   public static final ResourceKey<PaintingVariant> q = a("stage");
   public static final ResourceKey<PaintingVariant> r = a("void");
   public static final ResourceKey<PaintingVariant> s = a("skull_and_roses");
   public static final ResourceKey<PaintingVariant> t = a("wither");
   public static final ResourceKey<PaintingVariant> u = a("fighters");
   public static final ResourceKey<PaintingVariant> v = a("pointer");
   public static final ResourceKey<PaintingVariant> w = a("pigscene");
   public static final ResourceKey<PaintingVariant> x = a("burning_skull");
   public static final ResourceKey<PaintingVariant> y = a("skeleton");
   public static final ResourceKey<PaintingVariant> z = a("donkey_kong");
   public static final ResourceKey<PaintingVariant> A = a("earth");
   public static final ResourceKey<PaintingVariant> B = a("wind");
   public static final ResourceKey<PaintingVariant> C = a("water");
   public static final ResourceKey<PaintingVariant> D = a("fire");

   public static PaintingVariant a(IRegistry<PaintingVariant> var0) {
      IRegistry.a(var0, a, new PaintingVariant(16, 16));
      IRegistry.a(var0, b, new PaintingVariant(16, 16));
      IRegistry.a(var0, c, new PaintingVariant(16, 16));
      IRegistry.a(var0, d, new PaintingVariant(16, 16));
      IRegistry.a(var0, e, new PaintingVariant(16, 16));
      IRegistry.a(var0, f, new PaintingVariant(16, 16));
      IRegistry.a(var0, g, new PaintingVariant(16, 16));
      IRegistry.a(var0, h, new PaintingVariant(32, 16));
      IRegistry.a(var0, i, new PaintingVariant(32, 16));
      IRegistry.a(var0, j, new PaintingVariant(32, 16));
      IRegistry.a(var0, k, new PaintingVariant(32, 16));
      IRegistry.a(var0, l, new PaintingVariant(32, 16));
      IRegistry.a(var0, m, new PaintingVariant(16, 32));
      IRegistry.a(var0, n, new PaintingVariant(16, 32));
      IRegistry.a(var0, o, new PaintingVariant(32, 32));
      IRegistry.a(var0, p, new PaintingVariant(32, 32));
      IRegistry.a(var0, q, new PaintingVariant(32, 32));
      IRegistry.a(var0, r, new PaintingVariant(32, 32));
      IRegistry.a(var0, s, new PaintingVariant(32, 32));
      IRegistry.a(var0, t, new PaintingVariant(32, 32));
      IRegistry.a(var0, u, new PaintingVariant(64, 32));
      IRegistry.a(var0, v, new PaintingVariant(64, 64));
      IRegistry.a(var0, w, new PaintingVariant(64, 64));
      IRegistry.a(var0, x, new PaintingVariant(64, 64));
      IRegistry.a(var0, y, new PaintingVariant(64, 48));
      IRegistry.a(var0, A, new PaintingVariant(32, 32));
      IRegistry.a(var0, B, new PaintingVariant(32, 32));
      IRegistry.a(var0, C, new PaintingVariant(32, 32));
      IRegistry.a(var0, D, new PaintingVariant(32, 32));
      return IRegistry.a(var0, z, new PaintingVariant(64, 48));
   }

   private static ResourceKey<PaintingVariant> a(String var0) {
      return ResourceKey.a(Registries.O, new MinecraftKey(var0));
   }
}
