package net.minecraft.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.core.RegistryMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.server.packs.resources.IResource;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureStructureProcessorType;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import org.slf4j.Logger;

public class RegistryDataLoader {
   private static final Logger c = LogUtils.getLogger();
   public static final List<RegistryDataLoader.b<?>> a = List.of(
      new RegistryDataLoader.b<>(Registries.as, DimensionManager.h),
      new RegistryDataLoader.b<>(Registries.an, BiomeBase.a),
      new RegistryDataLoader.b<>(Registries.ao, ChatMessageType.a),
      new RegistryDataLoader.b<>(Registries.ap, WorldGenCarverWrapper.a),
      new RegistryDataLoader.b<>(Registries.aq, WorldGenFeatureConfigured.a),
      new RegistryDataLoader.b<>(Registries.aw, PlacedFeature.a),
      new RegistryDataLoader.b<>(Registries.ax, Structure.a),
      new RegistryDataLoader.b<>(Registries.az, StructureSet.a),
      new RegistryDataLoader.b<>(Registries.ay, DefinedStructureStructureProcessorType.m),
      new RegistryDataLoader.b<>(Registries.aA, WorldGenFeatureDefinedStructurePoolTemplate.a),
      new RegistryDataLoader.b<>(Registries.au, GeneratorSettingBase.a),
      new RegistryDataLoader.b<>(Registries.av, NoiseGeneratorNormal.a.a),
      new RegistryDataLoader.b<>(Registries.ar, DensityFunction.b),
      new RegistryDataLoader.b<>(Registries.aD, WorldPreset.a),
      new RegistryDataLoader.b<>(Registries.at, FlatLevelGeneratorPreset.a),
      new RegistryDataLoader.b<>(Registries.aC, TrimPattern.a),
      new RegistryDataLoader.b<>(Registries.aB, TrimMaterial.a),
      new RegistryDataLoader.b<>(Registries.o, DamageType.a),
      new RegistryDataLoader.b<>(Registries.aE, MultiNoiseBiomeSourceParameterList.a)
   );
   public static final List<RegistryDataLoader.b<?>> b = List.of(new RegistryDataLoader.b<>(Registries.aG, WorldDimension.a));

   public static IRegistryCustom.Dimension a(IResourceManager var0, IRegistryCustom var1, List<RegistryDataLoader.b<?>> var2) {
      Map<ResourceKey<?>, Exception> var3 = new HashMap<>();
      List<Pair<IRegistryWritable<?>, RegistryDataLoader.a>> var4 = var2.stream().map(var1x -> var1x.a(Lifecycle.stable(), var3)).toList();
      RegistryOps.b var5 = a(var1, var4);
      var4.forEach(var2x -> ((RegistryDataLoader.a)var2x.getSecond()).load(var0, var5));
      var4.forEach(var1x -> {
         IRegistry<?> var2x = (IRegistry)var1x.getFirst();

         try {
            var2x.l();
         } catch (Exception var4x) {
            var3.put(var2x.c(), var4x);
         }
      });
      if (!var3.isEmpty()) {
         a(var3);
         throw new IllegalStateException("Failed to load registries due to above errors");
      } else {
         return new IRegistryCustom.c(var4.stream().map(Pair::getFirst).toList()).c();
      }
   }

   private static RegistryOps.b a(IRegistryCustom var0, List<Pair<IRegistryWritable<?>, RegistryDataLoader.a>> var1) {
      final Map<ResourceKey<? extends IRegistry<?>>, RegistryOps.a<?>> var2 = new HashMap<>();
      var0.b().forEach(var1x -> var2.put(var1x.a(), a(var1x.b())));
      var1.forEach(var1x -> var2.put(((IRegistryWritable)var1x.getFirst()).c(), a((IRegistryWritable)var1x.getFirst())));
      return new RegistryOps.b() {
         @Override
         public <T> Optional<RegistryOps.a<T>> a(ResourceKey<? extends IRegistry<? extends T>> var0) {
            return Optional.ofNullable((RegistryOps.a<T>)var2.get(var0));
         }
      };
   }

