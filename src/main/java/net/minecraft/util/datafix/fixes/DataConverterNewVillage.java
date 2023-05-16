package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterNewVillage extends DataFix {
   public DataConverterNewVillage(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      CompoundListType<String, ?> var0 = DSL.compoundList(DSL.string(), this.getInputSchema().getType(DataConverterTypes.v));
      OpticFinder<? extends List<? extends Pair<String, ?>>> var1 = var0.finder();
      return this.a(var0);
   }

   private <SF> TypeRewriteRule a(CompoundListType<String, SF> var0) {
      Type<?> var1 = this.getInputSchema().getType(DataConverterTypes.c);
      Type<?> var2 = this.getInputSchema().getType(DataConverterTypes.v);
      OpticFinder<?> var3 = var1.findField("Level");
      OpticFinder<?> var4 = var3.type().findField("Structures");
      OpticFinder<?> var5 = var4.type().findField("Starts");
      OpticFinder<List<Pair<String, SF>>> var6 = var0.finder();
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "NewVillageFix",
            var1,
            var4x -> var4x.updateTyped(
                  var3,
                  var3xx -> var3xx.updateTyped(
                        var4,
                        var2xxx -> var2xxx.updateTyped(
                                 var5,
                                 var1xxxx -> var1xxxx.update(
                                       var6,
                                       var0xxxxx -> var0xxxxx.stream()
                                             .filter(var0xxxxxx -> !Objects.equals(var0xxxxxx.getFirst(), "Village"))
                                             .map(
                                                var0xxxxxx -> var0xxxxxx.mapFirst(var0xxxxxxx -> var0xxxxxxx.equals("New_Village") ? "Village" : var0xxxxxxx)
                                             )
                                             .collect(Collectors.toList())
                                    )
                              )
                              .update(
                                 DSL.remainderFinder(),
                                 var0xxxx -> var0xxxx.update(
                                       "References",
                                       var0xxxxx -> {
                                          Optional<? extends Dynamic<?>> var1xxxx = var0xxxxx.get("New_Village").result();
                                          return ((Dynamic)DataFixUtils.orElse(
                                                var1xxxx.map(var1xxxxx -> var0xxxxx.remove("New_Village").set("Village", var1xxxxx)), var0xxxxx
                                             ))
                                             .remove("Village");
                                       }
                                    )
                              )
                     )
               )
         ),
         this.fixTypeEverywhereTyped(
            "NewVillageStartFix",
            var2,
            var0x -> var0x.update(
                  DSL.remainderFinder(),
                  var0xx -> var0xx.update(
                        "id",
                        var0xxx -> Objects.equals(DataConverterSchemaNamed.a(var0xxx.asString("")), "minecraft:new_village")
                              ? var0xxx.createString("minecraft:village")
                              : var0xxx
                     )
               )
         )
      );
   }
}
