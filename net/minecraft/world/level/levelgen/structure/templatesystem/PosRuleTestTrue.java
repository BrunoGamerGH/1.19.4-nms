package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;

public class PosRuleTestTrue extends PosRuleTest {
   public static final Codec<PosRuleTestTrue> a = Codec.unit(() -> PosRuleTestTrue.b);
   public static final PosRuleTestTrue b = new PosRuleTestTrue();

   private PosRuleTestTrue() {
   }

   @Override
   public boolean a(BlockPosition var0, BlockPosition var1, BlockPosition var2, RandomSource var3) {
      return true;
   }

   @Override
   protected PosRuleTestType<?> a() {
      return PosRuleTestType.a;
   }
}
