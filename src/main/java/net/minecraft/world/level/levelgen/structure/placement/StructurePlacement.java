package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.datafixers.Products.P5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Optional;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.INamable;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public abstract class StructurePlacement {
   public static final Codec<StructurePlacement> b = BuiltInRegistries.R.q().dispatch(StructurePlacement::e, StructurePlacementType::codec);
   private static final int a = 10387320;
   public final BaseBlockPosition c;
   public final StructurePlacement.c d;
   public final float e;
   public final int f;
   public final Optional<StructurePlacement.a> g;

   protected static <S extends StructurePlacement> P5<Mu<S>, BaseBlockPosition, StructurePlacement.c, Float, Integer, Optional<StructurePlacement.a>> a(
      Instance<S> var0
   ) {
      return var0.group(
         BaseBlockPosition.v(16).optionalFieldOf("locate_offset", BaseBlockPosition.g).forGetter(StructurePlacement::f),
         StructurePlacement.c.e.optionalFieldOf("frequency_reduction_method", StructurePlacement.c.a).forGetter(StructurePlacement::g),
         Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(StructurePlacement::h),
         ExtraCodecs.h.fieldOf("salt").forGetter(StructurePlacement::i),
         StructurePlacement.a.a.optionalFieldOf("exclusion_zone").forGetter(StructurePlacement::j)
      );
   }

   protected StructurePlacement(BaseBlockPosition var0, StructurePlacement.c var1, float var2, int var3, Optional<StructurePlacement.a> var4) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
   }

   protected BaseBlockPosition f() {
      return this.c;
   }

   protected StructurePlacement.c g() {
      return this.d;
   }

   protected float h() {
      return this.e;
   }

   protected int i() {
      return this.f;
   }

   protected Optional<StructurePlacement.a> j() {
      return this.g;
   }

   public boolean b(ChunkGeneratorStructureState var0, int var1, int var2) {
      if (!this.a(var0, var1, var2)) {
         return false;
      } else if (this.e < 1.0F && !this.d.a(var0.d(), this.f, var1, var2, this.e)) {
         return false;
      } else {
         return !this.g.isPresent() || !this.g.get().a(var0, var1, var2);
      }
   }

   protected abstract boolean a(ChunkGeneratorStructureState var1, int var2, int var3);

   public BlockPosition a(ChunkCoordIntPair var0) {
      return new BlockPosition(var0.d(), 0, var0.e()).a(this.f());
   }

   public abstract StructurePlacementType<?> e();

   private static boolean a(long var0, int var2, int var3, int var4, float var5) {
      SeededRandom var6 = new SeededRandom(new LegacyRandomSource(0L));
      var6.a(var0, var2, var3, var4);
      return var6.i() < var5;
   }

   private static boolean b(long var0, int var2, int var3, int var4, float var5) {
      SeededRandom var6 = new SeededRandom(new LegacyRandomSource(0L));
      var6.c(var0, var3, var4);
      return var6.j() < (double)var5;
   }

   private static boolean c(long var0, int var2, int var3, int var4, float var5) {
      SeededRandom var6 = new SeededRandom(new LegacyRandomSource(0L));
      var6.a(var0, var3, var4, 10387320);
      return var6.i() < var5;
   }

   private static boolean d(long var0, int var2, int var3, int var4, float var5) {
      int var6 = var3 >> 4;
      int var7 = var4 >> 4;
      SeededRandom var8 = new SeededRandom(new LegacyRandomSource(0L));
      var8.b((long)(var6 ^ var7 << 4) ^ var0);
      var8.f();
      return var8.a((int)(1.0F / var5)) == 0;
   }

   @Deprecated
   public static record a(Holder<StructureSet> otherSet, int chunkCount) {
      private final Holder<StructureSet> b;
      private final int c;
      public static final Codec<StructurePlacement.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  RegistryFileCodec.a(Registries.az, StructureSet.a, false).fieldOf("other_set").forGetter(StructurePlacement.a::a),
                  Codec.intRange(1, 16).fieldOf("chunk_count").forGetter(StructurePlacement.a::b)
               )
               .apply(var0, StructurePlacement.a::new)
      );

      public a(Holder<StructureSet> var0, int var1) {
         this.b = var0;
         this.c = var1;
      }

      boolean a(ChunkGeneratorStructureState var0, int var1, int var2) {
         return var0.a(this.b, var1, var2, this.c);
      }

      public Holder<StructureSet> a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }
   }

   @FunctionalInterface
   public interface b {
      boolean shouldGenerate(long var1, int var3, int var4, int var5, float var6);
   }

   public static enum c implements INamable {
      a("default", StructurePlacement::a),
      b("legacy_type_1", StructurePlacement::d),
      c("legacy_type_2", StructurePlacement::c),
      d("legacy_type_3", StructurePlacement::b);

      public static final Codec<StructurePlacement.c> e = INamable.a(StructurePlacement.c::values);
      private final String f;
      private final StructurePlacement.b g;

      private c(String var2, StructurePlacement.b var3) {
         this.f = var2;
         this.g = var3;
      }

      public boolean a(long var0, int var2, int var3, int var4, float var5) {
         return this.g.shouldGenerate(var0, var2, var3, var4, var5);
      }

      @Override
      public String c() {
         return this.f;
      }
   }
}
