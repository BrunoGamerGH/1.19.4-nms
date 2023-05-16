package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.network.chat.IChatBaseComponent;

public class DataConverterItemLoreComponentize extends DataFix {
   public DataConverterItemLoreComponentize(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<?> var1 = var0.findField("tag");
      return this.fixTypeEverywhereTyped(
         "Item Lore componentize",
         var0,
         var1x -> var1x.updateTyped(
               var1,
               var0xx -> var0xx.update(
                     DSL.remainderFinder(),
                     var0xxx -> var0xxx.update(
                           "display",
                           var0xxxx -> var0xxxx.update(
                                 "Lore",
                                 var0xxxxx -> (Dynamic)DataFixUtils.orElse(
                                       var0xxxxx.asStreamOpt().map(DataConverterItemLoreComponentize::a).map(var0xxxxx::createList).result(), var0xxxxx
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static <T> Stream<Dynamic<T>> a(Stream<Dynamic<T>> var0) {
      return var0.map(
         var0x -> (Dynamic)DataFixUtils.orElse(var0x.asString().map(DataConverterItemLoreComponentize::a).map(var0x::createString).result(), var0x)
      );
   }

   private static String a(String var0) {
      return IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.b(var0));
   }
}
