package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1451_3 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1451_3(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var0.registerSimple(var1, "minecraft:egg");
      var0.registerSimple(var1, "minecraft:ender_pearl");
      var0.registerSimple(var1, "minecraft:fireball");
      var0.register(var1, "minecraft:potion", var1x -> DSL.optionalFields("Potion", DataConverterTypes.m.in(var0)));
      var0.registerSimple(var1, "minecraft:small_fireball");
      var0.registerSimple(var1, "minecraft:snowball");
      var0.registerSimple(var1, "minecraft:wither_skull");
      var0.registerSimple(var1, "minecraft:xp_bottle");
      var0.register(var1, "minecraft:arrow", () -> DSL.optionalFields("inBlockState", DataConverterTypes.n.in(var0)));
      var0.register(var1, "minecraft:enderman", () -> DSL.optionalFields("carriedBlockState", DataConverterTypes.n.in(var0), DataConverterSchemaV100.a(var0)));
      var0.register(
         var1,
         "minecraft:falling_block",
         () -> DSL.optionalFields("BlockState", DataConverterTypes.n.in(var0), "TileEntityData", DataConverterTypes.l.in(var0))
      );
      var0.register(var1, "minecraft:spectral_arrow", () -> DSL.optionalFields("inBlockState", DataConverterTypes.n.in(var0)));
      var0.register(
         var1,
         "minecraft:chest_minecart",
         () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      var0.register(var1, "minecraft:commandblock_minecart", () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
      var0.register(var1, "minecraft:furnace_minecart", () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
      var0.register(
         var1,
         "minecraft:hopper_minecart",
         () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0), "Items", DSL.list(DataConverterTypes.m.in(var0)))
      );
      var0.register(var1, "minecraft:minecart", () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
      var0.register(var1, "minecraft:spawner_minecart", () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0), DataConverterTypes.u.in(var0)));
      var0.register(var1, "minecraft:tnt_minecart", () -> DSL.optionalFields("DisplayState", DataConverterTypes.n.in(var0)));
      return var1;
   }
}
