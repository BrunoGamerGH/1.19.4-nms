package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.INamable;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import org.slf4j.Logger;

public class BiomeSettingsMobs {
   private static final Logger d = LogUtils.getLogger();
   private static final float e = 0.1F;
   public static final WeightedRandomList<BiomeSettingsMobs.c> a = WeightedRandomList.c();
   public static final BiomeSettingsMobs b = new BiomeSettingsMobs.a().a();
   public static final MapCodec<BiomeSettingsMobs> c = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.floatRange(0.0F, 0.9999999F).optionalFieldOf("creature_spawn_probability", 0.1F).forGetter(var0x -> var0x.f),
               Codec.simpleMap(
                     EnumCreatureType.i,
                     WeightedRandomList.c(BiomeSettingsMobs.c.a).promotePartial(SystemUtils.a("Spawn data: ", d::error)),
                     INamable.a(EnumCreatureType.values())
                  )
                  .fieldOf("spawners")
                  .forGetter(var0x -> var0x.g),
               Codec.simpleMap(BuiltInRegistries.h.q(), BiomeSettingsMobs.b.a, BuiltInRegistries.h).fieldOf("spawn_costs").forGetter(var0x -> var0x.h)
            )
            .apply(var0, BiomeSettingsMobs::new)
   );
   private final float f;
   private final Map<EnumCreatureType, WeightedRandomList<BiomeSettingsMobs.c>> g;
   private final Map<EntityTypes<?>, BiomeSettingsMobs.b> h;

   BiomeSettingsMobs(float var0, Map<EnumCreatureType, WeightedRandomList<BiomeSettingsMobs.c>> var1, Map<EntityTypes<?>, BiomeSettingsMobs.b> var2) {
      this.f = var0;
      this.g = ImmutableMap.copyOf(var1);
      this.h = ImmutableMap.copyOf(var2);
   }

   public WeightedRandomList<BiomeSettingsMobs.c> a(EnumCreatureType var0) {
      return this.g.getOrDefault(var0, a);
   }

   @Nullable
   public BiomeSettingsMobs.b a(EntityTypes<?> var0) {
      return this.h.get(var0);
   }

   public float a() {
      return this.f;
   }

   public static class a {
      private final Map<EnumCreatureType, List<BiomeSettingsMobs.c>> a = Stream.of(EnumCreatureType.values())
         .collect(ImmutableMap.toImmutableMap(var0 -> var0, var0 -> Lists.newArrayList()));
      private final Map<EntityTypes<?>, BiomeSettingsMobs.b> b = Maps.newLinkedHashMap();
      private float c = 0.1F;

      public BiomeSettingsMobs.a a(EnumCreatureType var0, BiomeSettingsMobs.c var1) {
         this.a.get(var0).add(var1);
         return this;
      }

      public BiomeSettingsMobs.a a(EntityTypes<?> var0, double var1, double var3) {
         this.b.put(var0, new BiomeSettingsMobs.b(var3, var1));
         return this;
      }

      public BiomeSettingsMobs.a a(float var0) {
         this.c = var0;
         return this;
      }

      public BiomeSettingsMobs a() {
         return new BiomeSettingsMobs(
            this.c,
            this.a.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> WeightedRandomList.a((List)var0.getValue()))),
            ImmutableMap.copyOf(this.b)
         );
      }
   }

   public static record b(double energyBudget, double charge) {
      private final double b;
      private final double c;
      public static final Codec<BiomeSettingsMobs.b> a = RecordCodecBuilder.create(
         var0 -> var0.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter(var0x -> var0x.b), Codec.DOUBLE.fieldOf("charge").forGetter(var0x -> var0x.c))
               .apply(var0, BiomeSettingsMobs.b::new)
      );

      public b(double var0, double var2) {
         this.b = var0;
         this.c = var2;
      }

      public double a() {
         return this.b;
      }

      public double b() {
         return this.c;
      }
   }

   public static class c extends WeightedEntry.a {
      public static final Codec<BiomeSettingsMobs.c> a = ExtraCodecs.a(
         RecordCodecBuilder.create(
            var0 -> var0.group(
                     BuiltInRegistries.h.q().fieldOf("type").forGetter(var0x -> var0x.b),
                     Weight.a.fieldOf("weight").forGetter(WeightedEntry.a::a),
                     ExtraCodecs.i.fieldOf("minCount").forGetter(var0x -> var0x.c),
                     ExtraCodecs.i.fieldOf("maxCount").forGetter(var0x -> var0x.d)
                  )
                  .apply(var0, BiomeSettingsMobs.c::new)
         ),
         (Function<BiomeSettingsMobs.c, DataResult<BiomeSettingsMobs.c>>)(var0 -> var0.c > var0.d
               ? DataResult.error(() -> "minCount needs to be smaller or equal to maxCount")
               : DataResult.success(var0))
      );
      public final EntityTypes<?> b;
      public final int c;
      public final int d;

      public c(EntityTypes<?> var0, int var1, int var2, int var3) {
         this(var0, Weight.a(var1), var2, var3);
      }

      public c(EntityTypes<?> var0, Weight var1, int var2, int var3) {
         super(var1);
         this.b = var0.f() == EnumCreatureType.h ? EntityTypes.av : var0;
         this.c = var2;
         this.d = var3;
      }

      @Override
      public String toString() {
         return EntityTypes.a(this.b) + "*(" + this.c + "-" + this.d + "):" + this.a();
      }
   }
}
