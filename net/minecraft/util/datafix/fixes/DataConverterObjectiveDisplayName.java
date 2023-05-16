package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.network.chat.IChatBaseComponent;

public class DataConverterObjectiveDisplayName extends DataFix {
   public DataConverterObjectiveDisplayName(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.w);
      return this.fixTypeEverywhereTyped(
         "ObjectiveDisplayNameFix",
         var0,
         var0x -> var0x.update(
               DSL.remainderFinder(),
               var0xx -> var0xx.update(
                     "DisplayName",
                     var1x -> (Dynamic)DataFixUtils.orElse(
                           var1x.asString()
                              .map(var0xxxx -> IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.b(var0xxxx)))
                              .map(var0xx::createString)
                              .result(),
                           var1x
                        )
                  )
            )
      );
   }
}
