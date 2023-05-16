package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureRuleTest;

public class WorldGenFeatureOreConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureOreConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.list(WorldGenFeatureOreConfiguration.a.a).fieldOf("targets").forGetter(var0x -> var0x.b),
               Codec.intRange(0, 64).fieldOf("size").forGetter(var0x -> var0x.c),
               Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter(var0x -> var0x.d)
            )
            .apply(var0, WorldGenFeatureOreConfiguration::new)
   );
   public final List<WorldGenFeatureOreConfiguration.a> b;
   public final int c;
   public final float d;

   public WorldGenFeatureOreConfiguration(List<WorldGenFeatureOreConfiguration.a> var0, int var1, float var2) {
      this.c = var1;
      this.b = var0;
      this.d = var2;
   }

   public WorldGenFeatureOreConfiguration(List<WorldGenFeatureOreConfiguration.a> var0, int var1) {
      this(var0, var1, 0.0F);
   }

   public WorldGenFeatureOreConfiguration(DefinedStructureRuleTest var0, IBlockData var1, int var2, float var3) {
      this(ImmutableList.of(new WorldGenFeatureOreConfiguration.a(var0, var1)), var2, var3);
   }

   public WorldGenFeatureOreConfiguration(DefinedStructureRuleTest var0, IBlockData var1, int var2) {
      this(ImmutableList.of(new WorldGenFeatureOreConfiguration.a(var0, var1)), var2, 0.0F);
   }

   public static WorldGenFeatureOreConfiguration.a a(DefinedStructureRuleTest var0, IBlockData var1) {
      return new WorldGenFeatureOreConfiguration.a(var0, var1);
   }

   public static class a {
      public static final Codec<WorldGenFeatureOreConfiguration.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  DefinedStructureRuleTest.c.fieldOf("target").forGetter(var0x -> var0x.b), IBlockData.b.fieldOf("state").forGetter(var0x -> var0x.c)
               )
               .apply(var0, WorldGenFeatureOreConfiguration.a::new)
      );
      public final DefinedStructureRuleTest b;
      public final IBlockData c;

      a(DefinedStructureRuleTest var0, IBlockData var1) {
         this.b = var0;
         this.c = var1;
      }
   }
}
