package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureTestBlock extends DefinedStructureRuleTest {
   public static final Codec<DefinedStructureTestBlock> a = BuiltInRegistries.f
      .q()
      .fieldOf("block")
      .xmap(DefinedStructureTestBlock::new, var0 -> var0.b)
      .codec();
   private final Block b;

   public DefinedStructureTestBlock(Block var0) {
      this.b = var0;
   }

   @Override
   public boolean a(IBlockData var0, RandomSource var1) {
      return var0.a(this.b);
   }

   @Override
   protected DefinedStructureRuleTestType<?> a() {
      return DefinedStructureRuleTestType.b;
   }
}
