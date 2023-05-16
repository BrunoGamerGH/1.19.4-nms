package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.INamable;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.biome.BiomeSettingsMobs;

public record StructureSpawnOverride(StructureSpawnOverride.a boundingBox, WeightedRandomList<BiomeSettingsMobs.c> spawns) {
   private final StructureSpawnOverride.a b;
   private final WeightedRandomList<BiomeSettingsMobs.c> c;
   public static final Codec<StructureSpawnOverride> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               StructureSpawnOverride.a.c.fieldOf("bounding_box").forGetter(StructureSpawnOverride::a),
               WeightedRandomList.c(BiomeSettingsMobs.c.a).fieldOf("spawns").forGetter(StructureSpawnOverride::b)
            )
            .apply(var0, StructureSpawnOverride::new)
   );

   public StructureSpawnOverride(StructureSpawnOverride.a var0, WeightedRandomList<BiomeSettingsMobs.c> var1) {
      this.b = var0;
      this.c = var1;
   }

   public StructureSpawnOverride.a a() {
      return this.b;
   }

   public WeightedRandomList<BiomeSettingsMobs.c> b() {
      return this.c;
   }

   public static enum a implements INamable {
      a("piece"),
      b("full");

      public static final Codec<StructureSpawnOverride.a> c = INamable.a(StructureSpawnOverride.a::values);
      private final String d;

      private a(String var2) {
         this.d = var2;
      }

      @Override
      public String c() {
         return this.d;
      }
   }
}
