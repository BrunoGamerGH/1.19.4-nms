package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator3;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;

public final class BiomeBase {
   public static final Codec<BiomeBase> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               BiomeBase.ClimateSettings.a.forGetter(var0x -> var0x.i),
               BiomeFog.a.fieldOf("effects").forGetter(var0x -> var0x.l),
               BiomeSettingsGeneration.b.forGetter(var0x -> var0x.j),
               BiomeSettingsMobs.c.forGetter(var0x -> var0x.k)
            )
            .apply(var0, BiomeBase::new)
   );
   public static final Codec<BiomeBase> b = RecordCodecBuilder.create(
      var0 -> var0.group(BiomeBase.ClimateSettings.a.forGetter(var0x -> var0x.i), BiomeFog.a.fieldOf("effects").forGetter(var0x -> var0x.l))
            .apply(var0, (var0x, var1) -> new BiomeBase(var0x, var1, BiomeSettingsGeneration.a, BiomeSettingsMobs.b))
   );
   public static final Codec<Holder<BiomeBase>> c = RegistryFileCodec.a(Registries.an, a);
   public static final Codec<HolderSet<BiomeBase>> d = RegistryCodecs.a(Registries.an, a);
   private static final NoiseGenerator3 f = new NoiseGenerator3(new SeededRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));
   static final NoiseGenerator3 g = new NoiseGenerator3(new SeededRandom(new LegacyRandomSource(3456L)), ImmutableList.of(-2, -1, 0));
   @Deprecated(
      forRemoval = true
   )
   public static final NoiseGenerator3 e = new NoiseGenerator3(new SeededRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
   private static final int h = 1024;
   public final BiomeBase.ClimateSettings i;
   private final BiomeSettingsGeneration j;
   private final BiomeSettingsMobs k;
   private final BiomeFog l;
   private final ThreadLocal<Long2FloatLinkedOpenHashMap> m = ThreadLocal.withInitial(() -> SystemUtils.a(() -> {
         Long2FloatLinkedOpenHashMap var0x = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            protected void rehash(int var0) {
            }
         };
         var0x.defaultReturnValue(Float.NaN);
         return var0x;
      }));

   BiomeBase(BiomeBase.ClimateSettings var0, BiomeFog var1, BiomeSettingsGeneration var2, BiomeSettingsMobs var3) {
      this.i = var0;
      this.j = var2;
      this.k = var3;
      this.l = var1;
   }

   public int a() {
      return this.l.d();
   }

   public BiomeSettingsMobs b() {
      return this.k;
   }

   public boolean c() {
      return this.i.a();
   }

   public BiomeBase.Precipitation a(BlockPosition var0) {
      if (!this.c()) {
         return BiomeBase.Precipitation.a;
      } else {
         return this.b(var0) ? BiomeBase.Precipitation.c : BiomeBase.Precipitation.b;
      }
   }

   private float e(BlockPosition var0) {
      float var1 = this.i.d.a(var0, this.g());
      if (var0.v() > 80) {
         float var2 = (float)(f.a((double)((float)var0.u() / 8.0F), (double)((float)var0.w() / 8.0F), false) * 8.0);
         return var1 - (var2 + (float)var0.v() - 80.0F) * 0.05F / 40.0F;
      } else {
         return var1;
      }
   }

   @Deprecated
   public float f(BlockPosition var0) {
      long var1 = var0.a();
      Long2FloatLinkedOpenHashMap var3 = (Long2FloatLinkedOpenHashMap)this.m.get();
      float var4 = var3.get(var1);
      if (!Float.isNaN(var4)) {
         return var4;
      } else {
         float var5 = this.e(var0);
         if (var3.size() == 1024) {
            var3.removeFirstFloat();
         }

         var3.put(var1, var5);
         return var5;
      }
   }

   public boolean a(IWorldReader var0, BlockPosition var1) {
      return this.a(var0, var1, true);
   }

   public boolean a(IWorldReader var0, BlockPosition var1, boolean var2) {
      if (this.c(var1)) {
         return false;
      } else {
         if (var1.v() >= var0.v_() && var1.v() < var0.ai() && var0.a(EnumSkyBlock.b, var1) < 10) {
            IBlockData var3 = var0.a_(var1);
            Fluid var4 = var0.b_(var1);
            if (var4.a() == FluidTypes.c && var3.b() instanceof BlockFluids) {
               if (!var2) {
                  return true;
               }

               boolean var5 = var0.B(var1.g()) && var0.B(var1.h()) && var0.B(var1.e()) && var0.B(var1.f());
               if (!var5) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean b(BlockPosition var0) {
      return !this.c(var0);
   }

   public boolean c(BlockPosition var0) {
      return this.f(var0) >= 0.15F;
   }

   public boolean d(BlockPosition var0) {
      return this.f(var0) > 0.1F;
   }

   public boolean b(IWorldReader var0, BlockPosition var1) {
      if (this.c(var1)) {
         return false;
      } else {
         if (var1.v() >= var0.v_() && var1.v() < var0.ai() && var0.a(EnumSkyBlock.b, var1) < 10) {
            IBlockData var2 = var0.a_(var1);
            if ((var2.h() || var2.a(Blocks.dM)) && Blocks.dM.o().a(var0, var1)) {
               return true;
            }
         }

         return false;
      }
   }

   public BiomeSettingsGeneration d() {
      return this.j;
   }

   public int e() {
      return this.l.a();
   }

   public int a(double var0, double var2) {
      int var4 = this.l.f().orElseGet(this::p);
      return this.l.g().a(var0, var2, var4);
   }

   private int p() {
      double var0 = (double)MathHelper.a(this.i.c, 0.0F, 1.0F);
      double var2 = (double)MathHelper.a(this.i.e, 0.0F, 1.0F);
      return GrassColor.a(var0, var2);
   }

   public int f() {
      return this.l.e().orElseGet(this::q);
   }

   private int q() {
      double var0 = (double)MathHelper.a(this.i.c, 0.0F, 1.0F);
      double var2 = (double)MathHelper.a(this.i.e, 0.0F, 1.0F);
      return FoliageColor.a(var0, var2);
   }

   public float g() {
      return this.i.c;
   }

   public BiomeFog h() {
      return this.l;
   }

   public int i() {
      return this.l.b();
   }

   public int j() {
      return this.l.c();
   }

   public Optional<BiomeParticles> k() {
      return this.l.h();
   }

   public Optional<Holder<SoundEffect>> l() {
      return this.l.i();
   }

   public Optional<CaveSoundSettings> m() {
      return this.l.j();
   }

   public Optional<CaveSound> n() {
      return this.l.k();
   }

   public Optional<Music> o() {
      return this.l.l();
   }

   public static record ClimateSettings(boolean hasPrecipitation, float temperature, BiomeBase.TemperatureModifier temperatureModifier, float downfall) {
      private final boolean b;
      final float c;
      final BiomeBase.TemperatureModifier d;
      final float e;
      public static final MapCodec<BiomeBase.ClimateSettings> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  Codec.BOOL.fieldOf("has_precipitation").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("temperature").forGetter(var0x -> var0x.c),
                  BiomeBase.TemperatureModifier.c.optionalFieldOf("temperature_modifier", BiomeBase.TemperatureModifier.a).forGetter(var0x -> var0x.d),
                  Codec.FLOAT.fieldOf("downfall").forGetter(var0x -> var0x.e)
               )
               .apply(var0, BiomeBase.ClimateSettings::new)
      );

      ClimateSettings(boolean var0, float var1, BiomeBase.TemperatureModifier var2, float var3) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }

      public boolean a() {
         return this.b;
      }

      public float b() {
         return this.c;
      }

      public BiomeBase.TemperatureModifier c() {
         return this.d;
      }

      public float d() {
         return this.e;
      }
   }

   public static enum Precipitation {
      a,
      b,
      c;
   }

   public static enum TemperatureModifier implements INamable {
      a("none") {
         @Override
         public float a(BlockPosition var0, float var1) {
            return var1;
         }
      },
      b("frozen") {
         @Override
         public float a(BlockPosition var0, float var1) {
            double var2 = BiomeBase.g.a((double)var0.u() * 0.05, (double)var0.w() * 0.05, false) * 7.0;
            double var4 = BiomeBase.e.a((double)var0.u() * 0.2, (double)var0.w() * 0.2, false);
            double var6 = var2 + var4;
            if (var6 < 0.3) {
               double var8 = BiomeBase.e.a((double)var0.u() * 0.09, (double)var0.w() * 0.09, false);
               if (var8 < 0.8) {
                  return 0.2F;
               }
            }

            return var1;
         }
      };

      private final String d;
      public static final Codec<BiomeBase.TemperatureModifier> c = INamable.a(BiomeBase.TemperatureModifier::values);

      public abstract float a(BlockPosition var1, float var2);

      TemperatureModifier(String var2) {
         this.d = var2;
      }

      public String a() {
         return this.d;
      }

      @Override
      public String c() {
         return this.d;
      }
   }

   public static class a {
      private boolean a = true;
      @Nullable
      private Float b;
      private BiomeBase.TemperatureModifier c = BiomeBase.TemperatureModifier.a;
      @Nullable
      private Float d;
      @Nullable
      private BiomeFog e;
      @Nullable
      private BiomeSettingsMobs f;
      @Nullable
      private BiomeSettingsGeneration g;

      public BiomeBase.a a(boolean var0) {
         this.a = var0;
         return this;
      }

      public BiomeBase.a a(float var0) {
         this.b = var0;
         return this;
      }

      public BiomeBase.a b(float var0) {
         this.d = var0;
         return this;
      }

      public BiomeBase.a a(BiomeFog var0) {
         this.e = var0;
         return this;
      }

      public BiomeBase.a a(BiomeSettingsMobs var0) {
         this.f = var0;
         return this;
      }

      public BiomeBase.a a(BiomeSettingsGeneration var0) {
         this.g = var0;
         return this;
      }

      public BiomeBase.a a(BiomeBase.TemperatureModifier var0) {
         this.c = var0;
         return this;
      }

      public BiomeBase a() {
         if (this.b != null && this.d != null && this.e != null && this.f != null && this.g != null) {
            return new BiomeBase(new BiomeBase.ClimateSettings(this.a, this.b, this.c, this.d), this.e, this.g, this.f);
         } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
         }
      }

      @Override
      public String toString() {
         return "BiomeBuilder{\nhasPrecipitation="
            + this.a
            + ",\ntemperature="
            + this.b
            + ",\ntemperatureModifier="
            + this.c
            + ",\ndownfall="
            + this.d
            + ",\nspecialEffects="
            + this.e
            + ",\nmobSpawnSettings="
            + this.f
            + ",\ngenerationSettings="
            + this.g
            + ",\n}";
      }
   }
}
