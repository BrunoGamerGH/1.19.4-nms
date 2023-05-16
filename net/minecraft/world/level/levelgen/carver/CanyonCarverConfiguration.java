package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CanyonCarverConfiguration extends WorldGenCarverConfiguration {
   public static final Codec<CanyonCarverConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenCarverConfiguration.d.forGetter(var0x -> var0x),
               FloatProvider.c.fieldOf("vertical_rotation").forGetter(var0x -> var0x.b),
               CanyonCarverConfiguration.a.a.fieldOf("shape").forGetter(var0x -> var0x.c)
            )
            .apply(var0, CanyonCarverConfiguration::new)
   );
   public final FloatProvider b;
   public final CanyonCarverConfiguration.a c;

   public CanyonCarverConfiguration(
      float var0,
      HeightProvider var1,
      FloatProvider var2,
      VerticalAnchor var3,
      CarverDebugSettings var4,
      HolderSet<Block> var5,
      FloatProvider var6,
      CanyonCarverConfiguration.a var7
   ) {
      super(var0, var1, var2, var3, var4, var5);
      this.b = var6;
      this.c = var7;
   }

   public CanyonCarverConfiguration(WorldGenCarverConfiguration var0, FloatProvider var1, CanyonCarverConfiguration.a var2) {
      this(var0.l, var0.e, var0.f, var0.g, var0.h, var0.i, var1, var2);
   }

   public static class a {
      public static final Codec<CanyonCarverConfiguration.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  FloatProvider.c.fieldOf("distance_factor").forGetter(var0x -> var0x.b),
                  FloatProvider.c.fieldOf("thickness").forGetter(var0x -> var0x.c),
                  ExtraCodecs.h.fieldOf("width_smoothness").forGetter(var0x -> var0x.d),
                  FloatProvider.c.fieldOf("horizontal_radius_factor").forGetter(var0x -> var0x.e),
                  Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter(var0x -> var0x.f),
                  Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter(var0x -> var0x.g)
               )
               .apply(var0, CanyonCarverConfiguration.a::new)
      );
      public final FloatProvider b;
      public final FloatProvider c;
      public final int d;
      public final FloatProvider e;
      public final float f;
      public final float g;

      public a(FloatProvider var0, FloatProvider var1, int var2, FloatProvider var3, float var4, float var5) {
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = var5;
         this.b = var0;
         this.c = var1;
      }
   }
}
