package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;

public class StructuresBecomeConfiguredFix extends DataFix {
   private static final Map<String, StructuresBecomeConfiguredFix.a> a = ImmutableMap.builder()
      .put(
         "mineshaft",
         StructuresBecomeConfiguredFix.a.a(
            Map.of(List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands"), "minecraft:mineshaft_mesa"), "minecraft:mineshaft"
         )
      )
      .put(
         "shipwreck",
         StructuresBecomeConfiguredFix.a.a(Map.of(List.of("minecraft:beach", "minecraft:snowy_beach"), "minecraft:shipwreck_beached"), "minecraft:shipwreck")
      )
      .put(
         "ocean_ruin",
         StructuresBecomeConfiguredFix.a.a(
            Map.of(List.of("minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean"), "minecraft:ocean_ruin_warm"),
            "minecraft:ocean_ruin_cold"
         )
      )
      .put(
         "village",
         StructuresBecomeConfiguredFix.a.a(
            Map.of(
               List.of("minecraft:desert"),
               "minecraft:village_desert",
               List.of("minecraft:savanna"),
               "minecraft:village_savanna",
               List.of("minecraft:snowy_plains"),
               "minecraft:village_snowy",
               List.of("minecraft:taiga"),
               "minecraft:village_taiga"
            ),
            "minecraft:village_plains"
         )
      )
      .put(
         "ruined_portal",
         StructuresBecomeConfiguredFix.a.a(
            Map.of(
               List.of("minecraft:desert"),
               "minecraft:ruined_portal_desert",
               List.of(
                  "minecraft:badlands",
                  "minecraft:eroded_badlands",
                  "minecraft:wooded_badlands",
                  "minecraft:windswept_hills",
                  "minecraft:windswept_forest",
                  "minecraft:windswept_gravelly_hills",
                  "minecraft:savanna_plateau",
                  "minecraft:windswept_savanna",
                  "minecraft:stony_shore",
                  "minecraft:meadow",
                  "minecraft:frozen_peaks",
                  "minecraft:jagged_peaks",
                  "minecraft:stony_peaks",
                  "minecraft:snowy_slopes"
               ),
               "minecraft:ruined_portal_mountain",
               List.of("minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle"),
               "minecraft:ruined_portal_jungle",
               List.of(
                  "minecraft:deep_frozen_ocean",
                  "minecraft:deep_cold_ocean",
                  "minecraft:deep_ocean",
                  "minecraft:deep_lukewarm_ocean",
                  "minecraft:frozen_ocean",
                  "minecraft:ocean",
                  "minecraft:cold_ocean",
                  "minecraft:lukewarm_ocean",
                  "minecraft:warm_ocean"
               ),
               "minecraft:ruined_portal_ocean"
            ),
            "minecraft:ruined_portal"
         )
      )
      .put("pillager_outpost", StructuresBecomeConfiguredFix.a.a("minecraft:pillager_outpost"))
      .put("mansion", StructuresBecomeConfiguredFix.a.a("minecraft:mansion"))
      .put("jungle_pyramid", StructuresBecomeConfiguredFix.a.a("minecraft:jungle_pyramid"))
      .put("desert_pyramid", StructuresBecomeConfiguredFix.a.a("minecraft:desert_pyramid"))
      .put("igloo", StructuresBecomeConfiguredFix.a.a("minecraft:igloo"))
      .put("swamp_hut", StructuresBecomeConfiguredFix.a.a("minecraft:swamp_hut"))
      .put("stronghold", StructuresBecomeConfiguredFix.a.a("minecraft:stronghold"))
      .put("monument", StructuresBecomeConfiguredFix.a.a("minecraft:monument"))
      .put("fortress", StructuresBecomeConfiguredFix.a.a("minecraft:fortress"))
      .put("endcity", StructuresBecomeConfiguredFix.a.a("minecraft:end_city"))
      .put("buried_treasure", StructuresBecomeConfiguredFix.a.a("minecraft:buried_treasure"))
      .put("nether_fossil", StructuresBecomeConfiguredFix.a.a("minecraft:nether_fossil"))
      .put("bastion_remnant", StructuresBecomeConfiguredFix.a.a("minecraft:bastion_remnant"))
      .build();

   public StructuresBecomeConfiguredFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = this.getInputSchema().getType(DataConverterTypes.c);
      return this.writeFixAndRead("StucturesToConfiguredStructures", var0, var1, this::a);
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      return var0.update("structures", var1x -> var1x.update("starts", var1xx -> this.a(var1xx, var0)).update("References", var1xx -> this.b(var1xx, var0)));
   }

