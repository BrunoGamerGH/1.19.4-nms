package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public record MangroveRootPlacement(
   HolderSet<Block> canGrowThrough,
   HolderSet<Block> muddyRootsIn,
   WorldGenFeatureStateProvider muddyRootsProvider,
   int maxRootWidth,
   int maxRootLength,
   float randomSkewChance
) {
   private final HolderSet<Block> b;
   private final HolderSet<Block> c;
   private final WorldGenFeatureStateProvider d;
   private final int e;
   private final int f;
   private final float g;
   public static final Codec<MangroveRootPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               RegistryCodecs.a(Registries.e).fieldOf("can_grow_through").forGetter(var0x -> var0x.b),
               RegistryCodecs.a(Registries.e).fieldOf("muddy_roots_in").forGetter(var0x -> var0x.c),
               WorldGenFeatureStateProvider.a.fieldOf("muddy_roots_provider").forGetter(var0x -> var0x.d),
               Codec.intRange(1, 12).fieldOf("max_root_width").forGetter(var0x -> var0x.e),
               Codec.intRange(1, 64).fieldOf("max_root_length").forGetter(var0x -> var0x.f),
               Codec.floatRange(0.0F, 1.0F).fieldOf("random_skew_chance").forGetter(var0x -> var0x.g)
            )
            .apply(var0, MangroveRootPlacement::new)
   );

   public MangroveRootPlacement(HolderSet<Block> var0, HolderSet<Block> var1, WorldGenFeatureStateProvider var2, int var3, int var4, float var5) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
   }

   public HolderSet<Block> a() {
      return this.b;
   }

   public HolderSet<Block> b() {
      return this.c;
   }

   public WorldGenFeatureStateProvider c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }

   public int e() {
      return this.f;
   }

   public float f() {
      return this.g;
   }
}
