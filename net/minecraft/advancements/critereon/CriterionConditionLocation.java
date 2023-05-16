package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.slf4j.Logger;

public class CriterionConditionLocation {
   private static final Logger b = LogUtils.getLogger();
   public static final CriterionConditionLocation a = new CriterionConditionLocation(
      CriterionConditionValue.DoubleRange.e,
      CriterionConditionValue.DoubleRange.e,
      CriterionConditionValue.DoubleRange.e,
      null,
      null,
      null,
      null,
      CriterionConditionLight.a,
      CriterionConditionBlock.a,
      CriterionConditionFluid.a
   );
   private final CriterionConditionValue.DoubleRange c;
   private final CriterionConditionValue.DoubleRange d;
   private final CriterionConditionValue.DoubleRange e;
   @Nullable
   private final ResourceKey<BiomeBase> f;
   @Nullable
   private final ResourceKey<Structure> g;
   @Nullable
   private final ResourceKey<World> h;
   @Nullable
   private final Boolean i;
   private final CriterionConditionLight j;
   private final CriterionConditionBlock k;
   private final CriterionConditionFluid l;

   public CriterionConditionLocation(
      CriterionConditionValue.DoubleRange var0,
      CriterionConditionValue.DoubleRange var1,
      CriterionConditionValue.DoubleRange var2,
      @Nullable ResourceKey<BiomeBase> var3,
      @Nullable ResourceKey<Structure> var4,
      @Nullable ResourceKey<World> var5,
      @Nullable Boolean var6,
      CriterionConditionLight var7,
      CriterionConditionBlock var8,
      CriterionConditionFluid var9
   ) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.h = var5;
      this.i = var6;
      this.j = var7;
      this.k = var8;
      this.l = var9;
   }

   public static CriterionConditionLocation a(ResourceKey<BiomeBase> var0) {
      return new CriterionConditionLocation(
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         var0,
         null,
         null,
         null,
         CriterionConditionLight.a,
         CriterionConditionBlock.a,
         CriterionConditionFluid.a
      );
   }

   public static CriterionConditionLocation b(ResourceKey<World> var0) {
      return new CriterionConditionLocation(
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         null,
         null,
         var0,
         null,
         CriterionConditionLight.a,
         CriterionConditionBlock.a,
         CriterionConditionFluid.a
      );
   }

   public static CriterionConditionLocation c(ResourceKey<Structure> var0) {
      return new CriterionConditionLocation(
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         CriterionConditionValue.DoubleRange.e,
         null,
         var0,
         null,
         null,
         CriterionConditionLight.a,
         CriterionConditionBlock.a,
         CriterionConditionFluid.a
      );
   }

   public static CriterionConditionLocation a(CriterionConditionValue.DoubleRange var0) {
      return new CriterionConditionLocation(
         CriterionConditionValue.DoubleRange.e,
         var0,
         CriterionConditionValue.DoubleRange.e,
         null,
         null,
         null,
         null,
         CriterionConditionLight.a,
         CriterionConditionBlock.a,
         CriterionConditionFluid.a
      );
   }

   public boolean a(WorldServer var0, double var1, double var3, double var5) {
      if (!this.c.d(var1)) {
         return false;
      } else if (!this.d.d(var3)) {
         return false;
      } else if (!this.e.d(var5)) {
         return false;
      } else if (this.h != null && this.h != var0.ab()) {
         return false;
      } else {
         BlockPosition var7 = BlockPosition.a(var1, var3, var5);
         boolean var8 = var0.o(var7);
         if (this.f == null || var8 && var0.v(var7).a(this.f)) {
            if (this.g == null || var8 && var0.a().a(var7, this.g).b()) {
               if (this.i == null || var8 && this.i == BlockCampfire.a(var0, var7)) {
                  if (!this.j.a(var0, var7)) {
                     return false;
                  } else if (!this.k.a(var0, var7)) {
                     return false;
                  } else {
                     return this.l.a(var0, var7);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (!this.c.c() || !this.d.c() || !this.e.c()) {
            JsonObject var1 = new JsonObject();
            var1.add("x", this.c.d());
            var1.add("y", this.d.d());
            var1.add("z", this.e.d());
            var0.add("position", var1);
         }

         if (this.h != null) {
            World.g.encodeStart(JsonOps.INSTANCE, this.h).resultOrPartial(b::error).ifPresent(var1x -> var0.add("dimension", var1x));
         }

         if (this.g != null) {
            var0.addProperty("structure", this.g.a().toString());
         }

         if (this.f != null) {
            var0.addProperty("biome", this.f.a().toString());
         }

         if (this.i != null) {
            var0.addProperty("smokey", this.i);
         }

         var0.add("light", this.j.a());
         var0.add("block", this.k.a());
         var0.add("fluid", this.l.a());
         return var0;
      }
   }

   public static CriterionConditionLocation a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "location");
         JsonObject var2 = ChatDeserializer.a(var1, "position", new JsonObject());
         CriterionConditionValue.DoubleRange var3 = CriterionConditionValue.DoubleRange.a(var2.get("x"));
         CriterionConditionValue.DoubleRange var4 = CriterionConditionValue.DoubleRange.a(var2.get("y"));
         CriterionConditionValue.DoubleRange var5 = CriterionConditionValue.DoubleRange.a(var2.get("z"));
         ResourceKey<World> var6 = var1.has("dimension")
            ? MinecraftKey.a
               .parse(JsonOps.INSTANCE, var1.get("dimension"))
               .resultOrPartial(b::error)
               .map(var0x -> ResourceKey.a(Registries.aF, var0x))
               .orElse(null)
            : null;
         ResourceKey<Structure> var7 = var1.has("structure")
            ? MinecraftKey.a
               .parse(JsonOps.INSTANCE, var1.get("structure"))
               .resultOrPartial(b::error)
               .map(var0x -> ResourceKey.a(Registries.ax, var0x))
               .orElse(null)
            : null;
         ResourceKey<BiomeBase> var8 = null;
         if (var1.has("biome")) {
            MinecraftKey var9 = new MinecraftKey(ChatDeserializer.h(var1, "biome"));
            var8 = ResourceKey.a(Registries.an, var9);
         }

         Boolean var9 = var1.has("smokey") ? var1.get("smokey").getAsBoolean() : null;
         CriterionConditionLight var10 = CriterionConditionLight.a(var1.get("light"));
         CriterionConditionBlock var11 = CriterionConditionBlock.a(var1.get("block"));
         CriterionConditionFluid var12 = CriterionConditionFluid.a(var1.get("fluid"));
         return new CriterionConditionLocation(var3, var4, var5, var8, var7, var6, var9, var10, var11, var12);
      } else {
         return a;
      }
   }

   public static class a {
      private CriterionConditionValue.DoubleRange a = CriterionConditionValue.DoubleRange.e;
      private CriterionConditionValue.DoubleRange b = CriterionConditionValue.DoubleRange.e;
      private CriterionConditionValue.DoubleRange c = CriterionConditionValue.DoubleRange.e;
      @Nullable
      private ResourceKey<BiomeBase> d;
      @Nullable
      private ResourceKey<Structure> e;
      @Nullable
      private ResourceKey<World> f;
      @Nullable
      private Boolean g;
      private CriterionConditionLight h = CriterionConditionLight.a;
      private CriterionConditionBlock i = CriterionConditionBlock.a;
      private CriterionConditionFluid j = CriterionConditionFluid.a;

      public static CriterionConditionLocation.a a() {
         return new CriterionConditionLocation.a();
      }

      public CriterionConditionLocation.a a(CriterionConditionValue.DoubleRange var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionLocation.a b(CriterionConditionValue.DoubleRange var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionLocation.a c(CriterionConditionValue.DoubleRange var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionLocation.a a(@Nullable ResourceKey<BiomeBase> var0) {
         this.d = var0;
         return this;
      }

      public CriterionConditionLocation.a b(@Nullable ResourceKey<Structure> var0) {
         this.e = var0;
         return this;
      }

      public CriterionConditionLocation.a c(@Nullable ResourceKey<World> var0) {
         this.f = var0;
         return this;
      }

      public CriterionConditionLocation.a a(CriterionConditionLight var0) {
         this.h = var0;
         return this;
      }

      public CriterionConditionLocation.a a(CriterionConditionBlock var0) {
         this.i = var0;
         return this;
      }

      public CriterionConditionLocation.a a(CriterionConditionFluid var0) {
         this.j = var0;
         return this;
      }

      public CriterionConditionLocation.a a(Boolean var0) {
         this.g = var0;
         return this;
      }

      public CriterionConditionLocation b() {
         return new CriterionConditionLocation(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
      }
   }
}
