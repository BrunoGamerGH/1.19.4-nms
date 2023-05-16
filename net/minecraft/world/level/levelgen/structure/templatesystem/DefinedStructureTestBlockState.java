package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureTestBlockState extends DefinedStructureRuleTest {
   public static final Codec<DefinedStructureTestBlockState> a = IBlockData.b
      .fieldOf("block_state")
      .xmap(DefinedStructureTestBlockState::new, var0 -> var0.b)
      .codec();
   private final IBlockData b;

   public DefinedStructureTestBlockState(IBlockData var0) {
      this.b = var0;
   }

   @Override
   public boolean a(IBlockData var0, RandomSource var1) {
      return var0 == this.b;
   }

   @Override
   protected DefinedStructureRuleTestType<?> a() {
      return DefinedStructureRuleTestType.c;
   }
}
