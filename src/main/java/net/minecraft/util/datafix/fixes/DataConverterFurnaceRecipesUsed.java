package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;

public class DataConverterFurnaceRecipesUsed extends DataFix {
   public DataConverterFurnaceRecipesUsed(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected TypeRewriteRule makeRule() {
      return this.a(this.getOutputSchema().getTypeRaw(DataConverterTypes.y));
   }

   private <R> TypeRewriteRule a(Type<R> var0) {
      Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> var1 = DSL.and(
         DSL.optional(DSL.field("RecipesUsed", DSL.and(DSL.compoundList(var0, DSL.intType()), DSL.remainderType()))), DSL.remainderType()
      );
      OpticFinder<?> var2 = DSL.namedChoice("minecraft:furnace", this.getInputSchema().getChoiceType(DataConverterTypes.l, "minecraft:furnace"));
      OpticFinder<?> var3 = DSL.namedChoice("minecraft:blast_furnace", this.getInputSchema().getChoiceType(DataConverterTypes.l, "minecraft:blast_furnace"));
      OpticFinder<?> var4 = DSL.namedChoice("minecraft:smoker", this.getInputSchema().getChoiceType(DataConverterTypes.l, "minecraft:smoker"));
      Type<?> var5 = this.getOutputSchema().getChoiceType(DataConverterTypes.l, "minecraft:furnace");
      Type<?> var6 = this.getOutputSchema().getChoiceType(DataConverterTypes.l, "minecraft:blast_furnace");
      Type<?> var7 = this.getOutputSchema().getChoiceType(DataConverterTypes.l, "minecraft:smoker");
      Type<?> var8 = this.getInputSchema().getType(DataConverterTypes.l);
      Type<?> var9 = this.getOutputSchema().getType(DataConverterTypes.l);
      return this.fixTypeEverywhereTyped(
         "FurnaceRecipesFix",
         var8,
         var9,
         var8x -> var8x.updateTyped(var2, var5, var2xx -> this.a(var0, var1, var2xx))
               .updateTyped(var3, var6, var2xx -> this.a(var0, var1, var2xx))
               .updateTyped(var4, var7, var2xx -> this.a(var0, var1, var2xx))
      );
   }

   private <R> Typed<?> a(Type<R> var0, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> var1, Typed<?> var2) {
      Dynamic<?> var3 = (Dynamic)var2.getOrCreate(DSL.remainderFinder());
      int var4 = var3.get("RecipesUsedSize").asInt(0);
      var3 = var3.remove("RecipesUsedSize");
      List<Pair<R, Integer>> var5 = Lists.newArrayList();

      for(int var6 = 0; var6 < var4; ++var6) {
         String var7 = "RecipeLocation" + var6;
         String var8 = "RecipeAmount" + var6;
         Optional<? extends Dynamic<?>> var9 = var3.get(var7).result();
         int var10 = var3.get(var8).asInt(0);
         if (var10 > 0) {
            var9.ifPresent(var3x -> {
               Optional<? extends Pair<R, ? extends Dynamic<?>>> var4x = var0.read(var3x).result();
               var4x.ifPresent(var2xx -> var5.add(Pair.of(var2xx.getFirst(), var10)));
            });
         }

         var3 = var3.remove(var7).remove(var8);
      }

      return var2.set(DSL.remainderFinder(), var1, Pair.of(Either.left(Pair.of(var5, var3.emptyMap())), var3));
   }
}