   private static <T> RegistryOps.a<T> a(IRegistryWritable<T> var0) {
      return new RegistryOps.a<>(var0.p(), var0.n(), var0.d());
   }

   private static <T> RegistryOps.a<T> a(IRegistry<T> var0) {
      return new RegistryOps.a<>(var0.p(), var0.u(), var0.d());
   }

   private static void a(Map<ResourceKey<?>, Exception> var0) {
      StringWriter var1 = new StringWriter();
      PrintWriter var2 = new PrintWriter(var1);
      Map<MinecraftKey, Map<MinecraftKey, Exception>> var3 = var0.entrySet()
         .stream()
         .collect(
            Collectors.groupingBy(var0x -> ((ResourceKey)var0x.getKey()).b(), Collectors.toMap(var0x -> ((ResourceKey)var0x.getKey()).a(), Entry::getValue))
         );
      var3.entrySet().stream().sorted(Entry.comparingByKey()).forEach(var1x -> {
         var2.printf("> Errors in registry %s:%n", var1x.getKey());
         var1x.getValue().entrySet().stream().sorted(Entry.comparingByKey()).forEach(var1xx -> {
            var2.printf(">> Errors in element %s:%n", var1xx.getKey());
            var1xx.getValue().printStackTrace(var2);
         });
      });
      var2.flush();
      c.error("Registry loading errors:\n{}", var1);
   }

   private static String a(MinecraftKey var0) {
      return var0.a();
   }

   static <E> void a(
      RegistryOps.b var0,
      IResourceManager var1,
      ResourceKey<? extends IRegistry<E>> var2,
      IRegistryWritable<E> var3,
      Decoder<E> var4,
      Map<ResourceKey<?>, Exception> var5
   ) {
      String var6 = a(var2.a());
      FileToIdConverter var7 = FileToIdConverter.a(var6);
      RegistryOps<JsonElement> var8 = RegistryOps.a(JsonOps.INSTANCE, var0);

      for(Entry<MinecraftKey, IResource> var10 : var7.a(var1).entrySet()) {
         MinecraftKey var11 = var10.getKey();
         ResourceKey<E> var12 = ResourceKey.a(var2, var7.b(var11));
         IResource var13 = var10.getValue();

         try (Reader var14 = var13.e()) {
            JsonElement var15 = JsonParser.parseReader(var14);
            DataResult<E> var16 = var4.parse(var8, var15);
            E var17 = (E)var16.getOrThrow(false, var0x -> {
            });
            var3.a(var12, var17, var13.c() ? Lifecycle.stable() : var16.lifecycle());
         } catch (Exception var20) {
            var5.put(var12, new IllegalStateException(String.format(Locale.ROOT, "Failed to parse %s from pack %s", var11, var13.b()), var20));
         }
      }
   }

   interface a {
      void load(IResourceManager var1, RegistryOps.b var2);
   }

   public static record b<T>(ResourceKey<? extends IRegistry<T>> key, Codec<T> elementCodec) {
      private final ResourceKey<? extends IRegistry<T>> a;
      private final Codec<T> b;

      public b(ResourceKey<? extends IRegistry<T>> var0, Codec<T> var1) {
         this.a = var0;
         this.b = var1;
      }

      Pair<IRegistryWritable<?>, RegistryDataLoader.a> a(Lifecycle var0, Map<ResourceKey<?>, Exception> var1) {
         IRegistryWritable<T> var2 = new RegistryMaterials<>(this.a, var0);
         RegistryDataLoader.a var3 = (var2x, var3x) -> RegistryDataLoader.a(var3x, var2x, this.a, var2, this.b, var1);
         return Pair.of(var2, var3);
      }
   }
}
