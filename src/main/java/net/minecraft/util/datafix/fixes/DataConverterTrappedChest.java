package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class DataConverterTrappedChest extends DataFix {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 4096;
   private static final short c = 12;

   public DataConverterTrappedChest(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.c);
      Type<?> var1 = var0.findFieldType("Level");
      Type<?> var2 = var1.findFieldType("TileEntities");
      if (!(var2 instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> var3 = (ListType)var2;
         OpticFinder<? extends List<?>> var4 = DSL.fieldFinder("TileEntities", var3);
         Type<?> var5 = this.getInputSchema().getType(DataConverterTypes.c);
         OpticFinder<?> var6 = var5.findField("Level");
         OpticFinder<?> var7 = var6.type().findField("Sections");
         Type<?> var8 = var7.type();
         if (!(var8 instanceof ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
         } else {
            Type<?> var9 = ((ListType)var8).getElement();
            OpticFinder<?> var10 = DSL.typeFinder(var9);
            return TypeRewriteRule.seq(
               new DataConverterAddChoices(this.getOutputSchema(), "AddTrappedChestFix", DataConverterTypes.l).makeRule(),
               this.fixTypeEverywhereTyped(
                  "Trapped Chest fix",
                  var5,
                  var4x -> var4x.updateTyped(
                        var6,
                        var3xx -> {
                           Optional<? extends Typed<?>> var4xx = var3xx.getOptionalTyped(var7);
                           if (!var4xx.isPresent()) {
                              return var3xx;
                           } else {
                              List<? extends Typed<?>> var5x = ((Typed)var4xx.get()).getAllTyped(var10);
                              IntSet var6x = new IntOpenHashSet();
         
                              for(Typed<?> var8x : var5x) {
                                 DataConverterTrappedChest.a var9x = new DataConverterTrappedChest.a(var8x, this.getInputSchema());
                                 if (!var9x.b()) {
                                    for(int var10x = 0; var10x < 4096; ++var10x) {
                                       int var11x = var9x.c(var10x);
                                       if (var9x.a(var11x)) {
                                          var6x.add(var9x.c() << 12 | var10x);
                                       }
                                    }
                                 }
                              }
         
                              Dynamic<?> var7 = (Dynamic)var3xx.get(DSL.remainderFinder());
                              int var8 = var7.get("xPos").asInt(0);
                              int var9 = var7.get("zPos").asInt(0);
                              TaggedChoiceType<String> var10 = this.getInputSchema().findChoiceType(DataConverterTypes.l);
                              return var3xx.updateTyped(
                                 var4,
                                 var4xxx -> var4xxx.updateTyped(
                                       var10.finder(),
                                       var4xxxx -> {
                                          Dynamic<?> var5xx = (Dynamic)var4xxxx.getOrCreate(DSL.remainderFinder());
                                          int var6xx = var5xx.get("x").asInt(0) - (var8 << 4);
                                          int var7x = var5xx.get("y").asInt(0);
                                          int var8xx = var5xx.get("z").asInt(0) - (var9 << 4);
                                          return var6x.contains(DataConverterLeaves.a(var6xx, var7x, var8xx))
                                             ? var4xxxx.update(var10.finder(), var0xxxxx -> var0xxxxx.mapFirst(var0xxxxxx -> {
                                                   if (!Objects.equals(var0xxxxxx, "minecraft:chest")) {
                                                      a.warn("Block Entity was expected to be a chest");
                                                   }
                  
                                                   return "minecraft:trapped_chest";
                                                }))
                                             : var4xxxx;
                                       }
                                    )
                              );
                           }
                        }
                     )
               )
            );
         }
      }
   }

   public static final class a extends DataConverterLeaves.b {
      @Nullable
      private IntSet h;

      public a(Typed<?> var0, Schema var1) {
         super(var0, var1);
      }

      @Override
      protected boolean a() {
         this.h = new IntOpenHashSet();

         for(int var0 = 0; var0 < this.e.size(); ++var0) {
            Dynamic<?> var1 = (Dynamic)this.e.get(var0);
            String var2 = var1.get("Name").asString("");
            if (Objects.equals(var2, "minecraft:trapped_chest")) {
               this.h.add(var0);
            }
         }

         return this.h.isEmpty();
      }

      public boolean a(int var0) {
         return this.h.contains(var0);
      }
   }
}
