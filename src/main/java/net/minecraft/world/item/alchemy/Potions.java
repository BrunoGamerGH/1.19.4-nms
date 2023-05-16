package net.minecraft.world.item.alchemy;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class Potions {
   public static ResourceKey<PotionRegistry> a = ResourceKey.a(Registries.U, new MinecraftKey("empty"));
   public static final PotionRegistry b = a(a, new PotionRegistry());
   public static final PotionRegistry c = a("water", new PotionRegistry());
   public static final PotionRegistry d = a("mundane", new PotionRegistry());
   public static final PotionRegistry e = a("thick", new PotionRegistry());
   public static final PotionRegistry f = a("awkward", new PotionRegistry());
   public static final PotionRegistry g = a("night_vision", new PotionRegistry(new MobEffect(MobEffects.p, 3600)));
   public static final PotionRegistry h = a("long_night_vision", new PotionRegistry("night_vision", new MobEffect(MobEffects.p, 9600)));
   public static final PotionRegistry i = a("invisibility", new PotionRegistry(new MobEffect(MobEffects.n, 3600)));
   public static final PotionRegistry j = a("long_invisibility", new PotionRegistry("invisibility", new MobEffect(MobEffects.n, 9600)));
   public static final PotionRegistry k = a("leaping", new PotionRegistry(new MobEffect(MobEffects.h, 3600)));
   public static final PotionRegistry l = a("long_leaping", new PotionRegistry("leaping", new MobEffect(MobEffects.h, 9600)));
   public static final PotionRegistry m = a("strong_leaping", new PotionRegistry("leaping", new MobEffect(MobEffects.h, 1800, 1)));
   public static final PotionRegistry n = a("fire_resistance", new PotionRegistry(new MobEffect(MobEffects.l, 3600)));
   public static final PotionRegistry o = a("long_fire_resistance", new PotionRegistry("fire_resistance", new MobEffect(MobEffects.l, 9600)));
   public static final PotionRegistry p = a("swiftness", new PotionRegistry(new MobEffect(MobEffects.a, 3600)));
   public static final PotionRegistry q = a("long_swiftness", new PotionRegistry("swiftness", new MobEffect(MobEffects.a, 9600)));
   public static final PotionRegistry r = a("strong_swiftness", new PotionRegistry("swiftness", new MobEffect(MobEffects.a, 1800, 1)));
   public static final PotionRegistry s = a("slowness", new PotionRegistry(new MobEffect(MobEffects.b, 1800)));
   public static final PotionRegistry t = a("long_slowness", new PotionRegistry("slowness", new MobEffect(MobEffects.b, 4800)));
   public static final PotionRegistry u = a("strong_slowness", new PotionRegistry("slowness", new MobEffect(MobEffects.b, 400, 3)));
   public static final PotionRegistry v = a(
      "turtle_master", new PotionRegistry("turtle_master", new MobEffect(MobEffects.b, 400, 3), new MobEffect(MobEffects.k, 400, 2))
   );
   public static final PotionRegistry w = a(
      "long_turtle_master", new PotionRegistry("turtle_master", new MobEffect(MobEffects.b, 800, 3), new MobEffect(MobEffects.k, 800, 2))
   );
   public static final PotionRegistry x = a(
      "strong_turtle_master", new PotionRegistry("turtle_master", new MobEffect(MobEffects.b, 400, 5), new MobEffect(MobEffects.k, 400, 3))
   );
   public static final PotionRegistry y = a("water_breathing", new PotionRegistry(new MobEffect(MobEffects.m, 3600)));
   public static final PotionRegistry z = a("long_water_breathing", new PotionRegistry("water_breathing", new MobEffect(MobEffects.m, 9600)));
   public static final PotionRegistry A = a("healing", new PotionRegistry(new MobEffect(MobEffects.f, 1)));
   public static final PotionRegistry B = a("strong_healing", new PotionRegistry("healing", new MobEffect(MobEffects.f, 1, 1)));
   public static final PotionRegistry C = a("harming", new PotionRegistry(new MobEffect(MobEffects.g, 1)));
   public static final PotionRegistry D = a("strong_harming", new PotionRegistry("harming", new MobEffect(MobEffects.g, 1, 1)));
   public static final PotionRegistry E = a("poison", new PotionRegistry(new MobEffect(MobEffects.s, 900)));
   public static final PotionRegistry F = a("long_poison", new PotionRegistry("poison", new MobEffect(MobEffects.s, 1800)));
   public static final PotionRegistry G = a("strong_poison", new PotionRegistry("poison", new MobEffect(MobEffects.s, 432, 1)));
   public static final PotionRegistry H = a("regeneration", new PotionRegistry(new MobEffect(MobEffects.j, 900)));
   public static final PotionRegistry I = a("long_regeneration", new PotionRegistry("regeneration", new MobEffect(MobEffects.j, 1800)));
   public static final PotionRegistry J = a("strong_regeneration", new PotionRegistry("regeneration", new MobEffect(MobEffects.j, 450, 1)));
   public static final PotionRegistry K = a("strength", new PotionRegistry(new MobEffect(MobEffects.e, 3600)));
   public static final PotionRegistry L = a("long_strength", new PotionRegistry("strength", new MobEffect(MobEffects.e, 9600)));
   public static final PotionRegistry M = a("strong_strength", new PotionRegistry("strength", new MobEffect(MobEffects.e, 1800, 1)));
   public static final PotionRegistry N = a("weakness", new PotionRegistry(new MobEffect(MobEffects.r, 1800)));
   public static final PotionRegistry O = a("long_weakness", new PotionRegistry("weakness", new MobEffect(MobEffects.r, 4800)));
   public static final PotionRegistry P = a("luck", new PotionRegistry("luck", new MobEffect(MobEffects.z, 6000)));
   public static final PotionRegistry Q = a("slow_falling", new PotionRegistry(new MobEffect(MobEffects.B, 1800)));
   public static final PotionRegistry R = a("long_slow_falling", new PotionRegistry("slow_falling", new MobEffect(MobEffects.B, 4800)));

   private static PotionRegistry a(String var0, PotionRegistry var1) {
      return IRegistry.a(BuiltInRegistries.j, var0, var1);
   }

   private static PotionRegistry a(ResourceKey<PotionRegistry> var0, PotionRegistry var1) {
      return IRegistry.a(BuiltInRegistries.j, var0, var1);
   }
}
