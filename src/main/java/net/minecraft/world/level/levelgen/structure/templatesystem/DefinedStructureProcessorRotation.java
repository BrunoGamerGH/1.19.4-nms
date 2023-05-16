package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Block;

public class DefinedStructureProcessorRotation extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorRotation> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               RegistryCodecs.a(Registries.e).optionalFieldOf("rottable_blocks").forGetter(var0x -> var0x.b),
               Codec.floatRange(0.0F, 1.0F).fieldOf("integrity").forGetter(var0x -> var0x.c)
            )
            .apply(var0, DefinedStructureProcessorRotation::new)
   );
   private final Optional<HolderSet<Block>> b;
   private final float c;

   public DefinedStructureProcessorRotation(HolderSet<Block> var0, float var1) {
      this(Optional.of(var0), var1);
   }

   public DefinedStructureProcessorRotation(float var0) {
      this(Optional.empty(), var0);
   }

   private DefinedStructureProcessorRotation(Optional<HolderSet<Block>> var0, float var1) {
      this.c = var1;
      this.b = var0;
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      RandomSource var6 = var5.b(var4.a);
      return (!this.b.isPresent() || var3.b.a(this.b.get())) && !(var6.i() <= this.c) ? null : var4;
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.b;
   }
}
