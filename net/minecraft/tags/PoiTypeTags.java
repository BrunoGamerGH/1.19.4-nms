package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;

public class PoiTypeTags {
   public static final TagKey<VillagePlaceType> a = a("acquirable_job_site");
   public static final TagKey<VillagePlaceType> b = a("village");
   public static final TagKey<VillagePlaceType> c = a("bee_home");

   private PoiTypeTags() {
   }

   private static TagKey<VillagePlaceType> a(String var0) {
      return TagKey.a(Registries.R, new MinecraftKey(var0));
   }
}
