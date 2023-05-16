package net.minecraft.world.level.block.entity;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public class BannerPatterns {
   public static final ResourceKey<EnumBannerPatternType> a = a("base");
   public static final ResourceKey<EnumBannerPatternType> b = a("square_bottom_left");
   public static final ResourceKey<EnumBannerPatternType> c = a("square_bottom_right");
   public static final ResourceKey<EnumBannerPatternType> d = a("square_top_left");
   public static final ResourceKey<EnumBannerPatternType> e = a("square_top_right");
   public static final ResourceKey<EnumBannerPatternType> f = a("stripe_bottom");
   public static final ResourceKey<EnumBannerPatternType> g = a("stripe_top");
   public static final ResourceKey<EnumBannerPatternType> h = a("stripe_left");
   public static final ResourceKey<EnumBannerPatternType> i = a("stripe_right");
   public static final ResourceKey<EnumBannerPatternType> j = a("stripe_center");
   public static final ResourceKey<EnumBannerPatternType> k = a("stripe_middle");
   public static final ResourceKey<EnumBannerPatternType> l = a("stripe_downright");
   public static final ResourceKey<EnumBannerPatternType> m = a("stripe_downleft");
   public static final ResourceKey<EnumBannerPatternType> n = a("small_stripes");
   public static final ResourceKey<EnumBannerPatternType> o = a("cross");
   public static final ResourceKey<EnumBannerPatternType> p = a("straight_cross");
   public static final ResourceKey<EnumBannerPatternType> q = a("triangle_bottom");
   public static final ResourceKey<EnumBannerPatternType> r = a("triangle_top");
   public static final ResourceKey<EnumBannerPatternType> s = a("triangles_bottom");
   public static final ResourceKey<EnumBannerPatternType> t = a("triangles_top");
   public static final ResourceKey<EnumBannerPatternType> u = a("diagonal_left");
   public static final ResourceKey<EnumBannerPatternType> v = a("diagonal_up_right");
   public static final ResourceKey<EnumBannerPatternType> w = a("diagonal_up_left");
   public static final ResourceKey<EnumBannerPatternType> x = a("diagonal_right");
   public static final ResourceKey<EnumBannerPatternType> y = a("circle");
   public static final ResourceKey<EnumBannerPatternType> z = a("rhombus");
   public static final ResourceKey<EnumBannerPatternType> A = a("half_vertical");
   public static final ResourceKey<EnumBannerPatternType> B = a("half_horizontal");
   public static final ResourceKey<EnumBannerPatternType> C = a("half_vertical_right");
   public static final ResourceKey<EnumBannerPatternType> D = a("half_horizontal_bottom");
   public static final ResourceKey<EnumBannerPatternType> E = a("border");
   public static final ResourceKey<EnumBannerPatternType> F = a("curly_border");
   public static final ResourceKey<EnumBannerPatternType> G = a("gradient");
   public static final ResourceKey<EnumBannerPatternType> H = a("gradient_up");
   public static final ResourceKey<EnumBannerPatternType> I = a("bricks");
   public static final ResourceKey<EnumBannerPatternType> J = a("globe");
   public static final ResourceKey<EnumBannerPatternType> K = a("creeper");
   public static final ResourceKey<EnumBannerPatternType> L = a("skull");
   public static final ResourceKey<EnumBannerPatternType> M = a("flower");
   public static final ResourceKey<EnumBannerPatternType> N = a("mojang");
   public static final ResourceKey<EnumBannerPatternType> O = a("piglin");

   private static ResourceKey<EnumBannerPatternType> a(String var0) {
      return ResourceKey.a(Registries.c, new MinecraftKey(var0));
   }

   public static EnumBannerPatternType a(IRegistry<EnumBannerPatternType> var0) {
      IRegistry.a(var0, a, new EnumBannerPatternType("b"));
      IRegistry.a(var0, b, new EnumBannerPatternType("bl"));
      IRegistry.a(var0, c, new EnumBannerPatternType("br"));
      IRegistry.a(var0, d, new EnumBannerPatternType("tl"));
      IRegistry.a(var0, e, new EnumBannerPatternType("tr"));
      IRegistry.a(var0, f, new EnumBannerPatternType("bs"));
      IRegistry.a(var0, g, new EnumBannerPatternType("ts"));
      IRegistry.a(var0, h, new EnumBannerPatternType("ls"));
      IRegistry.a(var0, i, new EnumBannerPatternType("rs"));
      IRegistry.a(var0, j, new EnumBannerPatternType("cs"));
      IRegistry.a(var0, k, new EnumBannerPatternType("ms"));
      IRegistry.a(var0, l, new EnumBannerPatternType("drs"));
      IRegistry.a(var0, m, new EnumBannerPatternType("dls"));
      IRegistry.a(var0, n, new EnumBannerPatternType("ss"));
      IRegistry.a(var0, o, new EnumBannerPatternType("cr"));
      IRegistry.a(var0, p, new EnumBannerPatternType("sc"));
      IRegistry.a(var0, q, new EnumBannerPatternType("bt"));
      IRegistry.a(var0, r, new EnumBannerPatternType("tt"));
      IRegistry.a(var0, s, new EnumBannerPatternType("bts"));
      IRegistry.a(var0, t, new EnumBannerPatternType("tts"));
      IRegistry.a(var0, u, new EnumBannerPatternType("ld"));
      IRegistry.a(var0, v, new EnumBannerPatternType("rd"));
      IRegistry.a(var0, w, new EnumBannerPatternType("lud"));
      IRegistry.a(var0, x, new EnumBannerPatternType("rud"));
      IRegistry.a(var0, y, new EnumBannerPatternType("mc"));
      IRegistry.a(var0, z, new EnumBannerPatternType("mr"));
      IRegistry.a(var0, A, new EnumBannerPatternType("vh"));
      IRegistry.a(var0, B, new EnumBannerPatternType("hh"));
      IRegistry.a(var0, C, new EnumBannerPatternType("vhr"));
      IRegistry.a(var0, D, new EnumBannerPatternType("hhb"));
      IRegistry.a(var0, E, new EnumBannerPatternType("bo"));
      IRegistry.a(var0, F, new EnumBannerPatternType("cbo"));
      IRegistry.a(var0, G, new EnumBannerPatternType("gra"));
      IRegistry.a(var0, H, new EnumBannerPatternType("gru"));
      IRegistry.a(var0, I, new EnumBannerPatternType("bri"));
      IRegistry.a(var0, J, new EnumBannerPatternType("glb"));
      IRegistry.a(var0, K, new EnumBannerPatternType("cre"));
      IRegistry.a(var0, L, new EnumBannerPatternType("sku"));
      IRegistry.a(var0, M, new EnumBannerPatternType("flo"));
      IRegistry.a(var0, N, new EnumBannerPatternType("moj"));
      return IRegistry.a(var0, O, new EnumBannerPatternType("pig"));
   }
}
