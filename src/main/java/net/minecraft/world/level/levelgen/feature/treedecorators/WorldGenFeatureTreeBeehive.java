package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.BlockBeehive;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityTypes;

public class WorldGenFeatureTreeBeehive extends WorldGenFeatureTree {
   public static final Codec<WorldGenFeatureTreeBeehive> a = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .xmap(WorldGenFeatureTreeBeehive::new, var0 -> var0.d)
      .codec();
   private static final EnumDirection b = EnumDirection.d;
   private static final EnumDirection[] c = EnumDirection.EnumDirectionLimit.a.a().filter(var0 -> var0 != b.g()).toArray(var0 -> new EnumDirection[var0]);
   private final float d;

   public WorldGenFeatureTreeBeehive(float var0) {
      this.d = var0;
   }

   @Override
   protected WorldGenFeatureTrees<?> a() {
      return WorldGenFeatureTrees.d;
   }

   @Override
   public void a(WorldGenFeatureTree.a var0) {
      RandomSource var1 = var0.b();
      if (!(var1.i() >= this.d)) {
         List<BlockPosition> var2 = var0.d();
         List<BlockPosition> var3 = var0.c();
         int var4 = !var2.isEmpty()
            ? Math.max(var2.get(0).v() - 1, var3.get(0).v() + 1)
            : Math.min(var3.get(0).v() + 1 + var1.a(3), var3.get(var3.size() - 1).v());
         List<BlockPosition> var5 = var3.stream().filter(var1x -> var1x.v() == var4).flatMap(var0x -> Stream.of(c).map(var0x::a)).collect(Collectors.toList());
         if (!var5.isEmpty()) {
            Collections.shuffle(var5);
            Optional<BlockPosition> var6 = var5.stream().filter(var1x -> var0.a(var1x) && var0.a(var1x.a(b))).findFirst();
            if (!var6.isEmpty()) {
               var0.a(var6.get(), Blocks.pa.o().a(BlockBeehive.a, b));
               var0.a().a(var6.get(), TileEntityTypes.H).ifPresent(var1x -> {
                  int var2x = 2 + var1.a(2);

                  for(int var3x = 0; var3x < var2x; ++var3x) {
                     NBTTagCompound var4x = new NBTTagCompound();
                     var4x.a("id", BuiltInRegistries.h.b(EntityTypes.h).toString());
                     var1x.a(var4x, var1.a(599), false);
                  }
               });
            }
         }
      }
   }
}
