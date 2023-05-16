package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class AttachedToLeavesDecorator extends WorldGenFeatureTree {
   public static final Codec<AttachedToLeavesDecorator> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(var0x -> var0x.b),
               Codec.intRange(0, 16).fieldOf("exclusion_radius_xz").forGetter(var0x -> var0x.c),
               Codec.intRange(0, 16).fieldOf("exclusion_radius_y").forGetter(var0x -> var0x.d),
               WorldGenFeatureStateProvider.a.fieldOf("block_provider").forGetter(var0x -> var0x.e),
               Codec.intRange(1, 16).fieldOf("required_empty_blocks").forGetter(var0x -> var0x.f),
               ExtraCodecs.a(EnumDirection.g.listOf()).fieldOf("directions").forGetter(var0x -> var0x.g)
            )
            .apply(var0, AttachedToLeavesDecorator::new)
   );
   protected final float b;
   protected final int c;
   protected final int d;
   protected final WorldGenFeatureStateProvider e;
   protected final int f;
   protected final List<EnumDirection> g;

   public AttachedToLeavesDecorator(float var0, int var1, int var2, WorldGenFeatureStateProvider var3, int var4, List<EnumDirection> var5) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
   }

   @Override
   public void a(WorldGenFeatureTree.a var0) {
      Set<BlockPosition> var1 = new HashSet<>();
      RandomSource var2 = var0.b();

      for(BlockPosition var4 : SystemUtils.a(var0.d(), var2)) {
         EnumDirection var5 = SystemUtils.a(this.g, var2);
         BlockPosition var6 = var4.a(var5);
         if (!var1.contains(var6) && var2.i() < this.b && this.a(var0, var4, var5)) {
            BlockPosition var7 = var6.b(-this.c, -this.d, -this.c);
            BlockPosition var8 = var6.b(this.c, this.d, this.c);

            for(BlockPosition var10 : BlockPosition.a(var7, var8)) {
               var1.add(var10.i());
            }

            var0.a(var6, this.e.a(var2, var6));
         }
      }
   }

   private boolean a(WorldGenFeatureTree.a var0, BlockPosition var1, EnumDirection var2) {
      for(int var3 = 1; var3 <= this.f; ++var3) {
         BlockPosition var4 = var1.a(var2, var3);
         if (!var0.a(var4)) {
            return false;
         }
      }

      return true;
   }

   @Override
   protected WorldGenFeatureTrees<?> a() {
      return WorldGenFeatureTrees.f;
   }
}
