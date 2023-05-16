package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureStructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class FossilFeatureConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<FossilFeatureConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               MinecraftKey.a.listOf().fieldOf("fossil_structures").forGetter(var0x -> var0x.b),
               MinecraftKey.a.listOf().fieldOf("overlay_structures").forGetter(var0x -> var0x.c),
               DefinedStructureStructureProcessorType.n.fieldOf("fossil_processors").forGetter(var0x -> var0x.d),
               DefinedStructureStructureProcessorType.n.fieldOf("overlay_processors").forGetter(var0x -> var0x.e),
               Codec.intRange(0, 7).fieldOf("max_empty_corners_allowed").forGetter(var0x -> var0x.f)
            )
            .apply(var0, FossilFeatureConfiguration::new)
   );
   public final List<MinecraftKey> b;
   public final List<MinecraftKey> c;
   public final Holder<ProcessorList> d;
   public final Holder<ProcessorList> e;
   public final int f;

   public FossilFeatureConfiguration(List<MinecraftKey> var0, List<MinecraftKey> var1, Holder<ProcessorList> var2, Holder<ProcessorList> var3, int var4) {
      if (var0.isEmpty()) {
         throw new IllegalArgumentException("Fossil structure lists need at least one entry");
      } else if (var0.size() != var1.size()) {
         throw new IllegalArgumentException("Fossil structure lists must be equal lengths");
      } else {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
      }
   }
}
