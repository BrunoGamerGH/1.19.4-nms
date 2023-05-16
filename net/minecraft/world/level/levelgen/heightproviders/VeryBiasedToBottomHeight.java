package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

public class VeryBiasedToBottomHeight extends HeightProvider {
   public static final Codec<VeryBiasedToBottomHeight> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.a.fieldOf("min_inclusive").forGetter(var0x -> var0x.d),
               VerticalAnchor.a.fieldOf("max_inclusive").forGetter(var0x -> var0x.e),
               Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("inner", 1).forGetter(var0x -> var0x.f)
            )
            .apply(var0, VeryBiasedToBottomHeight::new)
   );
   private static final Logger b = LogUtils.getLogger();
   private final VerticalAnchor d;
   private final VerticalAnchor e;
   private final int f;

   private VeryBiasedToBottomHeight(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
   }

   public static VeryBiasedToBottomHeight a(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      return new VeryBiasedToBottomHeight(var0, var1, var2);
   }

   @Override
   public int a(RandomSource var0, WorldGenerationContext var1) {
      int var2 = this.d.a(var1);
      int var3 = this.e.a(var1);
      if (var3 - var2 - this.f + 1 <= 0) {
         b.warn("Empty height range: {}", this);
         return var2;
      } else {
         int var4 = MathHelper.a(var0, var2 + this.f, var3);
         int var5 = MathHelper.a(var0, var2, var4 - 1);
         return MathHelper.a(var0, var2, var5 - 1 + this.f);
      }
   }

   @Override
   public HeightProviderType<?> a() {
      return HeightProviderType.d;
   }

   @Override
   public String toString() {
      return "biased[" + this.d + "-" + this.e + " inner: " + this.f + "]";
   }
}
