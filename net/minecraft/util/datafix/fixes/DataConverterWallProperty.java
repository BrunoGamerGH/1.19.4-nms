package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;

public class DataConverterWallProperty extends DataFix {
   private static final Set<String> a = ImmutableSet.of(
      "minecraft:andesite_wall",
      "minecraft:brick_wall",
      "minecraft:cobblestone_wall",
      "minecraft:diorite_wall",
      "minecraft:end_stone_brick_wall",
      "minecraft:granite_wall",
      new String[]{
         "minecraft:mossy_cobblestone_wall",
         "minecraft:mossy_stone_brick_wall",
         "minecraft:nether_brick_wall",
         "minecraft:prismarine_wall",
         "minecraft:red_nether_brick_wall",
         "minecraft:red_sandstone_wall",
         "minecraft:sandstone_wall",
         "minecraft:stone_brick_wall"
      }
   );

   public DataConverterWallProperty(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "WallPropertyFix", this.getInputSchema().getType(DataConverterTypes.n), var0 -> var0.update(DSL.remainderFinder(), DataConverterWallProperty::a)
      );
   }

   private static String a(String var0) {
      return "true".equals(var0) ? "low" : "none";
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0, String var1) {
      return var0.update(
         var1, var0x -> (Dynamic)DataFixUtils.orElse(var0x.asString().result().map(DataConverterWallProperty::a).map(var0x::createString), var0x)
      );
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      boolean var1 = var0.get("Name").asString().result().filter(a::contains).isPresent();
      return !var1 ? var0 : var0.update("Properties", var0x -> {
         Dynamic<?> var1x = a(var0x, "east");
         var1x = a(var1x, "west");
         var1x = a(var1x, "north");
         return a(var1x, "south");
      });
   }
}
