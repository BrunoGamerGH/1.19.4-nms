package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterRedstoneConnections extends DataFix {
   public DataConverterRedstoneConnections(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      return this.fixTypeEverywhereTyped("RedstoneConnectionsFix", var0.getType(DataConverterTypes.n), var0x -> var0x.update(DSL.remainderFinder(), this::a));
   }

   private <T> Dynamic<T> a(Dynamic<T> var0) {
      boolean var1 = var0.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
      return !var1
         ? var0
         : var0.update(
            "Properties",
            var0x -> {
               String var1x = var0x.get("east").asString("none");
               String var2x = var0x.get("west").asString("none");
               String var3 = var0x.get("north").asString("none");
               String var4 = var0x.get("south").asString("none");
               boolean var5 = a(var1x) || a(var2x);
               boolean var6 = a(var3) || a(var4);
               String var7 = !a(var1x) && !var6 ? "side" : var1x;
               String var8 = !a(var2x) && !var6 ? "side" : var2x;
               String var9 = !a(var3) && !var5 ? "side" : var3;
               String var10 = !a(var4) && !var5 ? "side" : var4;
               return var0x.update("east", var1xx -> var1xx.createString(var7))
                  .update("west", var1xx -> var1xx.createString(var8))
                  .update("north", var1xx -> var1xx.createString(var9))
                  .update("south", var1xx -> var1xx.createString(var10));
            }
         );
   }

   private static boolean a(String var0) {
      return !"none".equals(var0);
   }
}
