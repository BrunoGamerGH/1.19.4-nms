package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface PosRuleTestType<P extends PosRuleTest> {
   PosRuleTestType<PosRuleTestTrue> a = a("always_true", PosRuleTestTrue.a);
   PosRuleTestType<PosRuleTestLinear> b = a("linear_pos", PosRuleTestLinear.a);
   PosRuleTestType<PosRuleTestAxisAlignedLinear> c = a("axis_aligned_linear_pos", PosRuleTestAxisAlignedLinear.a);

   Codec<P> codec();

   static <P extends PosRuleTest> PosRuleTestType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.q, var0, () -> var1);
   }
}
