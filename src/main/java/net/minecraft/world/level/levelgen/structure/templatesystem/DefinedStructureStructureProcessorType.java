package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;

public interface DefinedStructureStructureProcessorType<P extends DefinedStructureProcessor> {
   DefinedStructureStructureProcessorType<DefinedStructureProcessorBlockIgnore> a = a("block_ignore", DefinedStructureProcessorBlockIgnore.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorRotation> b = a("block_rot", DefinedStructureProcessorRotation.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorGravity> c = a("gravity", DefinedStructureProcessorGravity.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorJigsawReplacement> d = a("jigsaw_replacement", DefinedStructureProcessorJigsawReplacement.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorRule> e = a("rule", DefinedStructureProcessorRule.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorNop> f = a("nop", DefinedStructureProcessorNop.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorBlockAge> g = a("block_age", DefinedStructureProcessorBlockAge.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorBlackstoneReplace> h = a("blackstone_replace", DefinedStructureProcessorBlackstoneReplace.a);
   DefinedStructureStructureProcessorType<DefinedStructureProcessorLavaSubmergedBlock> i = a(
      "lava_submerged_block", DefinedStructureProcessorLavaSubmergedBlock.a
   );
   DefinedStructureStructureProcessorType<ProtectedBlockProcessor> j = a("protected_blocks", ProtectedBlockProcessor.b);
   Codec<DefinedStructureProcessor> k = BuiltInRegistries.ag
      .q()
      .dispatch("processor_type", DefinedStructureProcessor::a, DefinedStructureStructureProcessorType::codec);
   Codec<ProcessorList> l = k.listOf().xmap(ProcessorList::new, ProcessorList::a);
   Codec<ProcessorList> m = Codec.either(l.fieldOf("processors").codec(), l)
      .xmap(var0 -> (ProcessorList)var0.map(var0x -> var0x, var0x -> var0x), Either::left);
   Codec<Holder<ProcessorList>> n = RegistryFileCodec.a(Registries.ay, m);

   Codec<P> codec();

   static <P extends DefinedStructureProcessor> DefinedStructureStructureProcessorType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.ag, var0, () -> var1);
   }
}
