package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface DefinedStructureRuleTestType<P extends DefinedStructureRuleTest> {
   DefinedStructureRuleTestType<DefinedStructureTestTrue> a = a("always_true", DefinedStructureTestTrue.a);
   DefinedStructureRuleTestType<DefinedStructureTestBlock> b = a("block_match", DefinedStructureTestBlock.a);
   DefinedStructureRuleTestType<DefinedStructureTestBlockState> c = a("blockstate_match", DefinedStructureTestBlockState.a);
   DefinedStructureRuleTestType<DefinedStructureTestTag> d = a("tag_match", DefinedStructureTestTag.a);
   DefinedStructureRuleTestType<DefinedStructureTestRandomBlock> e = a("random_block_match", DefinedStructureTestRandomBlock.a);
   DefinedStructureRuleTestType<DefinedStructureTestRandomBlockState> f = a("random_blockstate_match", DefinedStructureTestRandomBlockState.a);

   Codec<P> codec();

   static <P extends DefinedStructureRuleTest> DefinedStructureRuleTestType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.p, var0, () -> var1);
   }
}
