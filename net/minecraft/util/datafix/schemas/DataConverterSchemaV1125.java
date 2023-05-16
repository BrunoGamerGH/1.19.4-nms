package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1125 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1125(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerBlockEntities(var0);
      var0.registerSimple(var1, "minecraft:bed");
      return var1;
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(
         false,
         DataConverterTypes.i,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.z.in(var0), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.string())))
            )
      );
      var0.registerType(false, DataConverterTypes.z, () -> DSL.constType(a()));
      var0.registerType(false, DataConverterTypes.o, () -> DSL.constType(a()));
   }
}
