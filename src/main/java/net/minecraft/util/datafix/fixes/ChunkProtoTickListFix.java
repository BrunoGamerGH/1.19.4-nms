package net.minecraft.util.datafix.fixes;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableInt;

public class ChunkProtoTickListFix extends DataFix {
   private static final int a = 16;
   private static final ImmutableSet<String> b = ImmutableSet.of(
      "minecraft:bubble_column", "minecraft:kelp", "minecraft:kelp_plant", "minecraft:seagrass", "minecraft:tall_seagrass"
   );

   public ChunkProtoTickListFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      OpticFinder<?> var1 = var0.findField("Level");
      OpticFinder<?> var2 = var1.type().findField("Sections");
      OpticFinder<?> var3 = ((ListType)var2.type()).getElement().finder();
      OpticFinder<?> var4 = var3.type().findField("block_states");
      OpticFinder<?> var5 = var3.type().findField("biomes");
      OpticFinder<?> var6 = var4.type().findField("palette");
      OpticFinder<?> var7 = var1.type().findField("TileTicks");
      return this.fixTypeEverywhereTyped(
         "ChunkProtoTickListFix",
         var0,
         var7x -> var7x.updateTyped(
               var1,
               var6xx -> {
                  var6xx = var6xx.update(
                     DSL.remainderFinder(),
                     var0xxx -> (Dynamic)DataFixUtils.orElse(
                           var0xxx.get("LiquidTicks").result().map(var1xxx -> var0xxx.set("fluid_ticks", var1xxx).remove("LiquidTicks")), var0xxx
                        )
                  );
                  Dynamic<?> var7xx = (Dynamic)var6xx.get(DSL.remainderFinder());
                  MutableInt var8x = new MutableInt();
                  Int2ObjectMap<Supplier<ChunkProtoTickListFix.a>> var9 = new Int2ObjectArrayMap();
                  var6xx.getOptionalTyped(var2)
                     .ifPresent(
                        var6xxx -> var6xxx.getAllTyped(var3)
                              .forEach(
                                 var5xxxx -> {
                                    Dynamic<?> var6xxxx = (Dynamic)var5xxxx.get(DSL.remainderFinder());
                                    int var7xxx = var6xxxx.get("Y").asInt(Integer.MAX_VALUE);
                                    if (var7xxx != Integer.MAX_VALUE) {
                                       if (var5xxxx.getOptionalTyped(var5).isPresent()) {
                                          var8x.setValue(Math.min(var7xxx, var8x.getValue()));
                                       }
                  
                                       var5xxxx.getOptionalTyped(var4)
                                          .ifPresent(
                                             var3xxxxx -> var9.put(
                                                   var7xxx,
                                                   Suppliers.memoize(
                                                      () -> {
                                                         List<? extends Dynamic<?>> var2xxxxxx = var3xxxxx.getOptionalTyped(var6)
                                                            .map(
                                                               var0xxxxxxx -> var0xxxxxxx.write()
                                                                     .result()
                                                                     .map(var0xxxxxxxx -> var0xxxxxxxx.asList(Function.identity()))
                                                                     .orElse(Collections.emptyList())
                                                            )
                                                            .orElse(Collections.emptyList());
                                                         long[] var3xxxxxx = ((Dynamic)var3xxxxx.get(DSL.remainderFinder()))
                                                            .get("data")
                                                            .asLongStream()
                                                            .toArray();
                                                         return new ChunkProtoTickListFix.a(var2xxxxxx, var3xxxxxx);
                                                      }
                                                   )
                                                )
                                          );
                                    }
                                 }
                              )
                     );
                  byte var10 = var8x.getValue().byteValue();
                  var6xx = var6xx.update(DSL.remainderFinder(), var1xxx -> var1xxx.update("yPos", var1xxxx -> var1xxxx.createByte(var10)));
                  if (!var6xx.getOptionalTyped(var7).isPresent() && !var7xx.get("fluid_ticks").result().isPresent()) {
                     int var11 = var7xx.get("xPos").asInt(0);
                     int var12 = var7xx.get("zPos").asInt(0);
                     Dynamic<?> var13 = this.a(var7xx, var9, var10, var11, var12, "LiquidsToBeTicked", ChunkProtoTickListFix::b);
                     Dynamic<?> var14 = this.a(var7xx, var9, var10, var11, var12, "ToBeTicked", ChunkProtoTickListFix::a);
                     Optional<? extends Pair<? extends Typed<?>, ?>> var15 = var7.type().readTyped(var14).result();
                     if (var15.isPresent()) {
                        var6xx = var6xx.set(var7, (Typed)((Pair)var15.get()).getFirst());
                     }
      
                     return var6xx.update(DSL.remainderFinder(), var1xxx -> var1xxx.remove("ToBeTicked").remove("LiquidsToBeTicked").set("fluid_ticks", var13));
                  } else {
                     return var6xx;
                  }
               }
            )
      );
   }

   private Dynamic<?> a(
      Dynamic<?> var0, Int2ObjectMap<Supplier<ChunkProtoTickListFix.a>> var1, byte var2, int var3, int var4, String var5, Function<Dynamic<?>, String> var6
   ) {
      Stream<Dynamic<?>> var7 = Stream.empty();
      List<? extends Dynamic<?>> var8 = var0.get(var5).asList(Function.identity());

      for(int var9 = 0; var9 < var8.size(); ++var9) {
         int var10 = var9 + var2;
         Supplier<ChunkProtoTickListFix.a> var11 = (Supplier)var1.get(var10);
         Stream<? extends Dynamic<?>> var12 = ((Dynamic)var8.get(var9))
            .asStream()
            .mapToInt(var0x -> var0x.asShort((short)-1))
            .filter(var0x -> var0x > 0)
            .mapToObj(var6x -> this.a(var0, var11, var3, var10, var4, var6x, var6));
         var7 = Stream.concat(var7, var12);
      }

      return var0.createList(var7);
   }

   private static String a(@Nullable Dynamic<?> var0) {
      return var0 != null ? var0.get("Name").asString("minecraft:air") : "minecraft:air";
   }

   private static String b(@Nullable Dynamic<?> var0) {
      if (var0 == null) {
         return "minecraft:empty";
      } else {
         String var1 = var0.get("Name").asString("");
         if ("minecraft:water".equals(var1)) {
            return var0.get("Properties").get("level").asInt(0) == 0 ? "minecraft:water" : "minecraft:flowing_water";
         } else if ("minecraft:lava".equals(var1)) {
            return var0.get("Properties").get("level").asInt(0) == 0 ? "minecraft:lava" : "minecraft:flowing_lava";
         } else {
            return !b.contains(var1) && !var0.get("Properties").get("waterlogged").asBoolean(false) ? "minecraft:empty" : "minecraft:water";
         }
      }
   }

   private Dynamic<?> a(
      Dynamic<?> var0, @Nullable Supplier<ChunkProtoTickListFix.a> var1, int var2, int var3, int var4, int var5, Function<Dynamic<?>, String> var6
   ) {
      int var7 = var5 & 15;
      int var8 = var5 >>> 4 & 15;
      int var9 = var5 >>> 8 & 15;
      String var10 = var6.apply(var1 != null ? var1.get().a(var7, var8, var9) : null);
      return var0.createMap(
         ImmutableMap.builder()
            .put(var0.createString("i"), var0.createString(var10))
            .put(var0.createString("x"), var0.createInt(var2 * 16 + var7))
            .put(var0.createString("y"), var0.createInt(var3 * 16 + var8))
            .put(var0.createString("z"), var0.createInt(var4 * 16 + var9))
            .put(var0.createString("t"), var0.createInt(0))
            .put(var0.createString("p"), var0.createInt(0))
            .build()
      );
   }

   public static final class a {
      private static final long a = 4L;
      private final List<? extends Dynamic<?>> b;
      private final long[] c;
      private final int d;
      private final long e;
      private final int f;

      public a(List<? extends Dynamic<?>> var0, long[] var1) {
         this.b = var0;
         this.c = var1;
         this.d = Math.max(4, ChunkHeightAndBiomeFix.a(var0.size()));
         this.e = (1L << this.d) - 1L;
         this.f = (char)(64 / this.d);
      }

      @Nullable
      public Dynamic<?> a(int var0, int var1, int var2) {
         int var3 = this.b.size();
         if (var3 < 1) {
            return null;
         } else if (var3 == 1) {
            return (Dynamic<?>)this.b.get(0);
         } else {
            int var4 = this.b(var0, var1, var2);
            int var5 = var4 / this.f;
            if (var5 >= 0 && var5 < this.c.length) {
               long var6 = this.c[var5];
               int var8 = (var4 - var5 * this.f) * this.d;
               int var9 = (int)(var6 >> var8 & this.e);
               return var9 >= 0 && var9 < var3 ? (Dynamic)this.b.get(var9) : null;
            } else {
               return null;
            }
         }
      }

      private int b(int var0, int var1, int var2) {
         return (var1 << 4 | var2) << 4 | var0;
      }

      public List<? extends Dynamic<?>> a() {
         return this.b;
      }

      public long[] b() {
         return this.c;
      }
   }
}
