package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;

public class MultifaceGrowthConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<MultifaceGrowthConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               BuiltInRegistries.f
                  .q()
                  .fieldOf("block")
                  .flatXmap(MultifaceGrowthConfiguration::a, DataResult::success)
                  .orElse((MultifaceBlock)Blocks.ff)
                  .forGetter(var0x -> var0x.b),
               Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(var0x -> var0x.c),
               Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(var0x -> var0x.d),
               Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(var0x -> var0x.e),
               Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(var0x -> var0x.f),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").orElse(0.5F).forGetter(var0x -> var0x.g),
               RegistryCodecs.a(Registries.e).fieldOf("can_be_placed_on").forGetter(var0x -> var0x.h)
            )
            .apply(var0, MultifaceGrowthConfiguration::new)
   );
   public final MultifaceBlock b;
   public final int c;
   public final boolean d;
   public final boolean e;
   public final boolean f;
   public final float g;
   public final HolderSet<Block> h;
   private final ObjectArrayList<EnumDirection> i;

   private static DataResult<MultifaceBlock> a(Block var0) {
      return var0 instanceof MultifaceBlock var1 ? DataResult.success(var1) : DataResult.error(() -> "Growth block should be a multiface block");
   }

   public MultifaceGrowthConfiguration(MultifaceBlock var0, int var1, boolean var2, boolean var3, boolean var4, float var5, HolderSet<Block> var6) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = new ObjectArrayList(6);
      if (var3) {
         this.i.add(EnumDirection.b);
      }

      if (var2) {
         this.i.add(EnumDirection.a);
      }

      if (var4) {
         EnumDirection.EnumDirectionLimit.a.forEach(this.i::add);
      }
   }

   public List<EnumDirection> a(RandomSource var0, EnumDirection var1) {
      return SystemUtils.a(this.i.stream().filter(var1x -> var1x != var1), var0);
   }

   public List<EnumDirection> a(RandomSource var0) {
      return SystemUtils.a(this.i, var0);
   }
}
