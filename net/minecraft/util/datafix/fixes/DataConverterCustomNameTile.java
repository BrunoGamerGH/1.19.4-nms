package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterCustomNameTile extends DataFix {
   public DataConverterCustomNameTile(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> var0 = DSL.fieldFinder("id", DataConverterSchemaNamed.a());
      return this.fixTypeEverywhereTyped(
         "BlockEntityCustomNameToComponentFix", this.getInputSchema().getType(DataConverterTypes.l), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> var3 = var1x.getOptional(var0);
               return var3.isPresent() && Objects.equals(var3.get(), "minecraft:command_block") ? var2 : DataConverterCustomNameEntity.a(var2);
            })
      );
   }
}
