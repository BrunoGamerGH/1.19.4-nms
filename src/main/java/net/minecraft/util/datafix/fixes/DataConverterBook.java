package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ChatDeserializer;
import org.apache.commons.lang3.StringUtils;

public class DataConverterBook extends DataFix {
   public DataConverterBook(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return var0.update(
         "pages",
         var1x -> (Dynamic)DataFixUtils.orElse(
               var1x.asStreamOpt()
                  .map(
                     var0xx -> var0xx.map(
                           var0xxx -> {
                              if (!var0xxx.asString().result().isPresent()) {
                                 return var0xxx;
                              } else {
                                 String var1xx = var0xxx.asString("");
                                 IChatBaseComponent var2 = null;
                                 if (!"null".equals(var1xx) && !StringUtils.isEmpty(var1xx)) {
                                    if (var1xx.charAt(0) == '"' && var1xx.charAt(var1xx.length() - 1) == '"'
                                       || var1xx.charAt(0) == '{' && var1xx.charAt(var1xx.length() - 1) == '}') {
                                       try {
                                          var2 = ChatDeserializer.b(DataConverterSignText.a, var1xx, IChatBaseComponent.class, true);
                                          if (var2 == null) {
                                             var2 = CommonComponents.a;
                                          }
                                       } catch (Exception var6) {
                                       }
               
                                       if (var2 == null) {
                                          try {
                                             var2 = IChatBaseComponent.ChatSerializer.a(var1xx);
                                          } catch (Exception var5) {
                                          }
                                       }
               
                                       if (var2 == null) {
                                          try {
                                             var2 = IChatBaseComponent.ChatSerializer.b(var1xx);
                                          } catch (Exception var4) {
                                          }
                                       }
               
                                       if (var2 == null) {
                                          var2 = IChatBaseComponent.b(var1xx);
                                       }
                                    } else {
                                       var2 = IChatBaseComponent.b(var1xx);
                                    }
                                 } else {
                                    var2 = CommonComponents.a;
                                 }
               
                                 return var0xxx.createString(IChatBaseComponent.ChatSerializer.a(var2));
                              }
                           }
                        )
                  )
                  .map(var0::createList)
                  .result(),
               var0.emptyList()
            )
      );
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<?> var1 = var0.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWrittenBookPagesStrictJsonFix", var0, var1x -> var1x.updateTyped(var1, var0xx -> var0xx.update(DSL.remainderFinder(), this::a))
      );
   }
}
