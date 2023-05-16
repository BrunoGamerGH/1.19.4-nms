package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

public class UniformHeight extends HeightProvider {
   public static final Codec<UniformHeight> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               VerticalAnchor.a.fieldOf("min_inclusive").forGetter(var0x -> var0x.d), VerticalAnchor.a.fieldOf("max_inclusive").forGetter(var0x -> var0x.e)
            )
            .apply(var0, UniformHeight::new)
   );
   private static final Logger b = LogUtils.getLogger();
   private final VerticalAnchor d;
   private final VerticalAnchor e;
   private final LongSet f = new LongOpenHashSet();

   private UniformHeight(VerticalAnchor var0, VerticalAnchor var1) {
      this.d = var0;
      this.e = var1;
   }

   public static UniformHeight a(VerticalAnchor var0, VerticalAnchor var1) {
      return new UniformHeight(var0, var1);
   }

   @Override
   public int a(RandomSource var0, WorldGenerationContext var1) {
      int var2 = this.d.a(var1);
      int var3 = this.e.a(var1);
      if (var2 > var3) {
         if (this.f.add((long)var2 << 32 | (long)var3)) {
            b.warn("Empty height range: {}", this);
         }

         return var2;
      } else {
         return MathHelper.b(var0, var2, var3);
      }
   }

   @Override
   public HeightProviderType<?> a() {
      return HeightProviderType.b;
   }

   @Override
   public String toString() {
      return "[" + this.d + "-" + this.e + "]";
   }
}
