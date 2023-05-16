package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Map;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class StatsRenameFix extends DataFix {
   private final String a;
   private final Map<String, String> b;

   public StatsRenameFix(Schema var0, String var1, Map<String, String> var2) {
      super(var0, false);
      this.a = var1;
      this.b = var2;
   }

   protected TypeRewriteRule makeRule() {
      return TypeRewriteRule.seq(this.b(), this.a());
   }

   private TypeRewriteRule a() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.w);
      Type<?> var1 = this.getInputSchema().getType(DataConverterTypes.w);
      OpticFinder<?> var2 = var1.findField("CriteriaType");
      TaggedChoiceType<?> var3 = (TaggedChoiceType)var2.type()
         .findChoiceType("type", -1)
         .orElseThrow(() -> new IllegalStateException("Can't find choice type for criteria"));
      Type<?> var4 = (Type)var3.types().get("minecraft:custom");
      if (var4 == null) {
         throw new IllegalStateException("Failed to find custom criterion type variant");
      } else {
         OpticFinder<?> var5 = DSL.namedChoice("minecraft:custom", var4);
         OpticFinder<String> var6 = DSL.fieldFinder("id", DataConverterSchemaNamed.a());
         return this.fixTypeEverywhereTyped(
            this.a,
            var1,
            var0,
            var3x -> var3x.updateTyped(
                  var2, var2xx -> var2xx.updateTyped(var5, var1xxx -> var1xxx.update(var6, var0xxxx -> this.b.getOrDefault(var0xxxx, var0xxxx)))
               )
         );
      }
   }

   private TypeRewriteRule b() {
      Type<?> var0 = this.getOutputSchema().getType(DataConverterTypes.g);
      Type<?> var1 = this.getInputSchema().getType(DataConverterTypes.g);
      OpticFinder<?> var2 = var1.findField("stats");
      OpticFinder<?> var3 = var2.type().findField("minecraft:custom");
      OpticFinder<String> var4 = DataConverterSchemaNamed.a().finder();
      return this.fixTypeEverywhereTyped(
         this.a,
         var1,
         var0,
         var3x -> var3x.updateTyped(
               var2, var2xx -> var2xx.updateTyped(var3, var1xxx -> var1xxx.update(var4, var0xxxx -> this.b.getOrDefault(var0xxxx, var0xxxx)))
            )
      );
   }
}
