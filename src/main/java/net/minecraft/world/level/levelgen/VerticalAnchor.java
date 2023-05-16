package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.dimension.DimensionManager;

public interface VerticalAnchor {
   Codec<VerticalAnchor> a = ExtraCodecs.a(VerticalAnchor.b.d, ExtraCodecs.a(VerticalAnchor.a.d, VerticalAnchor.c.d))
      .xmap(VerticalAnchor::a, VerticalAnchor::a);
   VerticalAnchor b = b(0);
   VerticalAnchor c = c(0);

   static VerticalAnchor a(int var0) {
      return new VerticalAnchor.b(var0);
   }

   static VerticalAnchor b(int var0) {
      return new VerticalAnchor.a(var0);
   }

   static VerticalAnchor c(int var0) {
      return new VerticalAnchor.c(var0);
   }

   static VerticalAnchor a() {
      return b;
   }

   static VerticalAnchor b() {
      return c;
   }

   private static VerticalAnchor a(Either<VerticalAnchor.b, Either<VerticalAnchor.a, VerticalAnchor.c>> var0) {
      return (VerticalAnchor)var0.map(Function.identity(), var0x -> (Record)var0x.map(Function.identity(), Function.identity()));
   }

   private static Either<VerticalAnchor.b, Either<VerticalAnchor.a, VerticalAnchor.c>> a(VerticalAnchor var0) {
      return var0 instanceof VerticalAnchor.b
         ? Either.left((VerticalAnchor.b)var0)
         : Either.right(var0 instanceof VerticalAnchor.a ? Either.left((VerticalAnchor.a)var0) : Either.right((VerticalAnchor.c)var0));
   }

   int a(WorldGenerationContext var1);

   public static record a(int offset) implements VerticalAnchor {
      private final int e;
      public static final Codec<VerticalAnchor.a> d = Codec.intRange(DimensionManager.e, DimensionManager.d)
         .fieldOf("above_bottom")
         .xmap(VerticalAnchor.a::new, VerticalAnchor.a::c)
         .codec();

      public a(int var0) {
         this.e = var0;
      }

      @Override
      public int a(WorldGenerationContext var0) {
         return var0.a() + this.e;
      }

      @Override
      public String toString() {
         return this.e + " above bottom";
      }

      public int c() {
         return this.e;
      }
   }

   public static record b(int y) implements VerticalAnchor {
      private final int e;
      public static final Codec<VerticalAnchor.b> d = Codec.intRange(DimensionManager.e, DimensionManager.d)
         .fieldOf("absolute")
         .xmap(VerticalAnchor.b::new, VerticalAnchor.b::c)
         .codec();

      public b(int var0) {
         this.e = var0;
      }

      @Override
      public int a(WorldGenerationContext var0) {
         return this.e;
      }

      @Override
      public String toString() {
         return this.e + " absolute";
      }

      public int c() {
         return this.e;
      }
   }

   public static record c(int offset) implements VerticalAnchor {
      private final int e;
      public static final Codec<VerticalAnchor.c> d = Codec.intRange(DimensionManager.e, DimensionManager.d)
         .fieldOf("below_top")
         .xmap(VerticalAnchor.c::new, VerticalAnchor.c::c)
         .codec();

      public c(int var0) {
         this.e = var0;
      }

      @Override
      public int a(WorldGenerationContext var0) {
         return var0.b() - 1 + var0.a() - this.e;
      }

      @Override
      public String toString() {
         return this.e + " below top";
      }

      public int c() {
         return this.e;
      }
   }
}
