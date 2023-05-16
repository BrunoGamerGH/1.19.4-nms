package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class DefinedStructureRuleTest {
   public static final Codec<DefinedStructureRuleTest> c = BuiltInRegistries.p
      .q()
      .dispatch("predicate_type", DefinedStructureRuleTest::a, DefinedStructureRuleTestType::codec);

   public abstract boolean a(IBlockData var1, RandomSource var2);

   protected abstract DefinedStructureRuleTestType<?> a();
}
