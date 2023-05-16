package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureTestRandomBlockState extends DefinedStructureRuleTest {
   public static final Codec<DefinedStructureTestRandomBlockState> a = RecordCodecBuilder.create(
      var0 -> var0.group(IBlockData.b.fieldOf("block_state").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.d))
            .apply(var0, DefinedStructureTestRandomBlockState::new)
   );
   private final IBlockData b;
   private final float d;

   public DefinedStructureTestRandomBlockState(IBlockData var0, float var1) {
      this.b = var0;
      this.d = var1;
   }

   @Override
   public boolean a(IBlockData var0, RandomSource var1) {
      return var0 == this.b && var1.i() < this.d;
   }

   @Override
   protected DefinedStructureRuleTestType<?> a() {
      return DefinedStructureRuleTestType.f;
   }
}
