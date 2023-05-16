package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class DataConverterHanging extends DataFix {
   private static final int[][] a = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

   public DataConverterHanging(Schema var0, boolean var1) {
      super(var0, var1);
   }

   private Dynamic<?> a(Dynamic<?> var0, boolean var1, boolean var2) {
      if ((var1 || var2) && !var0.get("Facing").asNumber().result().isPresent()) {
         int var3;
         if (var0.get("Direction").asNumber().result().isPresent()) {
            var3 = var0.get("Direction").asByte((byte)0) % a.length;
            int[] var4 = a[var3];
            var0 = var0.set("TileX", var0.createInt(var0.get("TileX").asInt(0) + var4[0]));
            var0 = var0.set("TileY", var0.createInt(var0.get("TileY").asInt(0) + var4[1]));
            var0 = var0.set("TileZ", var0.createInt(var0.get("TileZ").asInt(0) + var4[2]));
            var0 = var0.remove("Direction");
            if (var2 && var0.get("ItemRotation").asNumber().result().isPresent()) {
               var0 = var0.set("ItemRotation", var0.createByte((byte)(var0.get("ItemRotation").asByte((byte)0) * 2)));
            }
         } else {
            var3 = var0.get("Dir").asByte((byte)0) % a.length;
            var0 = var0.remove("Dir");
         }

         var0 = var0.set("Facing", var0.createByte((byte)var3));
      }

      return var0;
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getChoiceType(DataConverterTypes.q, "Painting");
      OpticFinder<?> var1 = DSL.namedChoice("Painting", var0);
      Type<?> var2 = this.getInputSchema().getChoiceType(DataConverterTypes.q, "ItemFrame");
      OpticFinder<?> var3 = DSL.namedChoice("ItemFrame", var2);
      Type<?> var4 = this.getInputSchema().getType(DataConverterTypes.q);
      TypeRewriteRule var5 = this.fixTypeEverywhereTyped(
         "EntityPaintingFix",
         var4,
         var2x -> var2x.updateTyped(var1, var0, var0xx -> var0xx.update(DSL.remainderFinder(), var0xxx -> this.a(var0xxx, true, false)))
      );
      TypeRewriteRule var6 = this.fixTypeEverywhereTyped(
         "EntityItemFrameFix",
         var4,
         var2x -> var2x.updateTyped(var3, var2, var0xx -> var0xx.update(DSL.remainderFinder(), var0xxx -> this.a(var0xxx, false, true)))
      );
      return TypeRewriteRule.seq(var5, var6);
   }
}
