package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.INamable;

public class BiomeFog {
   public static final Codec<BiomeFog> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("fog_color").forGetter(var0x -> var0x.b),
               Codec.INT.fieldOf("water_color").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("water_fog_color").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("sky_color").forGetter(var0x -> var0x.e),
               Codec.INT.optionalFieldOf("foliage_color").forGetter(var0x -> var0x.f),
               Codec.INT.optionalFieldOf("grass_color").forGetter(var0x -> var0x.g),
               BiomeFog.GrassColor.d.optionalFieldOf("grass_color_modifier", BiomeFog.GrassColor.a).forGetter(var0x -> var0x.h),
               BiomeParticles.a.optionalFieldOf("particle").forGetter(var0x -> var0x.i),
               SoundEffect.b.optionalFieldOf("ambient_sound").forGetter(var0x -> var0x.j),
               CaveSoundSettings.a.optionalFieldOf("mood_sound").forGetter(var0x -> var0x.k),
               CaveSound.a.optionalFieldOf("additions_sound").forGetter(var0x -> var0x.l),
               Music.a.optionalFieldOf("music").forGetter(var0x -> var0x.m)
            )
            .apply(var0, BiomeFog::new)
   );
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final Optional<Integer> f;
   private final Optional<Integer> g;
   private final BiomeFog.GrassColor h;
   private final Optional<BiomeParticles> i;
   private final Optional<Holder<SoundEffect>> j;
   private final Optional<CaveSoundSettings> k;
   private final Optional<CaveSound> l;
   private final Optional<Music> m;

   BiomeFog(
      int var0,
      int var1,
      int var2,
      int var3,
      Optional<Integer> var4,
      Optional<Integer> var5,
      BiomeFog.GrassColor var6,
      Optional<BiomeParticles> var7,
      Optional<Holder<SoundEffect>> var8,
      Optional<CaveSoundSettings> var9,
      Optional<CaveSound> var10,
      Optional<Music> var11
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
      this.k = var9;
      this.l = var10;
      this.m = var11;
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }

   public Optional<Integer> e() {
      return this.f;
   }

   public Optional<Integer> f() {
      return this.g;
   }

   public BiomeFog.GrassColor g() {
      return this.h;
   }

   public Optional<BiomeParticles> h() {
      return this.i;
   }

   public Optional<Holder<SoundEffect>> i() {
      return this.j;
   }

   public Optional<CaveSoundSettings> j() {
      return this.k;
   }

   public Optional<CaveSound> k() {
      return this.l;
   }

   public Optional<Music> l() {
      return this.m;
   }

   public static enum GrassColor implements INamable {
      a("none") {
         @Override
         public int a(double var0, double var2, int var4) {
            return var4;
         }
      },
      b("dark_forest") {
         @Override
         public int a(double var0, double var2, int var4) {
            return (var4 & 16711422) + 2634762 >> 1;
         }
      },
      c("swamp") {
         @Override
         public int a(double var0, double var2, int var4) {
            double var5 = BiomeBase.e.a(var0 * 0.0225, var2 * 0.0225, false);
            return var5 < -0.1 ? 5011004 : 6975545;
         }
      };

      private final String e;
      public static final Codec<BiomeFog.GrassColor> d = INamable.a(BiomeFog.GrassColor::values);

      public abstract int a(double var1, double var3, int var5);

      GrassColor(String var2) {
         this.e = var2;
      }

      public String a() {
         return this.e;
      }

      @Override
      public String c() {
         return this.e;
      }
   }

   public static class a {
      private OptionalInt a = OptionalInt.empty();
      private OptionalInt b = OptionalInt.empty();
      private OptionalInt c = OptionalInt.empty();
      private OptionalInt d = OptionalInt.empty();
      private Optional<Integer> e = Optional.empty();
      private Optional<Integer> f = Optional.empty();
      private BiomeFog.GrassColor g = BiomeFog.GrassColor.a;
      private Optional<BiomeParticles> h = Optional.empty();
      private Optional<Holder<SoundEffect>> i = Optional.empty();
      private Optional<CaveSoundSettings> j = Optional.empty();
      private Optional<CaveSound> k = Optional.empty();
      private Optional<Music> l = Optional.empty();

      public BiomeFog.a a(int var0) {
         this.a = OptionalInt.of(var0);
         return this;
      }

      public BiomeFog.a b(int var0) {
         this.b = OptionalInt.of(var0);
         return this;
      }

      public BiomeFog.a c(int var0) {
         this.c = OptionalInt.of(var0);
         return this;
      }

      public BiomeFog.a d(int var0) {
         this.d = OptionalInt.of(var0);
         return this;
      }

      public BiomeFog.a e(int var0) {
         this.e = Optional.of(var0);
         return this;
      }

      public BiomeFog.a f(int var0) {
         this.f = Optional.of(var0);
         return this;
      }

      public BiomeFog.a a(BiomeFog.GrassColor var0) {
         this.g = var0;
         return this;
      }

      public BiomeFog.a a(BiomeParticles var0) {
         this.h = Optional.of(var0);
         return this;
      }

      public BiomeFog.a a(Holder<SoundEffect> var0) {
         this.i = Optional.of(var0);
         return this;
      }

      public BiomeFog.a a(CaveSoundSettings var0) {
         this.j = Optional.of(var0);
         return this;
      }

      public BiomeFog.a a(CaveSound var0) {
         this.k = Optional.of(var0);
         return this;
      }

      public BiomeFog.a a(@Nullable Music var0) {
         this.l = Optional.ofNullable(var0);
         return this;
      }

      public BiomeFog a() {
         return new BiomeFog(
            this.a.orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")),
            this.b.orElseThrow(() -> new IllegalStateException("Missing 'water' color.")),
            this.c.orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")),
            this.d.orElseThrow(() -> new IllegalStateException("Missing 'sky' color.")),
            this.e,
            this.f,
            this.g,
            this.h,
            this.i,
            this.j,
            this.k,
            this.l
         );
      }
   }
}
