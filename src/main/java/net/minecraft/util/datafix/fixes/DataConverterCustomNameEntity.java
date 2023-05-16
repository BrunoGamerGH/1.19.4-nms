package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterCustomNameEntity extends DataFix {
   public DataConverterCustomNameEntity(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> var0 = DSL.fieldFinder("id", DataConverterSchemaNamed.a());
      return this.fixTypeEverywhereTyped(
         "EntityCustomNameToComponentFix", this.getInputSchema().getType(DataConverterTypes.q), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> var3 = var1x.getOptional(var0);
               return var3.isPresent() && Objects.equals(var3.get(), "minecraft:commandblock_minecart") ? var2 : a(var2);
            })
      );
   }

   public static Dynamic<?> a(Dynamic<?> var0) {
      String var1 = var0.get("CustomName").asString("");
      return var1.isEmpty()
         ? var0.remove("CustomName")
         : var0.set("CustomName", var0.createString(IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.b(var1))));
   }
}
