package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.MathHelper;

public class DataConverterVillagerLevelXp extends DataFix {
   private static final int a = 2;
   private static final int[] b = new int[]{0, 10, 50, 100, 150};

   public static int a(int var0) {
      return b[MathHelper.a(var0 - 1, 0, b.length - 1)];
   }

   public DataConverterVillagerLevelXp(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getChoiceType(DataConverterTypes.q, "minecraft:villager");
      OpticFinder<?> var1 = DSL.namedChoice("minecraft:villager", var0);
      OpticFinder<?> var2 = var0.findField("Offers");
      Type<?> var3 = var2.type();
      OpticFinder<?> var4 = var3.findField("Recipes");
      ListType<?> var5 = (ListType)var4.type();
      OpticFinder<?> var6 = var5.getElement().finder();
      return this.fixTypeEverywhereTyped(
         "Villager level and xp rebuild",
         this.getInputSchema().getType(DataConverterTypes.q),
         var5x -> var5x.updateTyped(
               var1,
               var0,
               var3xx -> {
                  Dynamic<?> var4xx = (Dynamic)var3xx.get(DSL.remainderFinder());
                  int var5xx = var4xx.get("VillagerData").get("level").asInt(0);
                  Typed<?> var6x = var3xx;
                  if (var5xx == 0 || var5xx == 1) {
                     int var7x = var3xx.getOptionalTyped(var2)
                        .flatMap(var1xxx -> var1xxx.getOptionalTyped(var4))
                        .map(var1xxx -> var1xxx.getAllTyped(var6).size())
                        .orElse(0);
                     var5xx = MathHelper.a(var7x / 2, 1, 5);
                     if (var5xx > 1) {
                        var6x = a(var3xx, var5xx);
                     }
                  }
      
                  Optional<Number> var7 = var4xx.get("Xp").asNumber().result();
                  if (!var7.isPresent()) {
                     var6x = b(var6x, var5xx);
                  }
      
                  return var6x;
               }
            )
      );
   }

   private static Typed<?> a(Typed<?> var0, int var1) {
      return var0.update(DSL.remainderFinder(), var1x -> var1x.update("VillagerData", var1xx -> var1xx.set("level", var1xx.createInt(var1))));
   }

   private static Typed<?> b(Typed<?> var0, int var1) {
      int var2 = a(var1);
      return var0.update(DSL.remainderFinder(), var1x -> var1x.set("Xp", var1x.createInt(var2)));
   }
}
