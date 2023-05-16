package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.logging.LogUtils;
import java.util.Locale;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class LootItemFunctionExplorationMap extends LootItemFunctionConditional {
   static final Logger h = LogUtils.getLogger();
   public static final TagKey<Structure> a = StructureTags.e;
   public static final String b = "mansion";
   public static final MapIcon.Type c = MapIcon.Type.i;
   public static final byte d = 2;
   public static final int e = 50;
   public static final boolean f = true;
   final TagKey<Structure> i;
   final MapIcon.Type j;
   final byte k;
   final int l;
   final boolean m;

   LootItemFunctionExplorationMap(LootItemCondition[] var0, TagKey<Structure> var1, MapIcon.Type var2, byte var3, int var4, boolean var5) {
      super(var0);
      this.i = var1;
      this.j = var2;
      this.k = var3;
      this.l = var4;
      this.m = var5;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.l;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.f);
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (!var0.a(Items.tl)) {
         return var0;
      } else {
         Vec3D var2 = var1.c(LootContextParameters.f);
         if (var2 != null) {
            WorldServer var3 = var1.c();
            BlockPosition var4 = var3.a(this.i, BlockPosition.a(var2), this.l, this.m);
            if (var4 != null) {
               ItemStack var5 = ItemWorldMap.a(var3, var4.u(), var4.w(), this.k, true, true);
               ItemWorldMap.a(var3, var5);
               WorldMap.a(var5, var4, "+", this.j);
               return var5;
            }
         }

         return var0;
      }
   }

   public static LootItemFunctionExplorationMap.a c() {
      return new LootItemFunctionExplorationMap.a();
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionExplorationMap.a> {
      private TagKey<Structure> a;
      private MapIcon.Type b;
      private byte c;
      private int d;
      private boolean e;

      public a() {
         this.a = LootItemFunctionExplorationMap.a;
         this.b = LootItemFunctionExplorationMap.c;
         this.c = 2;
         this.d = 50;
         this.e = true;
      }

      protected LootItemFunctionExplorationMap.a a() {
         return this;
      }

      public LootItemFunctionExplorationMap.a a(TagKey<Structure> var0) {
         this.a = var0;
         return this;
      }

      public LootItemFunctionExplorationMap.a a(MapIcon.Type var0) {
         this.b = var0;
         return this;
      }

      public LootItemFunctionExplorationMap.a a(byte var0) {
         this.c = var0;
         return this;
      }

      public LootItemFunctionExplorationMap.a a(int var0) {
         this.d = var0;
         return this;
      }

      public LootItemFunctionExplorationMap.a a(boolean var0) {
         this.e = var0;
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionExplorationMap(this.g(), this.a, this.b, this.c, this.d, this.e);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionExplorationMap> {
      public void a(JsonObject var0, LootItemFunctionExplorationMap var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         if (!var1.i.equals(LootItemFunctionExplorationMap.a)) {
            var0.addProperty("destination", var1.i.b().toString());
         }

         if (var1.j != LootItemFunctionExplorationMap.c) {
            var0.add("decoration", var2.serialize(var1.j.toString().toLowerCase(Locale.ROOT)));
         }

         if (var1.k != 2) {
            var0.addProperty("zoom", var1.k);
         }

         if (var1.l != 50) {
            var0.addProperty("search_radius", var1.l);
         }

         if (!var1.m) {
            var0.addProperty("skip_existing_chunks", var1.m);
         }
      }

      public LootItemFunctionExplorationMap a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         TagKey<Structure> var3 = a(var0);
         String var4 = var0.has("decoration") ? ChatDeserializer.h(var0, "decoration") : "mansion";
         MapIcon.Type var5 = LootItemFunctionExplorationMap.c;

         try {
            var5 = MapIcon.Type.valueOf(var4.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var10) {
            LootItemFunctionExplorationMap.h
               .error("Error while parsing loot table decoration entry. Found {}. Defaulting to {}", var4, LootItemFunctionExplorationMap.c);
         }

         byte var6 = ChatDeserializer.a(var0, "zoom", (byte)2);
         int var7 = ChatDeserializer.a(var0, "search_radius", 50);
         boolean var8 = ChatDeserializer.a(var0, "skip_existing_chunks", true);
         return new LootItemFunctionExplorationMap(var2, var3, var5, var6, var7, var8);
      }

      private static TagKey<Structure> a(JsonObject var0) {
         if (var0.has("destination")) {
            String var1 = ChatDeserializer.h(var0, "destination");
            return TagKey.a(Registries.ax, new MinecraftKey(var1));
         } else {
            return LootItemFunctionExplorationMap.a;
         }
      }
   }
}
