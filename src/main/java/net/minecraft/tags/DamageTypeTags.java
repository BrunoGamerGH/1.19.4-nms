package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.damagesource.DamageType;

public interface DamageTypeTags {
   TagKey<DamageType> a = a("damages_helmet");
   TagKey<DamageType> b = a("bypasses_armor");
   TagKey<DamageType> c = a("bypasses_shield");
   TagKey<DamageType> d = a("bypasses_invulnerability");
   TagKey<DamageType> e = a("bypasses_cooldown");
   TagKey<DamageType> f = a("bypasses_effects");
   TagKey<DamageType> g = a("bypasses_resistance");
   TagKey<DamageType> h = a("bypasses_enchantments");
   TagKey<DamageType> i = a("is_fire");
   TagKey<DamageType> j = a("is_projectile");
   TagKey<DamageType> k = a("witch_resistant_to");
   TagKey<DamageType> l = a("is_explosion");
   TagKey<DamageType> m = a("is_fall");
   TagKey<DamageType> n = a("is_drowning");
   TagKey<DamageType> o = a("is_freezing");
   TagKey<DamageType> p = a("is_lightning");
   TagKey<DamageType> q = a("no_anger");
   TagKey<DamageType> r = a("no_impact");
   TagKey<DamageType> s = a("always_most_significant_fall");
   TagKey<DamageType> t = a("wither_immune_to");
   TagKey<DamageType> u = a("ignites_armor_stands");
   TagKey<DamageType> v = a("burns_armor_stands");
   TagKey<DamageType> w = a("avoids_guardian_thorns");
   TagKey<DamageType> x = a("always_triggers_silverfish");
   TagKey<DamageType> y = a("always_hurts_ender_dragons");

   private static TagKey<DamageType> a(String var0) {
      return TagKey.a(Registries.o, new MinecraftKey(var0));
   }
}