   private Dynamic<?> a(Dynamic<?> var0, Dynamic<?> var1) {
      Map<? extends Dynamic<?>, ? extends Dynamic<?>> var2 = (Map)var0.getMapValues().result().get();
      List<Dynamic<?>> var3 = new ArrayList();
      var2.forEach((var1x, var2x) -> {
         if (var2x.get("id").asString("INVALID").equals("INVALID")) {
            var3.add(var1x);
         }
      });

      for(Dynamic<?> var5 : var3) {
         var0 = var0.remove(var5.asString(""));
      }

      return var0.updateMapValues(var1x -> this.a(var1x, var1));
   }

   private Pair<Dynamic<?>, Dynamic<?>> a(Pair<Dynamic<?>, Dynamic<?>> var0, Dynamic<?> var1) {
      Dynamic<?> var2 = this.c(var0, var1);
      return new Pair(var2, ((Dynamic)var0.getSecond()).set("id", var2));
   }

   private Dynamic<?> b(Dynamic<?> var0, Dynamic<?> var1) {
      Map<? extends Dynamic<?>, ? extends Dynamic<?>> var2 = (Map)var0.getMapValues().result().get();
      List<Dynamic<?>> var3 = new ArrayList();
      var2.forEach((var1x, var2x) -> {
         if (var2x.asLongStream().count() == 0L) {
            var3.add(var1x);
         }
      });

      for(Dynamic<?> var5 : var3) {
         var0 = var0.remove(var5.asString(""));
      }

      return var0.updateMapValues(var1x -> this.b(var1x, var1));
   }

   private Pair<Dynamic<?>, Dynamic<?>> b(Pair<Dynamic<?>, Dynamic<?>> var0, Dynamic<?> var1) {
      return var0.mapFirst(var2x -> this.c(var0, var1));
   }

   private Dynamic<?> c(Pair<Dynamic<?>, Dynamic<?>> var0, Dynamic<?> var1) {
      String var2 = ((Dynamic)var0.getFirst()).asString("UNKNOWN").toLowerCase(Locale.ROOT);
      StructuresBecomeConfiguredFix.a var3 = a.get(var2);
      if (var3 == null) {
         throw new IllegalStateException("Found unknown structure: " + var2);
      } else {
         Dynamic<?> var4 = (Dynamic)var0.getSecond();
         String var5 = var3.b;
         if (!var3.a().isEmpty()) {
            Optional<String> var6 = this.a(var1, var3);
            if (var6.isPresent()) {
               var5 = var6.get();
            }
         }

         Dynamic<?> var6 = var4.createString(var5);
         return var6;
      }
   }

   private Optional<String> a(Dynamic<?> var0, StructuresBecomeConfiguredFix.a var1) {
      Object2IntArrayMap<String> var2 = new Object2IntArrayMap();
      var0.get("sections").asList(Function.identity()).forEach(var2x -> var2x.get("biomes").get("palette").asList(Function.identity()).forEach(var2xx -> {
            String var3x = var1.a().get(var2xx.asString(""));
            if (var3x != null) {
               var2.mergeInt(var3x, 1, Integer::sum);
            }
         }));
      return var2.object2IntEntrySet()
         .stream()
         .max(Comparator.comparingInt(it.unimi.dsi.fastutil.objects.Object2IntMap.Entry::getIntValue))
         .map(Entry::getKey);
   }

   static record a(Map<String, String> biomeMapping, String fallback) {
      private final Map<String, String> a;
      final String b;

      private a(Map<String, String> var0, String var1) {
         this.a = var0;
         this.b = var1;
      }

      public static StructuresBecomeConfiguredFix.a a(String var0) {
         return new StructuresBecomeConfiguredFix.a(Map.of(), var0);
      }

      public static StructuresBecomeConfiguredFix.a a(Map<List<String>, String> var0, String var1) {
         return new StructuresBecomeConfiguredFix.a(a(var0), var1);
      }

      private static Map<String, String> a(Map<List<String>, String> var0) {
         Builder<String, String> var1 = ImmutableMap.builder();

         for(Entry<List<String>, String> var3 : var0.entrySet()) {
            var3.getKey().forEach(var2 -> var1.put(var2, var3.getValue()));
         }

         return var1.build();
      }
   }
}
