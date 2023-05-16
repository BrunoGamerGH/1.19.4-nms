package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.network.chat.IChatBaseComponent;

public class DataConverterCustomNameItem extends DataFix {
   public DataConverterCustomNameItem(Schema var0, boolean var1) {
      super(var0, var1);
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      Optional<? extends Dynamic<?>> var1 = var0.get("display").result();
      if (var1.isPresent()) {
         Dynamic<?> var2 = (Dynamic)var1.get();
         Optional<String> var3 = var2.get("Name").asString().result();
         if (var3.isPresent()) {
            var2 = var2.set("Name", var2.createString(IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.b(var3.get()))));
         } else {
            Optional<String> var4 = var2.get("LocName").asString().result();
            if (var4.isPresent()) {
               var2 = var2.set("Name", var2.createString(IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.c(var4.get()))));
               var2 = var2.remove("LocName");
            }
         }

         return var0.set("display", var2);
      } else {
         return var0;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<?> var1 = var0.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemCustomNameToComponentFix", var0, var1x -> var1x.updateTyped(var1, var0xx -> var0xx.update(DSL.remainderFinder(), this::a))
      );
   }
}
