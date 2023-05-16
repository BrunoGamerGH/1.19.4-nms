package net.minecraft.world.level.levelgen.flat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.DimensionManager;

public class WorldGenFlatLayerInfo {
   public static final Codec<WorldGenFlatLayerInfo> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, DimensionManager.c).fieldOf("height").forGetter(WorldGenFlatLayerInfo::a),
               BuiltInRegistries.f.q().fieldOf("block").orElse(Blocks.a).forGetter(var0x -> var0x.b().b())
            )
            .apply(var0, WorldGenFlatLayerInfo::new)
   );
   private final Block b;
   private final int c;

   public WorldGenFlatLayerInfo(int var0, Block var1) {
      this.c = var0;
      this.b = var1;
   }

   public int a() {
      return this.c;
   }

   public IBlockData b() {
      return this.b.o();
   }

   @Override
   public String toString() {
      return (this.c != 1 ? this.c + "*" : "") + BuiltInRegistries.f.b(this.b);
   }
}
