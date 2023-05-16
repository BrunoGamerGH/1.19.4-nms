package net.minecraft.world.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;

public record MobSpawnerData(NBTTagCompound entityToSpawn, Optional<MobSpawnerData.a> customSpawnRules) {
   private final NBTTagCompound d;
   private final Optional<MobSpawnerData.a> e;
   public static final String a = "entity";
   public static final Codec<MobSpawnerData> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               NBTTagCompound.a.fieldOf("entity").forGetter(var0x -> var0x.d),
               MobSpawnerData.a.a.optionalFieldOf("custom_spawn_rules").forGetter(var0x -> var0x.e)
            )
            .apply(var0, MobSpawnerData::new)
   );
   public static final Codec<SimpleWeightedRandomList<MobSpawnerData>> c = SimpleWeightedRandomList.a(b);

   public MobSpawnerData() {
      this(new NBTTagCompound(), Optional.empty());
   }

   public MobSpawnerData(NBTTagCompound var0, Optional<MobSpawnerData.a> var1) {
      if (var0.e("id")) {
         MinecraftKey var2 = MinecraftKey.a(var0.l("id"));
         if (var2 != null) {
            var0.a("id", var2.toString());
         } else {
            var0.r("id");
         }
      }

      this.d = var0;
      this.e = var1;
   }

   public NBTTagCompound a() {
      return this.d;
   }

   public Optional<MobSpawnerData.a> b() {
      return this.e;
   }

   public NBTTagCompound c() {
      return this.d;
   }

   public Optional<MobSpawnerData.a> d() {
      return this.e;
   }

   public static record a(InclusiveRange<Integer> blockLightLimit, InclusiveRange<Integer> skyLightLimit) {
      private final InclusiveRange<Integer> b;
      private final InclusiveRange<Integer> c;
      private static final InclusiveRange<Integer> d = new InclusiveRange<>(0, 15);
      public static final Codec<MobSpawnerData.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  InclusiveRange.a.optionalFieldOf("block_light_limit", d).flatXmap(MobSpawnerData.a::a, MobSpawnerData.a::a).forGetter(var0x -> var0x.b),
                  InclusiveRange.a.optionalFieldOf("sky_light_limit", d).flatXmap(MobSpawnerData.a::a, MobSpawnerData.a::a).forGetter(var0x -> var0x.c)
               )
               .apply(var0, MobSpawnerData.a::new)
      );

      public a(InclusiveRange<Integer> var0, InclusiveRange<Integer> var1) {
         this.b = var0;
         this.c = var1;
      }

      private static DataResult<InclusiveRange<Integer>> a(InclusiveRange<Integer> var0) {
         return !d.a(var0) ? DataResult.error(() -> "Light values must be withing range " + d) : DataResult.success(var0);
      }

      public InclusiveRange<Integer> a() {
         return this.b;
      }

      public InclusiveRange<Integer> b() {
         return this.c;
      }
   }
}
