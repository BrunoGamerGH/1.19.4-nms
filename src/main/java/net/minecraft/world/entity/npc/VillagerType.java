package net.minecraft.world.entity.npc;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;

public final class VillagerType {
   public static final VillagerType a = a("desert");
   public static final VillagerType b = a("jungle");
   public static final VillagerType c = a("plains");
   public static final VillagerType d = a("savanna");
   public static final VillagerType e = a("snow");
   public static final VillagerType f = a("swamp");
   public static final VillagerType g = a("taiga");
   private final String h;
   private static final Map<ResourceKey<BiomeBase>, VillagerType> i = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put(Biomes.A, a);
      var0.put(Biomes.f, a);
      var0.put(Biomes.B, a);
      var0.put(Biomes.C, a);
      var0.put(Biomes.z, b);
      var0.put(Biomes.x, b);
      var0.put(Biomes.y, b);
      var0.put(Biomes.s, d);
      var0.put(Biomes.r, d);
      var0.put(Biomes.w, d);
      var0.put(Biomes.X, e);
      var0.put(Biomes.W, e);
      var0.put(Biomes.L, e);
      var0.put(Biomes.e, e);
      var0.put(Biomes.N, e);
      var0.put(Biomes.q, e);
      var0.put(Biomes.d, e);
      var0.put(Biomes.F, e);
      var0.put(Biomes.G, e);
      var0.put(Biomes.H, e);
      var0.put(Biomes.I, e);
      var0.put(Biomes.g, f);
      var0.put(Biomes.h, f);
      var0.put(Biomes.o, g);
      var0.put(Biomes.n, g);
      var0.put(Biomes.u, g);
      var0.put(Biomes.t, g);
      var0.put(Biomes.p, g);
      var0.put(Biomes.v, g);
   });

   private VillagerType(String var0) {
      this.h = var0;
   }

   @Override
   public String toString() {
      return this.h;
   }

   private static VillagerType a(String var0) {
      return IRegistry.a(BuiltInRegistries.y, new MinecraftKey(var0), new VillagerType(var0));
   }

   public static VillagerType a(Holder<BiomeBase> var0) {
      return var0.e().map(i::get).orElse(c);
   }
}
