package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

public class ChunkHeightAndBiomeFix extends DataFix {
   public static final String a = "__context";
   private static final String d = "ChunkHeightAndBiomeFix";
   private static final int e = 16;
   private static final int f = 24;
   private static final int g = -4;
   public static final int b = 4096;
   private static final int h = 64;
   private static final int i = 9;
   private static final long j = 511L;
   private static final int k = 64;
   private static final String[] l = new String[]{
      "WORLD_SURFACE_WG", "WORLD_SURFACE", "WORLD_SURFACE_IGNORE_SNOW", "OCEAN_FLOOR_WG", "OCEAN_FLOOR", "MOTION_BLOCKING", "MOTION_BLOCKING_NO_LEAVES"
   };
   private static final Set<String> m = Set.of("surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
   private static final Set<String> n = Set.of("noise", "surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
   private static final Set<String> o = Set.of(
      "minecraft:air",
      "minecraft:basalt",
      "minecraft:bedrock",
      "minecraft:blackstone",
      "minecraft:calcite",
      "minecraft:cave_air",
      "minecraft:coarse_dirt",
      "minecraft:crimson_nylium",
      "minecraft:dirt",
      "minecraft:end_stone",
      "minecraft:grass_block",
      "minecraft:gravel",
      "minecraft:ice",
      "minecraft:lava",
      "minecraft:mycelium",
      "minecraft:nether_wart_block",
      "minecraft:netherrack",
      "minecraft:orange_terracotta",
      "minecraft:packed_ice",
      "minecraft:podzol",
      "minecraft:powder_snow",
      "minecraft:red_sand",
      "minecraft:red_sandstone",
      "minecraft:sand",
      "minecraft:sandstone",
      "minecraft:snow_block",
      "minecraft:soul_sand",
      "minecraft:soul_soil",
      "minecraft:stone",
      "minecraft:terracotta",
      "minecraft:warped_nylium",
      "minecraft:warped_wart_block",
      "minecraft:water",
      "minecraft:white_terracotta"
   );
   private static final int p = 16;
   private static final int q = 64;
   private static final int r = 1008;
   public static final String c = "minecraft:plains";
   private static final Int2ObjectMap<String> s = new Int2ObjectOpenHashMap();

   public ChunkHeightAndBiomeFix(Schema var0) {
      super(var0, true);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      OpticFinder<?> var1 = var0.findField("Level");
      OpticFinder<?> var2 = var1.type().findField("Sections");
      Schema var3 = this.getOutputSchema();
      Type<?> var4 = var3.getType(DataConverterTypes.c);
      Type<?> var5 = var4.findField("Level").type();
      Type<?> var6 = var5.findField("Sections").type();
      return this.fixTypeEverywhereTyped(
         "ChunkHeightAndBiomeFix",
         var0,
         var4,
         var4x -> var4x.updateTyped(
               var1,
               var5,
               var3xx -> {
                  Dynamic<?> var4xx = (Dynamic)var3xx.get(DSL.remainderFinder());
                  OptionalDynamic<?> var5x = ((Dynamic)var4x.get(DSL.remainderFinder())).get("__context");
                  String var6x = var5x.get("dimension").asString().result().orElse("");
                  String var7x = var5x.get("generator").asString().result().orElse("");
                  boolean var8 = "minecraft:overworld".equals(var6x);
                  MutableBoolean var9 = new MutableBoolean();
                  int var10 = var8 ? -4 : 0;
                  Dynamic<?>[] var11 = a(var4xx, var8, var10, var9);
                  Dynamic<?> var12 = d(
                     var4xx.createList(Stream.of(var4xx.createMap(ImmutableMap.of(var4xx.createString("Name"), var4xx.createString("minecraft:air")))))
                  );
                  Set<String> var13 = Sets.newHashSet();
                  MutableObject<Supplier<ChunkProtoTickListFix.a>> var14 = new MutableObject((Supplier<ChunkProtoTickListFix.a>)() -> null);
                  var3xx = var3xx.updateTyped(
                     var2,
                     var6,
                     var7xx -> {
                        IntSet var8x = new IntOpenHashSet();
                        Dynamic<?> var9x = (Dynamic)var7xx.write().result().orElseThrow(() -> new IllegalStateException("Malformed Chunk.Level.Sections"));
                        List<Dynamic<?>> var10x = var9x.asStream().map(var6xxx -> {
                           int var7xxx = var6xxx.get("Y").asInt(0);
                           Dynamic<?> var8xx = (Dynamic)DataFixUtils.orElse(var6xxx.get("Palette").result().flatMap(var2xxxxx -> {
                              var2xxxxx.asStream().map(var0xxxxxx -> var0xxxxxx.get("Name").asString("minecraft:air")).forEach(var13::add);
                              return var6xxx.get("BlockStates").result().map(var1xxxxxx -> b(var2xxxxx, var1xxxxxx));
                           }), var12);
                           Dynamic<?> var9xx = var6xxx;
                           int var10xx = var7xxx - var10;
                           if (var10xx >= 0 && var10xx < var11.length) {
                              var9xx = var6xxx.set("biomes", var11[var10xx]);
                           }
         
                           var8x.add(var7xxx);
                           if (var6xxx.get("Y").asInt(Integer.MAX_VALUE) == 0) {
                              var14.setValue((Supplier<ChunkProtoTickListFix.a>)() -> {
                                 List<? extends Dynamic<?>> var1xxxxx = var8xx.get("palette").asList(Function.identity());
                                 long[] var2xxxxx = var8xx.get("data").asLongStream().toArray();
                                 return new ChunkProtoTickListFix.a(var1xxxxx, var2xxxxx);
                              });
                           }
         
                           return var9xx.set("block_states", var8xx).remove("Palette").remove("BlockStates");
                        }).collect(Collectors.toCollection(ArrayList::new));
         
                        for(int var11x = 0; var11x < var11.length; ++var11x) {
                           int var12x = var11x + var10;
                           if (var8x.add(var12x)) {
                              Dynamic<?> var13x = var4xx.createMap(Map.of(var4xx.createString("Y"), var4xx.createInt(var12x)));
                              var13x = var13x.set("block_states", var12);
                              var13x = var13x.set("biomes", var11[var11x]);
                              var10x.add(var13x);
                           }
                        }
         
                        return (Typed)((Pair)var6.readTyped(var4xx.createList(var10x.stream()))
                              .result()
                              .orElseThrow(() -> new IllegalStateException("ChunkHeightAndBiomeFix failed.")))
                           .getFirst();
                     }
                  );
                  return var3xx.update(DSL.remainderFinder(), var5xx -> {
                     if (var8) {
                        var5xx = this.a(var5xx, var13);
                     }
      
                     return a(var5xx, var8, var9.booleanValue(), "minecraft:noise".equals(var7x), (Supplier<ChunkProtoTickListFix.a>)var14.getValue());
                  });
               }
            )
      );
   }

   private Dynamic<?> a(Dynamic<?> var0, Set<String> var1) {
      return var0.update("Status", var1x -> {
         String var2x = var1x.asString("empty");
         if (m.contains(var2x)) {
            return var1x;
         } else {
            var1.remove("minecraft:air");
            boolean var3 = !var1.isEmpty();
            var1.removeAll(o);
            boolean var4 = !var1.isEmpty();
            if (var4) {
               return var1x.createString("liquid_carvers");
            } else if ("noise".equals(var2x) || var3) {
               return var1x.createString("noise");
            } else {
               return "biomes".equals(var2x) ? var1x.createString("structure_references") : var1x;
            }
         }
      });
   }

   private static Dynamic<?>[] a(Dynamic<?> var0, boolean var1, int var2, MutableBoolean var3) {
      Dynamic<?>[] var4 = new Dynamic[var1 ? 24 : 16];
      int[] var5 = (int[])var0.get("Biomes").asIntStreamOpt().result().map(IntStream::toArray).orElse(null);
      if (var5 != null && var5.length == 1536) {
         var3.setValue(true);

         for(int var6 = 0; var6 < 24; ++var6) {
            var4[var6] = a(var0, (Int2IntFunction)(var2x -> a(var5, var6 * 64 + var2x)));
         }
      } else if (var5 != null && var5.length == 1024) {
         for(int var6 = 0; var6 < 16; ++var6) {
            int var7 = var6 - var2;
            var4[var7] = a(var0, (Int2IntFunction)(var2x -> a(var5, var6 * 64 + var2x)));
         }

         if (var1) {
            Dynamic<?> var6 = a(var0, (Int2IntFunction)(var1x -> a(var5, var1x % 16)));
            Dynamic<?> var7 = a(var0, (Int2IntFunction)(var1x -> a(var5, var1x % 16 + 1008)));

            for(int var8 = 0; var8 < 4; ++var8) {
               var4[var8] = var6;
            }

            for(int var8 = 20; var8 < 24; ++var8) {
               var4[var8] = var7;
            }
         }
      } else {
         Arrays.fill(var4, d(var0.createList(Stream.of(var0.createString("minecraft:plains")))));
      }

      return var4;
   }

   private static int a(int[] var0, int var1) {
      return var0[var1] & 0xFF;
   }

   private static Dynamic<?> a(Dynamic<?> var0, boolean var1, boolean var2, boolean var3, Supplier<ChunkProtoTickListFix.a> var4) {
      var0 = var0.remove("Biomes");
      if (!var1) {
         return a(var0, 16, 0);
      } else if (var2) {
         return a(var0, 24, 0);
      } else {
         var0 = b(var0);
         var0 = a(var0, "Lights");
         var0 = a(var0, "LiquidsToBeTicked");
         var0 = a(var0, "PostProcessing");
         var0 = a(var0, "ToBeTicked");
         var0 = a(var0, 24, 4);
         var0 = var0.update("UpgradeData", ChunkHeightAndBiomeFix::a);
         if (!var3) {
            return var0;
         } else {
            Optional<? extends Dynamic<?>> var5 = var0.get("Status").result();
            if (var5.isPresent()) {
               Dynamic<?> var6 = (Dynamic)var5.get();
               String var7 = var6.asString("");
               if (!"empty".equals(var7)) {
                  var0 = var0.set("blending_data", var0.createMap(ImmutableMap.of(var0.createString("old_noise"), var0.createBoolean(n.contains(var7)))));
                  ChunkProtoTickListFix.a var8 = var4.get();
                  if (var8 != null) {
                     BitSet var9 = new BitSet(256);
                     boolean var10 = var7.equals("noise");

                     for(int var11 = 0; var11 < 16; ++var11) {
                        for(int var12 = 0; var12 < 16; ++var12) {
                           Dynamic<?> var13 = var8.a(var12, 0, var11);
                           boolean var14 = var13 != null && "minecraft:bedrock".equals(var13.get("Name").asString(""));
                           boolean var15 = var13 != null && "minecraft:air".equals(var13.get("Name").asString(""));
                           if (var15) {
                              var9.set(var11 * 16 + var12);
                           }

                           var10 |= var14;
                        }
                     }

                     if (var10 && var9.cardinality() != var9.size()) {
                        Dynamic<?> var11 = "full".equals(var7) ? var0.createString("heightmaps") : var6;
                        var0 = var0.set(
                           "below_zero_retrogen",
                           var0.createMap(
                              ImmutableMap.of(
                                 var0.createString("target_status"),
                                 var11,
                                 var0.createString("missing_bedrock"),
                                 var0.createLongList(LongStream.of(var9.toLongArray()))
                              )
                           )
                        );
                        var0 = var0.set("Status", var0.createString("empty"));
                     }

                     var0 = var0.set("isLightOn", var0.createBoolean(false));
                  }
               }
            }

            return var0;
         }
      }
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      return var0.update("Indices", var0x -> {
         Map<Dynamic<?>, Dynamic<?>> var1 = new HashMap();
         var0x.getMapValues().result().ifPresent(var1x -> var1x.forEach((var1xx, var2) -> {
               try {
                  var1xx.asString().result().map(Integer::parseInt).ifPresent(var3 -> {
                     int var4x = var3 - -4;
                     var1.put(var1xx.createString(Integer.toString(var4x)), var2);
                  });
               } catch (NumberFormatException var4) {
               }
            }));
         return var0x.createMap(var1);
      });
   }

   private static Dynamic<?> a(Dynamic<?> var0, int var1, int var2) {
      Dynamic<?> var3 = var0.get("CarvingMasks").orElseEmptyMap();
      var3 = var3.updateMapValues(var3x -> {
         long[] var4x = BitSet.valueOf(((Dynamic)var3x.getSecond()).asByteBuffer().array()).toLongArray();
         long[] var5 = new long[64 * var1];
         System.arraycopy(var4x, 0, var5, 64 * var2, var4x.length);
         return Pair.of((Dynamic)var3x.getFirst(), var0.createLongList(LongStream.of(var5)));
      });
      return var0.set("CarvingMasks", var3);
   }

   private static Dynamic<?> a(Dynamic<?> var0, String var1) {
      List<Dynamic<?>> var2 = var0.get(var1).orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
      if (var2.size() == 24) {
         return var0;
      } else {
         Dynamic<?> var3 = var0.emptyList();

         for(int var4 = 0; var4 < 4; ++var4) {
            var2.add(0, var3);
            var2.add(var3);
         }

         return var0.set(var1, var0.createList(var2.stream()));
      }
   }

   private static Dynamic<?> b(Dynamic<?> var0) {
      return var0.update("Heightmaps", var0x -> {
         for(String var4 : l) {
            var0x = var0x.update(var4, ChunkHeightAndBiomeFix::c);
         }

         return var0x;
      });
   }

   private static Dynamic<?> c(Dynamic<?> var0) {
      return var0.createLongList(var0.asLongStream().map(var0x -> {
         long var2 = 0L;

         for(int var4 = 0; var4 + 9 <= 64; var4 += 9) {
            long var5 = var0x >> var4 & 511L;
            long var7;
            if (var5 == 0L) {
               var7 = 0L;
            } else {
               var7 = Math.min(var5 + 64L, 511L);
            }

            var2 |= var7 << var4;
         }

         return var2;
      }));
   }

   private static Dynamic<?> a(Dynamic<?> var0, Int2IntFunction var1) {
      Int2IntMap var2 = new Int2IntLinkedOpenHashMap();

      for(int var3 = 0; var3 < 64; ++var3) {
         int var4 = var1.applyAsInt(var3);
         if (!var2.containsKey(var4)) {
            var2.put(var4, var2.size());
         }
      }

      Dynamic<?> var3 = var0.createList(var2.keySet().stream().map(var1x -> var0.createString((String)s.getOrDefault(var1x, "minecraft:plains"))));
      int var4 = a(var2.size());
      if (var4 == 0) {
         return d(var3);
      } else {
         int var5 = 64 / var4;
         int var6 = (64 + var5 - 1) / var5;
         long[] var7 = new long[var6];
         int var8 = 0;
         int var9 = 0;

         for(int var10 = 0; var10 < 64; ++var10) {
            int var11 = var1.applyAsInt(var10);
            var7[var8] |= (long)var2.get(var11) << var9;
            var9 += var4;
            if (var9 + var4 > 64) {
               ++var8;
               var9 = 0;
            }
         }

         Dynamic<?> var10 = var0.createLongList(Arrays.stream(var7));
         return a(var3, var10);
      }
   }

   private static Dynamic<?> d(Dynamic<?> var0) {
      return var0.createMap(ImmutableMap.of(var0.createString("palette"), var0));
   }

   private static Dynamic<?> a(Dynamic<?> var0, Dynamic<?> var1) {
      return var0.createMap(ImmutableMap.of(var0.createString("palette"), var0, var0.createString("data"), var1));
   }

   private static Dynamic<?> b(Dynamic<?> var0, Dynamic<?> var1) {
      List<Dynamic<?>> var2 = var0.asStream().collect(Collectors.toCollection(ArrayList::new));
      if (var2.size() == 1) {
         return d(var0);
      } else {
         var0 = a(var0, var1, var2);
         return a(var0, var1);
      }
   }

   private static Dynamic<?> a(Dynamic<?> var0, Dynamic<?> var1, List<Dynamic<?>> var2) {
      long var3 = var1.asLongStream().count() * 64L;
      long var5 = var3 / 4096L;
      int var7 = var2.size();
      int var8 = a(var7);
      if (var5 <= (long)var8) {
         return var0;
      } else {
         Dynamic<?> var9 = var0.createMap(ImmutableMap.of(var0.createString("Name"), var0.createString("minecraft:air")));
         int var10 = (1 << (int)(var5 - 1L)) + 1;
         int var11 = var10 - var7;

         for(int var12 = 0; var12 < var11; ++var12) {
            var2.add(var9);
         }

         return var0.createList(var2.stream());
      }
   }

   public static int a(int var0) {
      return var0 == 0 ? 0 : (int)Math.ceil(Math.log((double)var0) / Math.log(2.0));
   }

   static {
      s.put(0, "minecraft:ocean");
      s.put(1, "minecraft:plains");
      s.put(2, "minecraft:desert");
      s.put(3, "minecraft:mountains");
      s.put(4, "minecraft:forest");
      s.put(5, "minecraft:taiga");
      s.put(6, "minecraft:swamp");
      s.put(7, "minecraft:river");
      s.put(8, "minecraft:nether_wastes");
      s.put(9, "minecraft:the_end");
      s.put(10, "minecraft:frozen_ocean");
      s.put(11, "minecraft:frozen_river");
      s.put(12, "minecraft:snowy_tundra");
      s.put(13, "minecraft:snowy_mountains");
      s.put(14, "minecraft:mushroom_fields");
      s.put(15, "minecraft:mushroom_field_shore");
      s.put(16, "minecraft:beach");
      s.put(17, "minecraft:desert_hills");
      s.put(18, "minecraft:wooded_hills");
      s.put(19, "minecraft:taiga_hills");
      s.put(20, "minecraft:mountain_edge");
      s.put(21, "minecraft:jungle");
      s.put(22, "minecraft:jungle_hills");
      s.put(23, "minecraft:jungle_edge");
      s.put(24, "minecraft:deep_ocean");
      s.put(25, "minecraft:stone_shore");
      s.put(26, "minecraft:snowy_beach");
      s.put(27, "minecraft:birch_forest");
      s.put(28, "minecraft:birch_forest_hills");
      s.put(29, "minecraft:dark_forest");
      s.put(30, "minecraft:snowy_taiga");
      s.put(31, "minecraft:snowy_taiga_hills");
      s.put(32, "minecraft:giant_tree_taiga");
      s.put(33, "minecraft:giant_tree_taiga_hills");
      s.put(34, "minecraft:wooded_mountains");
      s.put(35, "minecraft:savanna");
      s.put(36, "minecraft:savanna_plateau");
      s.put(37, "minecraft:badlands");
      s.put(38, "minecraft:wooded_badlands_plateau");
      s.put(39, "minecraft:badlands_plateau");
      s.put(40, "minecraft:small_end_islands");
      s.put(41, "minecraft:end_midlands");
      s.put(42, "minecraft:end_highlands");
      s.put(43, "minecraft:end_barrens");
      s.put(44, "minecraft:warm_ocean");
      s.put(45, "minecraft:lukewarm_ocean");
      s.put(46, "minecraft:cold_ocean");
      s.put(47, "minecraft:deep_warm_ocean");
      s.put(48, "minecraft:deep_lukewarm_ocean");
      s.put(49, "minecraft:deep_cold_ocean");
      s.put(50, "minecraft:deep_frozen_ocean");
      s.put(127, "minecraft:the_void");
      s.put(129, "minecraft:sunflower_plains");
      s.put(130, "minecraft:desert_lakes");
      s.put(131, "minecraft:gravelly_mountains");
      s.put(132, "minecraft:flower_forest");
      s.put(133, "minecraft:taiga_mountains");
      s.put(134, "minecraft:swamp_hills");
      s.put(140, "minecraft:ice_spikes");
      s.put(149, "minecraft:modified_jungle");
      s.put(151, "minecraft:modified_jungle_edge");
      s.put(155, "minecraft:tall_birch_forest");
      s.put(156, "minecraft:tall_birch_hills");
      s.put(157, "minecraft:dark_forest_hills");
      s.put(158, "minecraft:snowy_taiga_mountains");
      s.put(160, "minecraft:giant_spruce_taiga");
      s.put(161, "minecraft:giant_spruce_taiga_hills");
      s.put(162, "minecraft:modified_gravelly_mountains");
      s.put(163, "minecraft:shattered_savanna");
      s.put(164, "minecraft:shattered_savanna_plateau");
      s.put(165, "minecraft:eroded_badlands");
      s.put(166, "minecraft:modified_wooded_badlands_plateau");
      s.put(167, "minecraft:modified_badlands_plateau");
      s.put(168, "minecraft:bamboo_jungle");
      s.put(169, "minecraft:bamboo_jungle_hills");
      s.put(170, "minecraft:soul_sand_valley");
      s.put(171, "minecraft:crimson_forest");
      s.put(172, "minecraft:warped_forest");
      s.put(173, "minecraft:basalt_deltas");
      s.put(174, "minecraft:dripstone_caves");
      s.put(175, "minecraft:lush_caves");
      s.put(177, "minecraft:meadow");
      s.put(178, "minecraft:grove");
      s.put(179, "minecraft:snowy_slopes");
      s.put(180, "minecraft:snowcapped_peaks");
      s.put(181, "minecraft:lofty_peaks");
      s.put(182, "minecraft:stony_peaks");
   }
}
