package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureTestTag extends DefinedStructureRuleTest {
   public static final Codec<DefinedStructureTestTag> a = TagKey.a(Registries.e).fieldOf("tag").xmap(DefinedStructureTestTag::new, var0 -> var0.b).codec();
   private final TagKey<Block> b;

   public DefinedStructureTestTag(TagKey<Block> var0) {
      this.b = var0;
   }

   @Override
   public boolean a(IBlockData var0, RandomSource var1) {
      return var0.a(this.b);
   }

   @Override
   protected DefinedStructureRuleTestType<?> a() {
      return DefinedStructureRuleTestType.d;
   }
}
