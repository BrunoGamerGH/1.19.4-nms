package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterShulkerBoxItem extends DataFix {
   public static final String[] a = new String[]{
      "minecraft:white_shulker_box",
      "minecraft:orange_shulker_box",
      "minecraft:magenta_shulker_box",
      "minecraft:light_blue_shulker_box",
      "minecraft:yellow_shulker_box",
      "minecraft:lime_shulker_box",
      "minecraft:pink_shulker_box",
      "minecraft:gray_shulker_box",
      "minecraft:silver_shulker_box",
      "minecraft:cyan_shulker_box",
      "minecraft:purple_shulker_box",
      "minecraft:blue_shulker_box",
      "minecraft:brown_shulker_box",
      "minecraft:green_shulker_box",
      "minecraft:red_shulker_box",
      "minecraft:black_shulker_box"
   };

   public DataConverterShulkerBoxItem(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.m);
      OpticFinder<Pair<String, String>> var1 = DSL.fieldFinder("id", DSL.named(DataConverterTypes.s.typeName(), DataConverterSchemaNamed.a()));
      OpticFinder<?> var2 = var0.findField("tag");
      OpticFinder<?> var3 = var2.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemShulkerBoxColorFix",
         var0,
         var3x -> {
            Optional<Pair<String, String>> var4x = var3x.getOptional(var1);
            if (var4x.isPresent() && Objects.equals(((Pair)var4x.get()).getSecond(), "minecraft:shulker_box")) {
               Optional<? extends Typed<?>> var5 = var3x.getOptionalTyped(var2);
               if (var5.isPresent()) {
                  Typed<?> var6 = (Typed)var5.get();
                  Optional<? extends Typed<?>> var7 = var6.getOptionalTyped(var3);
                  if (var7.isPresent()) {
                     Typed<?> var8 = (Typed)var7.get();
                     Dynamic<?> var9 = (Dynamic)var8.get(DSL.remainderFinder());
                     int var10 = var9.get("Color").asInt(0);
                     var9.remove("Color");
                     return var3x.set(var2, var6.set(var3, var8.set(DSL.remainderFinder(), var9)))
                        .set(var1, Pair.of(DataConverterTypes.s.typeName(), a[var10 % 16]));
                  }
               }
            }
   
            return var3x;
         }
      );
   }
}
