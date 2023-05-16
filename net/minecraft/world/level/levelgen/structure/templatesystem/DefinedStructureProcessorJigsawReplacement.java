package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.ArgumentBlock;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureProcessorJigsawReplacement extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorJigsawReplacement> a = Codec.unit(() -> DefinedStructureProcessorJigsawReplacement.b);
   public static final DefinedStructureProcessorJigsawReplacement b = new DefinedStructureProcessorJigsawReplacement();

   private DefinedStructureProcessorJigsawReplacement() {
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      IBlockData var6 = var4.b;
      if (var6.a(Blocks.oX)) {
         String var7 = var4.c.l("final_state");

         IBlockData var8;
         try {
            ArgumentBlock.a var9 = ArgumentBlock.a(var0.a(Registries.e), var7, true);
            var8 = var9.a();
         } catch (CommandSyntaxException var11) {
            throw new RuntimeException(var11);
         }

         return var8.a(Blocks.kK) ? null : new DefinedStructure.BlockInfo(var4.a, var8, null);
      } else {
         return var4;
      }
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.d;
   }
}
