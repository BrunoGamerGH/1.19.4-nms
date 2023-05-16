package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureProcessorRule extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorRule> a = DefinedStructureProcessorPredicates.a
      .listOf()
      .fieldOf("rules")
      .xmap(DefinedStructureProcessorRule::new, var0 -> var0.b)
      .codec();
   private final ImmutableList<DefinedStructureProcessorPredicates> b;

   public DefinedStructureProcessorRule(List<? extends DefinedStructureProcessorPredicates> var0) {
      this.b = ImmutableList.copyOf(var0);
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      RandomSource var6 = RandomSource.a(MathHelper.a(var4.a));
      IBlockData var7 = var0.a_(var4.a);
      UnmodifiableIterator var9 = this.b.iterator();

      while(var9.hasNext()) {
         DefinedStructureProcessorPredicates var9x = (DefinedStructureProcessorPredicates)var9.next();
         if (var9x.a(var4.b, var7, var3.a, var4.a, var2, var6)) {
            return new DefinedStructure.BlockInfo(var4.a, var9x.a(), var9x.b());
         }
      }

      return var4;
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.e;
   }
}
