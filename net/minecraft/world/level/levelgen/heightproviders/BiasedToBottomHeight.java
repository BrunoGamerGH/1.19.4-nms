package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

public class BiasedToBottomHeight extends HeightProvider {
   public static final Codec<BiasedToBottomHeight> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.a.fieldOf("min_inclusive").forGetter(var0x -> var0x.d),
               VerticalAnchor.a.fieldOf("max_inclusive").forGetter(var0x -> var0x.e),
               Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("inner", 1).forGetter(var0x -> var0x.f)
            )
            .apply(var0, BiasedToBottomHeight::new)
   );
   private static final Logger b = LogUtils.getLogger();
   private final VerticalAnchor d;
   private final VerticalAnchor e;
   private final int f;

   private BiasedToBottomHeight(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
   }

   public static BiasedToBottomHeight a(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      return new BiasedToBottomHeight(var0, var1, var2);
   }

   @Override
   public int a(RandomSource var0, WorldGenerationContext var1) {
      int var2 = this.d.a(var1);
      int var3 = this.e.a(var1);
      if (var3 - var2 - this.f + 1 <= 0) {
         b.warn("Empty height range: {}", this);
         return var2;
      } else {
         int var4 = var0.a(var3 - var2 - this.f + 1);
         return var0.a(var4 + this.f) + var2;
      }
   }

   @Override
   public HeightProviderType<?> a() {
      return HeightProviderType.c;
   }

   @Override
   public String toString() {
      return "biased[" + this.d + "-" + this.e + " inner: " + this.f + "]";
   }
}
