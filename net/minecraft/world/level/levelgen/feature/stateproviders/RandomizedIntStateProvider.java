package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class RandomizedIntStateProvider extends WorldGenFeatureStateProvider {
   public static final Codec<RandomizedIntStateProvider> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("source").forGetter(var0x -> var0x.c),
               Codec.STRING.fieldOf("property").forGetter(var0x -> var0x.d),
               IntProvider.c.fieldOf("values").forGetter(var0x -> var0x.f)
            )
            .apply(var0, RandomizedIntStateProvider::new)
   );
   private final WorldGenFeatureStateProvider c;
   private final String d;
   @Nullable
   private BlockStateInteger e;
   private final IntProvider f;

   public RandomizedIntStateProvider(WorldGenFeatureStateProvider var0, BlockStateInteger var1, IntProvider var2) {
      this.c = var0;
      this.e = var1;
      this.d = var1.f();
      this.f = var2;
      Collection<Integer> var3 = var1.a();

      for(int var4 = var2.a(); var4 <= var2.b(); ++var4) {
         if (!var3.contains(var4)) {
            throw new IllegalArgumentException("Property value out of range: " + var1.f() + ": " + var4);
         }
      }
   }

   public RandomizedIntStateProvider(WorldGenFeatureStateProvider var0, String var1, IntProvider var2) {
      this.c = var0;
      this.d = var1;
      this.f = var2;
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.g;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      IBlockData var2 = this.c.a(var0, var1);
      if (this.e == null || !var2.b(this.e)) {
         this.e = a(var2, this.d);
      }

      return var2.a(this.e, Integer.valueOf(this.f.a(var0)));
   }

   private static BlockStateInteger a(IBlockData var0, String var1) {
      Collection<IBlockState<?>> var2 = var0.x();
      Optional<BlockStateInteger> var3 = var2.stream()
         .filter(var1x -> var1x.f().equals(var1))
         .filter(var0x -> var0x instanceof BlockStateInteger)
         .map(var0x -> (BlockStateInteger)var0x)
         .findAny();
      return var3.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + var1));
   }
}
