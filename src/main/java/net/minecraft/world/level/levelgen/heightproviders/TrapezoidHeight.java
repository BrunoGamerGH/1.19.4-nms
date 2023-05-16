package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

public class TrapezoidHeight extends HeightProvider {
   public static final Codec<TrapezoidHeight> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.a.fieldOf("min_inclusive").forGetter(var0x -> var0x.d),
               VerticalAnchor.a.fieldOf("max_inclusive").forGetter(var0x -> var0x.e),
               Codec.INT.optionalFieldOf("plateau", 0).forGetter(var0x -> var0x.f)
            )
            .apply(var0, TrapezoidHeight::new)
   );
   private static final Logger b = LogUtils.getLogger();
   private final VerticalAnchor d;
   private final VerticalAnchor e;
   private final int f;

   private TrapezoidHeight(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
   }

   public static TrapezoidHeight a(VerticalAnchor var0, VerticalAnchor var1, int var2) {
      return new TrapezoidHeight(var0, var1, var2);
   }

   public static TrapezoidHeight a(VerticalAnchor var0, VerticalAnchor var1) {
      return a(var0, var1, 0);
   }

   @Override
   public int a(RandomSource var0, WorldGenerationContext var1) {
      int var2 = this.d.a(var1);
      int var3 = this.e.a(var1);
      if (var2 > var3) {
         b.warn("Empty height range: {}", this);
         return var2;
      } else {
         int var4 = var3 - var2;
         if (this.f >= var4) {
            return MathHelper.b(var0, var2, var3);
         } else {
            int var5 = (var4 - this.f) / 2;
            int var6 = var4 - var5;
            return var2 + MathHelper.b(var0, 0, var6) + MathHelper.b(var0, 0, var5);
         }
      }
   }

   @Override
   public HeightProviderType<?> a() {
      return HeightProviderType.e;
   }

   @Override
   public String toString() {
      return this.f == 0 ? "triangle (" + this.d + "-" + this.e + ")" : "trapezoid(" + this.f + ") in [" + this.d + "-" + this.e + "]";
   }
}
