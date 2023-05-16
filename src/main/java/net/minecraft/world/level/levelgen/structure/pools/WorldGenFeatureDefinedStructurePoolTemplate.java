package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorGravity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableObject;

public class WorldGenFeatureDefinedStructurePoolTemplate {
   private static final int c = Integer.MIN_VALUE;
   private static final MutableObject<Codec<Holder<WorldGenFeatureDefinedStructurePoolTemplate>>> d = new MutableObject();
   public static final Codec<WorldGenFeatureDefinedStructurePoolTemplate> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.a(d::getValue).fieldOf("fallback").forGetter(WorldGenFeatureDefinedStructurePoolTemplate::a),
               Codec.mapPair(WorldGenFeatureDefinedStructurePoolStructure.e.fieldOf("element"), Codec.intRange(1, 150).fieldOf("weight"))
                  .codec()
                  .listOf()
                  .fieldOf("elements")
                  .forGetter(var0x -> var0x.e)
            )
            .apply(var0, WorldGenFeatureDefinedStructurePoolTemplate::new)
   );
   public static final Codec<Holder<WorldGenFeatureDefinedStructurePoolTemplate>> b = SystemUtils.a(RegistryFileCodec.a(Registries.aA, a), d::setValue);
   private final List<Pair<WorldGenFeatureDefinedStructurePoolStructure, Integer>> e;
   private final ObjectArrayList<WorldGenFeatureDefinedStructurePoolStructure> f;
   private final Holder<WorldGenFeatureDefinedStructurePoolTemplate> g;
   private int h = Integer.MIN_VALUE;

   public WorldGenFeatureDefinedStructurePoolTemplate(
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var0, List<Pair<WorldGenFeatureDefinedStructurePoolStructure, Integer>> var1
   ) {
      this.e = var1;
      this.f = new ObjectArrayList();

      for(Pair<WorldGenFeatureDefinedStructurePoolStructure, Integer> var3 : var1) {
         WorldGenFeatureDefinedStructurePoolStructure var4 = (WorldGenFeatureDefinedStructurePoolStructure)var3.getFirst();

         for(int var5 = 0; var5 < var3.getSecond(); ++var5) {
            this.f.add(var4);
         }
      }

      this.g = var0;
   }

   public WorldGenFeatureDefinedStructurePoolTemplate(
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var0,
      List<Pair<Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, ? extends WorldGenFeatureDefinedStructurePoolStructure>, Integer>> var1,
      WorldGenFeatureDefinedStructurePoolTemplate.Matching var2
   ) {
      this.e = Lists.newArrayList();
      this.f = new ObjectArrayList();

      for(Pair<Function<WorldGenFeatureDefinedStructurePoolTemplate.Matching, ? extends WorldGenFeatureDefinedStructurePoolStructure>, Integer> var4 : var1) {
         WorldGenFeatureDefinedStructurePoolStructure var5 = (WorldGenFeatureDefinedStructurePoolStructure)((Function)var4.getFirst()).apply(var2);
         this.e.add(Pair.of(var5, (Integer)var4.getSecond()));

         for(int var6 = 0; var6 < var4.getSecond(); ++var6) {
            this.f.add(var5);
         }
      }

      this.g = var0;
   }

   public int a(StructureTemplateManager var0) {
      if (this.h == Integer.MIN_VALUE) {
         this.h = this.f
            .stream()
            .filter(var0x -> var0x != WorldGenFeatureDefinedStructurePoolEmpty.b)
            .mapToInt(var1x -> var1x.a(var0, BlockPosition.b, EnumBlockRotation.a).d())
            .max()
            .orElse(0);
      }

      return this.h;
   }

   public Holder<WorldGenFeatureDefinedStructurePoolTemplate> a() {
      return this.g;
   }

   public WorldGenFeatureDefinedStructurePoolStructure a(RandomSource var0) {
      return (WorldGenFeatureDefinedStructurePoolStructure)this.f.get(var0.a(this.f.size()));
   }

   public List<WorldGenFeatureDefinedStructurePoolStructure> b(RandomSource var0) {
      return SystemUtils.a(this.f, var0);
   }

   public int b() {
      return this.f.size();
   }

   public static enum Matching implements INamable {
      a("terrain_matching", ImmutableList.of(new DefinedStructureProcessorGravity(HeightMap.Type.a, -1))),
      b("rigid", ImmutableList.of());

      public static final INamable.a<WorldGenFeatureDefinedStructurePoolTemplate.Matching> c = INamable.a(
         WorldGenFeatureDefinedStructurePoolTemplate.Matching::values
      );
      private final String d;
      private final ImmutableList<DefinedStructureProcessor> e;

      private Matching(String var2, ImmutableList var3) {
         this.d = var2;
         this.e = var3;
      }

      public String a() {
         return this.d;
      }

      public static WorldGenFeatureDefinedStructurePoolTemplate.Matching a(String var0) {
         return c.a(var0);
      }

      public ImmutableList<DefinedStructureProcessor> b() {
         return this.e;
      }

      @Override
      public String c() {
         return this.d;
      }
   }
}
