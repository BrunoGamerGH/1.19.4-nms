package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

public class WorldGenFeatureDefinedStructureJigsawJunction {
   private final int a;
   private final int b;
   private final int c;
   private final int d;
   private final WorldGenFeatureDefinedStructurePoolTemplate.Matching e;

   public WorldGenFeatureDefinedStructureJigsawJunction(int var0, int var1, int var2, int var3, WorldGenFeatureDefinedStructurePoolTemplate.Matching var4) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public WorldGenFeatureDefinedStructurePoolTemplate.Matching e() {
      return this.e;
   }

   public <T> Dynamic<T> a(DynamicOps<T> var0) {
      Builder<T, T> var1 = ImmutableMap.builder();
      var1.put(var0.createString("source_x"), var0.createInt(this.a))
         .put(var0.createString("source_ground_y"), var0.createInt(this.b))
         .put(var0.createString("source_z"), var0.createInt(this.c))
         .put(var0.createString("delta_y"), var0.createInt(this.d))
         .put(var0.createString("dest_proj"), var0.createString(this.e.a()));
      return new Dynamic(var0, var0.createMap(var1.build()));
   }

   public static <T> WorldGenFeatureDefinedStructureJigsawJunction a(Dynamic<T> var0) {
      return new WorldGenFeatureDefinedStructureJigsawJunction(
         var0.get("source_x").asInt(0),
         var0.get("source_ground_y").asInt(0),
         var0.get("source_z").asInt(0),
         var0.get("delta_y").asInt(0),
         WorldGenFeatureDefinedStructurePoolTemplate.Matching.a(var0.get("dest_proj").asString(""))
      );
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         WorldGenFeatureDefinedStructureJigsawJunction var1 = (WorldGenFeatureDefinedStructureJigsawJunction)var0;
         if (this.a != var1.a) {
            return false;
         } else if (this.c != var1.c) {
            return false;
         } else if (this.d != var1.d) {
            return false;
         } else {
            return this.e == var1.e;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.a;
      var0 = 31 * var0 + this.b;
      var0 = 31 * var0 + this.c;
      var0 = 31 * var0 + this.d;
      return 31 * var0 + this.e.hashCode();
   }

   @Override
   public String toString() {
      return "JigsawJunction{sourceX="
         + this.a
         + ", sourceGroundY="
         + this.b
         + ", sourceZ="
         + this.c
         + ", deltaY="
         + this.d
         + ", destProjection="
         + this.e
         + "}";
   }
}
