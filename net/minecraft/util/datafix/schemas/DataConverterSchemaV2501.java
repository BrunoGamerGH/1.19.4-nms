package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV2501 extends DataConverterSchemaNamed {
   public DataConverterSchemaV2501(int var0, Schema var1) {
      super(var0, var1);
   }

   private static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      var0.register(
         var1,
         var2,
         () -> DSL.optionalFields(
               "Items", DSL.list(DataConverterTypes.m.in(var0)), "RecipesUsed", DSL.compoundList(DataConverterTypes.y.in(var0), DSL.constType(DSL.intType()))
            )
      );
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerBlockEntities(var0);
      a(var0, var1, "minecraft:furnace");
      a(var0, var1, "minecraft:smoker");
      a(var0, var1, "minecraft:blast_furnace");
      return var1;
   }
}
