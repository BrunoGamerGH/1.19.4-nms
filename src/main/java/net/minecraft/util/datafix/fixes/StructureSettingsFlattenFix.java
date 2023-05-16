package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class StructureSettingsFlattenFix extends DataFix {
   public StructureSettingsFlattenFix(Schema var0) {
      super(var0, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.A);
      OpticFinder<?> var1 = var0.findField("dimensions");
      return this.fixTypeEverywhereTyped("StructureSettingsFlatten", var0, var1x -> var1x.updateTyped(var1, var1xx -> {
            Dynamic<?> var2x = (Dynamic)var1xx.write().result().orElseThrow();
            Dynamic<?> var3 = var2x.updateMapValues(StructureSettingsFlattenFix::a);
            return (Typed)((Pair)var1.type().readTyped(var3).result().orElseThrow()).getFirst();
         }));
   }

   private static Pair<Dynamic<?>, Dynamic<?>> a(Pair<Dynamic<?>, Dynamic<?>> var0) {
      Dynamic<?> var1 = (Dynamic)var0.getSecond();
      return Pair.of(
         (Dynamic)var0.getFirst(),
         var1.update("generator", var0x -> var0x.update("settings", var0xx -> var0xx.update("structures", StructureSettingsFlattenFix::a)))
      );
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      Dynamic<?> var1 = var0.get("structures")
         .orElseEmptyMap()
         .updateMapValues(var1x -> var1x.mapSecond(var1xx -> var1xx.set("type", var0.createString("minecraft:random_spread"))));
      return (Dynamic<?>)DataFixUtils.orElse(
         var0.get("stronghold").result().map(var2 -> var1.set("minecraft:stronghold", var2.set("type", var0.createString("minecraft:concentric_rings")))),
         var1
      );
   }
}
