package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingVariantTags {
   public static final TagKey<PaintingVariant> a = a("placeable");

   private PaintingVariantTags() {
   }

   private static TagKey<PaintingVariant> a(String var0) {
      return TagKey.a(Registries.O, new MinecraftKey(var0));
   }
}
