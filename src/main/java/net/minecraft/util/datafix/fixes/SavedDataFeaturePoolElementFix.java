package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SavedDataFeaturePoolElementFix extends DataFix {
   private static final Pattern a = Pattern.compile("\\[(\\d+)\\]");
   private static final Set<String> b = Sets.newHashSet(
      new String[]{"minecraft:jigsaw", "minecraft:nvi", "minecraft:pcp", "minecraft:bastionremnant", "minecraft:runtime"}
   );
   private static final Set<String> c = Sets.newHashSet(new String[]{"minecraft:tree", "minecraft:flower", "minecraft:block_pile", "minecraft:random_patch"});

   public SavedDataFeaturePoolElementFix(Schema var0) {
      super(var0, false);
   }

   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead(
         "SavedDataFeaturePoolElementFix",
         this.getInputSchema().getType(DataConverterTypes.v),
         this.getOutputSchema().getType(DataConverterTypes.v),
         SavedDataFeaturePoolElementFix::b
      );
   }

   private static <T> Dynamic<T> b(Dynamic<T> var0) {
      return var0.update("Children", SavedDataFeaturePoolElementFix::c);
   }

   private static <T> Dynamic<T> c(Dynamic<T> var0) {
      return (Dynamic<T>)var0.asStreamOpt().map(SavedDataFeaturePoolElementFix::a).map(var0::createList).result().orElse((T)var0);
   }

   private static Stream<? extends Dynamic<?>> a(Stream<? extends Dynamic<?>> var0) {
      return var0.map(
         var0x -> {
            String var1 = var0x.get("id").asString("");
            if (!b.contains(var1)) {
               return var0x;
            } else {
               OptionalDynamic<?> var2 = var0x.get("pool_element");
               return !var2.get("element_type").asString("").equals("minecraft:feature_pool_element")
                  ? var0x
                  : var0x.update("pool_element", var0xx -> var0xx.update("feature", SavedDataFeaturePoolElementFix::a));
            }
         }
      );
   }

   private static <T> OptionalDynamic<T> a(Dynamic<T> var0, String... var1) {
      if (var1.length == 0) {
         throw new IllegalArgumentException("Missing path");
      } else {
         OptionalDynamic<T> var2 = var0.get(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            String var4 = var1[var3];
            Matcher var5 = a.matcher(var4);
            if (var5.matches()) {
               int var6 = Integer.parseInt(var5.group(1));
               List<? extends Dynamic<T>> var7 = var2.asList(Function.identity());
               if (var6 >= 0 && var6 < var7.size()) {
                  var2 = new OptionalDynamic(var0.getOps(), DataResult.success((Dynamic)var7.get(var6)));
               } else {
                  var2 = new OptionalDynamic(var0.getOps(), DataResult.error(() -> "Missing id:" + var6));
               }
            } else {
               var2 = var2.get(var4);
            }
         }

         return var2;
      }
   }

   @VisibleForTesting
   protected static Dynamic<?> a(Dynamic<?> var0) {
      Optional<String> var1 = a(
         a(var0, "type").asString(""),
         a(var0, "name").asString(""),
         a(var0, "config", "state_provider", "type").asString(""),
         a(var0, "config", "state_provider", "state", "Name").asString(""),
         a(var0, "config", "state_provider", "entries", "[0]", "data", "Name").asString(""),
         a(var0, "config", "foliage_placer", "type").asString(""),
         a(var0, "config", "leaves_provider", "state", "Name").asString("")
      );
      return var1.isPresent() ? var0.createString(var1.get()) : var0;
   }

   private static Optional<String> a(String var0, String var1, String var2, String var3, String var4, String var5, String var6) {
      String var7;
      if (!var0.isEmpty()) {
         var7 = var0;
      } else {
         if (var1.isEmpty()) {
            return Optional.empty();
         }

         if ("minecraft:normal_tree".equals(var1)) {
            var7 = "minecraft:tree";
         } else {
            var7 = var1;
         }
      }

      if (c.contains(var7)) {
         if ("minecraft:random_patch".equals(var7)) {
            if ("minecraft:simple_state_provider".equals(var2)) {
               if ("minecraft:sweet_berry_bush".equals(var3)) {
                  return Optional.of("minecraft:patch_berry_bush");
               }

               if ("minecraft:cactus".equals(var3)) {
                  return Optional.of("minecraft:patch_cactus");
               }
            } else if ("minecraft:weighted_state_provider".equals(var2) && ("minecraft:grass".equals(var4) || "minecraft:fern".equals(var4))) {
               return Optional.of("minecraft:patch_taiga_grass");
            }
         } else if ("minecraft:block_pile".equals(var7)) {
            if (!"minecraft:simple_state_provider".equals(var2) && !"minecraft:rotated_block_provider".equals(var2)) {
               if ("minecraft:weighted_state_provider".equals(var2)) {
                  if ("minecraft:packed_ice".equals(var4) || "minecraft:blue_ice".equals(var4)) {
                     return Optional.of("minecraft:pile_ice");
                  }

                  if ("minecraft:jack_o_lantern".equals(var4) || "minecraft:pumpkin".equals(var4)) {
                     return Optional.of("minecraft:pile_pumpkin");
                  }
               }
            } else {
               if ("minecraft:hay_block".equals(var3)) {
                  return Optional.of("minecraft:pile_hay");
               }

               if ("minecraft:melon".equals(var3)) {
                  return Optional.of("minecraft:pile_melon");
               }

               if ("minecraft:snow".equals(var3)) {
                  return Optional.of("minecraft:pile_snow");
               }
            }
         } else {
            if ("minecraft:flower".equals(var7)) {
               return Optional.of("minecraft:flower_plain");
            }

            if ("minecraft:tree".equals(var7)) {
               if ("minecraft:acacia_foliage_placer".equals(var5)) {
                  return Optional.of("minecraft:acacia");
               }

               if ("minecraft:blob_foliage_placer".equals(var5) && "minecraft:oak_leaves".equals(var6)) {
                  return Optional.of("minecraft:oak");
               }

               if ("minecraft:pine_foliage_placer".equals(var5)) {
                  return Optional.of("minecraft:pine");
               }

               if ("minecraft:spruce_foliage_placer".equals(var5)) {
                  return Optional.of("minecraft:spruce");
               }
            }
         }
      }

      return Optional.empty();
   }
}
