package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterBanner extends DataFix {
   public DataConverterBanner(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<Pair<String, String>> var1 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      OpticFinder<?> var2 = var0.findField("tag");
      OpticFinder<?> var3 = var2.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemBannerColorFix",
         var0,
         var3x -> {
            Optional<Pair<String, String>> var4x = var3x.getOptional(var1);
            if (var4x.isPresent() && Objects.equals(((Pair)var4x.get()).getSecond(), "minecraft:banner")) {
               Dynamic<?> var5 = (Dynamic)var3x.get(DSL.remainderFinder());
               Optional<? extends Typed<?>> var6 = var3x.getOptionalTyped(var2);
               if (var6.isPresent()) {
                  Typed<?> var7 = (Typed)var6.get();
                  Optional<? extends Typed<?>> var8 = var7.getOptionalTyped(var3);
                  if (var8.isPresent()) {
                     Typed<?> var9 = (Typed)var8.get();
                     Dynamic<?> var10 = (Dynamic)var7.get(DSL.remainderFinder());
                     Dynamic<?> var11 = (Dynamic)var9.getOrCreate(DSL.remainderFinder());
                     if (var11.get("Base").asNumber().result().isPresent()) {
                        var5 = var5.set("Damage", var5.createShort((short)(var11.get("Base").asInt(0) & 15)));
                        Optional<? extends Dynamic<?>> var12 = var10.get("display").result();
                        if (var12.isPresent()) {
                           Dynamic<?> var13 = (Dynamic)var12.get();
                           Dynamic<?> var14 = var13.createMap(
                              ImmutableMap.of(var13.createString("Lore"), var13.createList(Stream.of(var13.createString("(+NBT"))))
                           );
                           if (Objects.equals(var13, var14)) {
                              return var3x.set(DSL.remainderFinder(), var5);
                           }
                        }
   
                        var11.remove("Base");
                        return var3x.set(DSL.remainderFinder(), var5).set(var2, var7.set(var3, var9.set(DSL.remainderFinder(), var11)));
                     }
                  }
               }
   
               return var3x.set(DSL.remainderFinder(), var5);
            } else {
               return var3x;
            }
         }
      );
   }
}
