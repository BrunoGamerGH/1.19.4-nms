package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.animal.CatVariant;

public class CatVariantTags {
   public static final TagKey<CatVariant> a = a("default_spawns");
   public static final TagKey<CatVariant> b = a("full_moon_spawns");

   private CatVariantTags() {
   }

   private static TagKey<CatVariant> a(String var0) {
      return TagKey.a(Registries.j, new MinecraftKey(var0));
   }
}
