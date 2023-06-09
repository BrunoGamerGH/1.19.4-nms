package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;

public class DataConverterJigsawRotation extends DataFix {
   private static final Map<String, String> a = ImmutableMap.builder()
      .put("down", "down_south")
      .put("up", "up_north")
      .put("north", "north_up")
      .put("south", "south_up")
      .put("west", "west_up")
      .put("east", "east_up")
      .build();

   public DataConverterJigsawRotation(Schema var0, boolean var1) {
      super(var0, var1);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      Optional<String> var1 = var0.get("Name").asString().result();
      return var1.equals(Optional.of("minecraft:jigsaw")) ? var0.update("Properties", var0x -> {
         String var1x = var0x.get("facing").asString("north");
         return var0x.remove("facing").set("orientation", var0x.createString(a.getOrDefault(var1x, var1x)));
      }) : var0;
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "jigsaw_rotation_fix",
         this.getInputSchema().getType(DataConverterTypes.n),
         var0 -> var0.update(DSL.remainderFinder(), DataConverterJigsawRotation::a)
      );
   }
}
