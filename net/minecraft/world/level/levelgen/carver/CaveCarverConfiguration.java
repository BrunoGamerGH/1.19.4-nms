package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CaveCarverConfiguration extends WorldGenCarverConfiguration {
   public static final Codec<CaveCarverConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenCarverConfiguration.d.forGetter(var0x -> var0x),
               FloatProvider.c.fieldOf("horizontal_radius_multiplier").forGetter(var0x -> var0x.b),
               FloatProvider.c.fieldOf("vertical_radius_multiplier").forGetter(var0x -> var0x.c),
               FloatProvider.a(-1.0F, 1.0F).fieldOf("floor_level").forGetter(var0x -> var0x.j)
            )
            .apply(var0, CaveCarverConfiguration::new)
   );
   public final FloatProvider b;
   public final FloatProvider c;
   final FloatProvider j;

   public CaveCarverConfiguration(
      float var0,
      HeightProvider var1,
      FloatProvider var2,
      VerticalAnchor var3,
      CarverDebugSettings var4,
      HolderSet<Block> var5,
      FloatProvider var6,
      FloatProvider var7,
      FloatProvider var8
   ) {
      super(var0, var1, var2, var3, var4, var5);
      this.b = var6;
      this.c = var7;
      this.j = var8;
   }

   public CaveCarverConfiguration(
      float var0,
      HeightProvider var1,
      FloatProvider var2,
      VerticalAnchor var3,
      HolderSet<Block> var4,
      FloatProvider var5,
      FloatProvider var6,
      FloatProvider var7
   ) {
      this(var0, var1, var2, var3, CarverDebugSettings.a, var4, var5, var6, var7);
   }

   public CaveCarverConfiguration(WorldGenCarverConfiguration var0, FloatProvider var1, FloatProvider var2, FloatProvider var3) {
      this(var0.l, var0.e, var0.f, var0.g, var0.h, var0.i, var1, var2, var3);
   }
}
