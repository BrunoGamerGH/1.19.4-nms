package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureTestRandomBlock extends DefinedStructureRuleTest {
   public static final Codec<DefinedStructureTestRandomBlock> a = RecordCodecBuilder.create(
      var0 -> var0.group(BuiltInRegistries.f.q().fieldOf("block").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.d))
            .apply(var0, DefinedStructureTestRandomBlock::new)
   );
   private final Block b;
   private final float d;

   public DefinedStructureTestRandomBlock(Block var0, float var1) {
      this.b = var0;
      this.d = var1;
   }

   @Override
   public boolean a(IBlockData var0, RandomSource var1) {
      return var0.a(this.b) && var1.i() < this.d;
   }

   @Override
   protected DefinedStructureRuleTestType<?> a() {
      return DefinedStructureRuleTestType.e;
   }
}
