package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;

public abstract class PosRuleTest {
   public static final Codec<PosRuleTest> c = BuiltInRegistries.q.q().dispatch("predicate_type", PosRuleTest::a, PosRuleTestType::codec);

   public abstract boolean a(BlockPosition var1, BlockPosition var2, BlockPosition var3, RandomSource var4);

   protected abstract PosRuleTestType<?> a();
}
