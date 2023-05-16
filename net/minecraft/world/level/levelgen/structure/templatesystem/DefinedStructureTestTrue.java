package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureTestTrue extends DefinedStructureRuleTest {
   public static final Codec<DefinedStructureTestTrue> a = Codec.unit(() -> DefinedStructureTestTrue.b);
   public static final DefinedStructureTestTrue b = new DefinedStructureTestTrue();

   private DefinedStructureTestTrue() {
   }

   @Override
   public boolean a(IBlockData var0, RandomSource var1) {
      return true;
   }

   @Override
   protected DefinedStructureRuleTestType<?> a() {
      return DefinedStructureRuleTestType.a;
   }
}
