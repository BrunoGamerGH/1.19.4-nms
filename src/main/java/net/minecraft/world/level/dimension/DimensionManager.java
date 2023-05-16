package net.minecraft.world.level.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.nio.file.Path;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;

public record DimensionManager(
   OptionalLong fixedTime,
   boolean hasSkyLight,
   boolean hasCeiling,
   boolean ultraWarm,
   boolean natural,
   double coordinateScale,
   boolean bedWorks,
   boolean respawnAnchorWorks,
   int minY,
   int height,
   int logicalHeight,
   TagKey<Block> infiniburn,
   MinecraftKey effectsLocation,
   float ambientLight,
   DimensionManager.a monsterSettings
) {
   private final OptionalLong k;
   private final boolean l;
   private final boolean m;
   private final boolean n;
   private final boolean o;
   private final double p;
   private final boolean q;
   private final boolean r;
   private final int s;
   private final int t;
   private final int u;
   private final TagKey<Block> v;
   private final MinecraftKey w;
   private final float x;
   private final DimensionManager.a y;
   public static final int a = BlockPosition.c;
   public static final int b = 16;
   public static final int c = (1 << a) - 32;
   public static final int d = (c >> 1) - 1;
   public static final int e = d - c + 1;
   public static final int f = d << 4;
   public static final int g = e << 4;
   public static final Codec<DimensionManager> h = ExtraCodecs.c(
      RecordCodecBuilder.create(
         var0 -> var0.group(
                  ExtraCodecs.a(Codec.LONG.optionalFieldOf("fixed_time")).forGetter(DimensionManager::f),
                  Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionManager::g),
                  Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionManager::h),
                  Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionManager::i),
                  Codec.BOOL.fieldOf("natural").forGetter(DimensionManager::j),
                  Codec.doubleRange(1.0E-5F, 3.0E7).fieldOf("coordinate_scale").forGetter(DimensionManager::k),
                  Codec.BOOL.fieldOf("bed_works").forGetter(DimensionManager::l),
                  Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionManager::m),
                  Codec.intRange(e, d).fieldOf("min_y").forGetter(DimensionManager::n),
                  Codec.intRange(16, c).fieldOf("height").forGetter(DimensionManager::o),
                  Codec.intRange(0, c).fieldOf("logical_height").forGetter(DimensionManager::p),
                  TagKey.b(Registries.e).fieldOf("infiniburn").forGetter(DimensionManager::q),
                  MinecraftKey.a.fieldOf("effects").orElse(BuiltinDimensionTypes.e).forGetter(DimensionManager::r),
                  Codec.FLOAT.fieldOf("ambient_light").forGetter(DimensionManager::s),
                  DimensionManager.a.a.forGetter(DimensionManager::t)
               )
               .apply(var0, DimensionManager::new)
      )
   );
   private static final int z = 8;
   public static final float[] i = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public static final Codec<Holder<DimensionManager>> j = RegistryFileCodec.a(Registries.as, h);

   public DimensionManager(
      OptionalLong var0,
      boolean var1,
      boolean var2,
      boolean var3,
      boolean var4,
      double var5,
      boolean var7,
      boolean var8,
      int var9,
      int var10,
      int var11,
      TagKey<Block> var12,
      MinecraftKey var13,
      float var14,
      DimensionManager.a var15
   ) {
      if (var10 < 16) {
         throw new IllegalStateException("height has to be at least 16");
      } else if (var9 + var10 > d + 1) {
         throw new IllegalStateException("min_y + height cannot be higher than: " + (d + 1));
      } else if (var11 > var10) {
         throw new IllegalStateException("logical_height cannot be higher than height");
      } else if (var10 % 16 != 0) {
         throw new IllegalStateException("height has to be multiple of 16");
      } else if (var9 % 16 != 0) {
         throw new IllegalStateException("min_y has to be a multiple of 16");
      } else {
         this.k = var0;
         this.l = var1;
         this.m = var2;
         this.n = var3;
         this.o = var4;
         this.p = var5;
         this.q = var7;
         this.r = var8;
         this.s = var9;
         this.t = var10;
         this.u = var11;
         this.v = var12;
         this.w = var13;
         this.x = var14;
         this.y = var15;
      }
   }

   @Deprecated
   public static DataResult<ResourceKey<World>> a(Dynamic<?> var0) {
      Optional<Number> var1 = var0.asNumber().result();
      if (var1.isPresent()) {
         int var2 = var1.get().intValue();
         if (var2 == -1) {
            return DataResult.success(World.i);
         }

         if (var2 == 0) {
            return DataResult.success(World.h);
         }

         if (var2 == 1) {
            return DataResult.success(World.j);
         }
      }

      return World.g.parse(var0);
   }

   public static double a(DimensionManager var0, DimensionManager var1) {
      double var2 = var0.k();
      double var4 = var1.k();
      return var2 / var4;
   }

   public static Path a(ResourceKey<World> var0, Path var1) {
      if (var0 == World.h) {
         return var1;
      } else if (var0 == World.j) {
         return var1.resolve("DIM1");
      } else {
         return var0 == World.i ? var1.resolve("DIM-1") : var1.resolve("dimensions").resolve(var0.a().b()).resolve(var0.a().a());
      }
   }

   public boolean a() {
      return this.k.isPresent();
   }

   public float a(long var0) {
      double var2 = MathHelper.e((double)this.k.orElse(var0) / 24000.0 - 0.25);
      double var4 = 0.5 - Math.cos(var2 * Math.PI) / 2.0;
      return (float)(var2 * 2.0 + var4) / 3.0F;
   }

   public int b(long var0) {
      return (int)(var0 / 24000L % 8L + 8L) % 8;
   }

   public boolean b() {
      return this.y.a();
   }

   public boolean c() {
      return this.y.b();
   }

   public IntProvider d() {
      return this.y.c();
   }

   public int e() {
      return this.y.d();
   }

   public OptionalLong f() {
      return this.k;
   }

   public boolean g() {
      return this.l;
   }

   public boolean h() {
      return this.m;
   }

   public boolean i() {
      return this.n;
   }

   public boolean j() {
      return this.o;
   }

   public double k() {
      return this.p;
   }

   public boolean l() {
      return this.q;
   }

   public boolean m() {
      return this.r;
   }

   public int n() {
      return this.s;
   }

   public int o() {
      return this.t;
   }

   public int p() {
      return this.u;
   }

   public TagKey<Block> q() {
      return this.v;
   }

   public MinecraftKey r() {
      return this.w;
   }

   public float s() {
      return this.x;
   }

   public DimensionManager.a t() {
      return this.y;
   }

   public static record a(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
      private final boolean b;
      private final boolean c;
      private final IntProvider d;
      private final int e;
      public static final MapCodec<DimensionManager.a> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  Codec.BOOL.fieldOf("piglin_safe").forGetter(DimensionManager.a::a),
                  Codec.BOOL.fieldOf("has_raids").forGetter(DimensionManager.a::b),
                  IntProvider.b(0, 15).fieldOf("monster_spawn_light_level").forGetter(DimensionManager.a::c),
                  Codec.intRange(0, 15).fieldOf("monster_spawn_block_light_limit").forGetter(DimensionManager.a::d)
               )
               .apply(var0, DimensionManager.a::new)
      );

      public a(boolean var0, boolean var1, IntProvider var2, int var3) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }

      public boolean a() {
         return this.b;
      }

      public boolean b() {
         return this.c;
      }

      public IntProvider c() {
         return this.d;
      }

      public int d() {
         return this.e;
      }
   }
}
