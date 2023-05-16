package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;

public class WeightedListInt extends IntProvider {
   public static final Codec<WeightedListInt> a = RecordCodecBuilder.create(
      var0 -> var0.group(SimpleWeightedRandomList.b(IntProvider.c).fieldOf("distribution").forGetter(var0x -> var0x.b)).apply(var0, WeightedListInt::new)
   );
   private final SimpleWeightedRandomList<IntProvider> b;
   private final int f;
   private final int g;

   public WeightedListInt(SimpleWeightedRandomList<IntProvider> var0) {
      this.b = var0;
      List<WeightedEntry.b<IntProvider>> var1 = var0.e();
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MIN_VALUE;

      for(WeightedEntry.b<IntProvider> var5 : var1) {
         int var6 = var5.b().a();
         int var7 = var5.b().b();
         var2 = Math.min(var2, var6);
         var3 = Math.max(var3, var7);
      }

      this.f = var2;
      this.g = var3;
   }

   @Override
   public int a(RandomSource var0) {
      return this.b.a(var0).orElseThrow(IllegalStateException::new).a(var0);
   }

   @Override
   public int a() {
      return this.f;
   }

   @Override
   public int b() {
      return this.g;
   }

   @Override
   public IntProviderType<?> c() {
      return IntProviderType.e;
   }
}
