package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.network.chat.IChatBaseComponent;

public class DataConverterTeamDisplayName extends DataFix {
   public DataConverterTeamDisplayName(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> var0 = DSL.named(DataConverterTypes.x.typeName(), DSL.remainderType());
      if (!Objects.equals(var0, this.getInputSchema().getType(DataConverterTypes.x))) {
         throw new IllegalStateException("Team type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(
            "TeamDisplayNameFix",
            var0,
            var0x -> var0xx -> var0xx.mapSecond(
                     var0xxx -> var0xxx.update(
                           "DisplayName",
                           var1x -> (Dynamic)DataFixUtils.orElse(
                                 var1x.asString()
                                    .map(var0xxxxx -> IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.b(var0xxxxx)))
                                    .map(var0xxx::createString)
                                    .result(),
                                 var1x
                              )
                        )
                  )
         );
      }
   }
}
