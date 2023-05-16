package net.minecraft.util.datafix.fixes;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.util.ChatDeserializer;

public class DataConverterLevelDataGeneratorOptions extends DataFix {
   static final Map<String, String> a = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put("0", "minecraft:ocean");
      var0.put("1", "minecraft:plains");
      var0.put("2", "minecraft:desert");
      var0.put("3", "minecraft:mountains");
      var0.put("4", "minecraft:forest");
      var0.put("5", "minecraft:taiga");
      var0.put("6", "minecraft:swamp");
      var0.put("7", "minecraft:river");
      var0.put("8", "minecraft:nether");
      var0.put("9", "minecraft:the_end");
      var0.put("10", "minecraft:frozen_ocean");
      var0.put("11", "minecraft:frozen_river");
      var0.put("12", "minecraft:snowy_tundra");
      var0.put("13", "minecraft:snowy_mountains");
      var0.put("14", "minecraft:mushroom_fields");
      var0.put("15", "minecraft:mushroom_field_shore");
      var0.put("16", "minecraft:beach");
      var0.put("17", "minecraft:desert_hills");
      var0.put("18", "minecraft:wooded_hills");
      var0.put("19", "minecraft:taiga_hills");
      var0.put("20", "minecraft:mountain_edge");
      var0.put("21", "minecraft:jungle");
      var0.put("22", "minecraft:jungle_hills");
      var0.put("23", "minecraft:jungle_edge");
      var0.put("24", "minecraft:deep_ocean");
      var0.put("25", "minecraft:stone_shore");
      var0.put("26", "minecraft:snowy_beach");
      var0.put("27", "minecraft:birch_forest");
      var0.put("28", "minecraft:birch_forest_hills");
      var0.put("29", "minecraft:dark_forest");
      var0.put("30", "minecraft:snowy_taiga");
      var0.put("31", "minecraft:snowy_taiga_hills");
      var0.put("32", "minecraft:giant_tree_taiga");
      var0.put("33", "minecraft:giant_tree_taiga_hills");
      var0.put("34", "minecraft:wooded_mountains");
      var0.put("35", "minecraft:savanna");
      var0.put("36", "minecraft:savanna_plateau");
      var0.put("37", "minecraft:badlands");
      var0.put("38", "minecraft:wooded_badlands_plateau");
      var0.put("39", "minecraft:badlands_plateau");
      var0.put("40", "minecraft:small_end_islands");
      var0.put("41", "minecraft:end_midlands");
      var0.put("42", "minecraft:end_highlands");
      var0.put("43", "minecraft:end_barrens");
      var0.put("44", "minecraft:warm_ocean");
      var0.put("45", "minecraft:lukewarm_ocean");
      var0.put("46", "minecraft:cold_ocean");
      var0.put("47", "minecraft:deep_warm_ocean");
      var0.put("48", "minecraft:deep_lukewarm_ocean");
      var0.put("49", "minecraft:deep_cold_ocean");
      var0.put("50", "minecraft:deep_frozen_ocean");
      var0.put("127", "minecraft:the_void");
      var0.put("129", "minecraft:sunflower_plains");
      var0.put("130", "minecraft:desert_lakes");
      var0.put("131", "minecraft:gravelly_mountains");
      var0.put("132", "minecraft:flower_forest");
      var0.put("133", "minecraft:taiga_mountains");
      var0.put("134", "minecraft:swamp_hills");
      var0.put("140", "minecraft:ice_spikes");
      var0.put("149", "minecraft:modified_jungle");
      var0.put("151", "minecraft:modified_jungle_edge");
      var0.put("155", "minecraft:tall_birch_forest");
      var0.put("156", "minecraft:tall_birch_hills");
      var0.put("157", "minecraft:dark_forest_hills");
      var0.put("158", "minecraft:snowy_taiga_mountains");
      var0.put("160", "minecraft:giant_spruce_taiga");
      var0.put("161", "minecraft:giant_spruce_taiga_hills");
      var0.put("162", "minecraft:modified_gravelly_mountains");
      var0.put("163", "minecraft:shattered_savanna");
      var0.put("164", "minecraft:shattered_savanna_plateau");
      var0.put("165", "minecraft:eroded_badlands");
      var0.put("166", "minecraft:modified_wooded_badlands_plateau");
      var0.put("167", "minecraft:modified_badlands_plateau");
   });
   public static final String b = "generatorOptions";

   public DataConverterLevelDataGeneratorOptions(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.a);
      return this.fixTypeEverywhereTyped(
         "LevelDataGeneratorOptionsFix", this.getInputSchema().getType(DataConverterTypes.a), var0, var1x -> (Typed)var1x.write().flatMap(var1xx -> {
               Optional<String> var2 = var1xx.get("generatorOptions").asString().result();
               Dynamic<?> var3;
               if ("flat".equalsIgnoreCase(var1xx.get("generatorName").asString(""))) {
                  String var4 = var2.orElse("");
                  var3 = var1xx.set("generatorOptions", a(var4, var1xx.getOps()));
               } else if ("buffet".equalsIgnoreCase(var1xx.get("generatorName").asString("")) && var2.isPresent()) {
                  Dynamic<JsonElement> var4 = new Dynamic(JsonOps.INSTANCE, ChatDeserializer.a(var2.get(), true));
                  var3 = var1xx.set("generatorOptions", var4.convert(var1xx.getOps()));
               } else {
                  var3 = var1xx;
               }
   
               return var0.readTyped(var3);
            }).map(Pair::getFirst).result().orElseThrow(() -> new IllegalStateException("Could not read new level type."))
      );
   }

   private static <T> Dynamic<T> a(String var0, DynamicOps<T> var1) {
      Iterator<String> var2 = Splitter.on(';').split(var0).iterator();
      String var4 = "minecraft:plains";
      Map<String, Map<String, String>> var5 = Maps.newHashMap();
      List<Pair<Integer, String>> var3;
      if (!var0.isEmpty() && var2.hasNext()) {
         var3 = b(var2.next());
         if (!var3.isEmpty()) {
            if (var2.hasNext()) {
               var4 = a.getOrDefault(var2.next(), "minecraft:plains");
            }

            if (var2.hasNext()) {
               String[] var6 = var2.next().toLowerCase(Locale.ROOT).split(",");

               for(String var10 : var6) {
                  String[] var11 = var10.split("\\(", 2);
                  if (!var11[0].isEmpty()) {
                     var5.put(var11[0], Maps.newHashMap());
                     if (var11.length > 1 && var11[1].endsWith(")") && var11[1].length() > 1) {
                        String[] var12 = var11[1].substring(0, var11[1].length() - 1).split(" ");

                        for(String var16 : var12) {
                           String[] var17 = var16.split("=", 2);
                           if (var17.length == 2) {
                              var5.get(var11[0]).put(var17[0], var17[1]);
                           }
                        }
                     }
                  }
               }
            } else {
               var5.put("village", Maps.newHashMap());
            }
         }
      } else {
         var3 = Lists.newArrayList();
         var3.add(Pair.of(1, "minecraft:bedrock"));
         var3.add(Pair.of(2, "minecraft:dirt"));
         var3.add(Pair.of(1, "minecraft:grass_block"));
         var5.put("village", Maps.newHashMap());
      }

      T var6 = (T)var1.createList(
         var3.stream()
            .map(
               var1x -> var1.createMap(
                     ImmutableMap.of(
                        var1.createString("height"),
                        var1.createInt(var1x.getFirst()),
                        var1.createString("block"),
                        var1.createString((String)var1x.getSecond())
                     )
                  )
            )
      );
      T var7 = (T)var1.createMap(
         var5.entrySet()
            .stream()
            .map(
               var1x -> Pair.of(
                     var1.createString(((String)var1x.getKey()).toLowerCase(Locale.ROOT)),
                     var1.createMap(
                        ((Map)var1x.getValue())
                           .entrySet()
                           .stream()
                           .map(var1xx -> Pair.of(var1.createString((String)var1xx.getKey()), var1.createString((String)var1xx.getValue())))
                           .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
                     )
                  )
            )
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
      );
      return new Dynamic(
         var1,
         var1.createMap(
            ImmutableMap.of(var1.createString("layers"), var6, var1.createString("biome"), var1.createString(var4), var1.createString("structures"), var7)
         )
      );
   }

   @Nullable
   private static Pair<Integer, String> a(String var0) {
      String[] var1 = var0.split("\\*", 2);
      int var2;
      if (var1.length == 2) {
         try {
            var2 = Integer.parseInt(var1[0]);
         } catch (NumberFormatException var4) {
            return null;
         }
      } else {
         var2 = 1;
      }

      String var3 = var1[var1.length - 1];
      return Pair.of(var2, var3);
   }

   private static List<Pair<Integer, String>> b(String var0) {
      List<Pair<Integer, String>> var1 = Lists.newArrayList();
      String[] var2 = var0.split(",");

      for(String var6 : var2) {
         Pair<Integer, String> var7 = a(var6);
         if (var7 == null) {
            return Collections.emptyList();
         }

         var1.add(var7);
      }

      return var1;
   }
}
