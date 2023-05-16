package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;

public class BannerPatternTags {
   public static final TagKey<EnumBannerPatternType> a = a("no_item_required");
   public static final TagKey<EnumBannerPatternType> b = a("pattern_item/flower");
   public static final TagKey<EnumBannerPatternType> c = a("pattern_item/creeper");
   public static final TagKey<EnumBannerPatternType> d = a("pattern_item/skull");
   public static final TagKey<EnumBannerPatternType> e = a("pattern_item/mojang");
   public static final TagKey<EnumBannerPatternType> f = a("pattern_item/globe");
   public static final TagKey<EnumBannerPatternType> g = a("pattern_item/piglin");

   private BannerPatternTags() {
   }

   private static TagKey<EnumBannerPatternType> a(String var0) {
      return TagKey.a(Registries.c, new MinecraftKey(var0));
   }
}
