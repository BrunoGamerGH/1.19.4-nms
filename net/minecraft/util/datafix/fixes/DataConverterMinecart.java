package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;

public class DataConverterMinecart extends DataFix {
   private static final List<String> a = Lists.newArrayList(new String[]{"MinecartRideable", "MinecartChest", "MinecartFurnace"});

   public DataConverterMinecart(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> var0 = this.getInputSchema().findChoiceType(DataConverterTypes.q);
      TaggedChoiceType<String> var1 = this.getOutputSchema().findChoiceType(DataConverterTypes.q);
      return this.fixTypeEverywhere(
         "EntityMinecartIdentifiersFix",
         var0,
         var1,
         var2x -> var3 -> {
               if (!Objects.equals(var3.getFirst(), "Minecart")) {
                  return var3;
               } else {
                  Typed<? extends Pair<String, ?>> var4 = (Typed)var0.point(var2x, "Minecart", var3.getSecond()).orElseThrow(IllegalStateException::new);
                  Dynamic<?> var5 = (Dynamic)var4.getOrCreate(DSL.remainderFinder());
                  int var7 = var5.get("Type").asInt(0);
                  String var6;
                  if (var7 > 0 && var7 < a.size()) {
                     var6 = a.get(var7);
                  } else {
                     var6 = "MinecartRideable";
                  }
   
                  return Pair.of(
                     var6,
                     (DataResult)var4.write()
                        .map(var2xxx -> ((Type)var1.types().get(var6)).read(var2xxx))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))
                  );
               }
            }
      );
   }
}
