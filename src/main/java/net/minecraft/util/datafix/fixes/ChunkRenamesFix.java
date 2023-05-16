package net.minecraft.util.datafix.fixes;

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
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;

public class ChunkRenamesFix extends DataFix {
   public ChunkRenamesFix(Schema var0) {
      super(var0, true);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> var0 = this.getInputSchema().getType(DataConverterTypes.c);
      OpticFinder<?> var1 = var0.findField("Level");
      OpticFinder<?> var2 = var1.type().findField("Structures");
      Type<?> var3 = this.getOutputSchema().getType(DataConverterTypes.c);
      Type<?> var4 = var3.findFieldType("structures");
      return this.fixTypeEverywhereTyped("Chunk Renames; purge Level-tag", var0, var3, var3x -> {
         Typed<?> var4x = var3x.getTyped(var1);
         Typed<?> var5x = a(var4x);
         var5x = var5x.set(DSL.remainderFinder(), a(var3x, (Dynamic)var4x.get(DSL.remainderFinder())));
         var5x = a(var5x, "TileEntities", "block_entities");
         var5x = a(var5x, "TileTicks", "block_ticks");
         var5x = a(var5x, "Entities", "entities");
         var5x = a(var5x, "Sections", "sections");
         var5x = var5x.updateTyped(var2, var4, var0xx -> a(var0xx, "Starts", "starts"));
         var5x = a(var5x, "Structures", "structures");
         return var5x.update(DSL.remainderFinder(), var0xx -> var0xx.remove("Level"));
      });
   }

   private static Typed<?> a(Typed<?> var0, String var1, String var2) {
      return a(var0, var1, var2, var0.getType().findFieldType(var1)).update(DSL.remainderFinder(), var1x -> var1x.remove(var1));
   }

   private static <A> Typed<?> a(Typed<?> var0, String var1, String var2, Type<A> var3) {
      Type<Either<A, Unit>> var4 = DSL.optional(DSL.field(var1, var3));
      Type<Either<A, Unit>> var5 = DSL.optional(DSL.field(var2, var3));
      return var0.update(var4.finder(), var5, Function.identity());
   }

   private static <A> Typed<Pair<String, A>> a(Typed<A> var0) {
      return new Typed(DSL.named("chunk", var0.getType()), var0.getOps(), Pair.of("chunk", var0.getValue()));
   }

   private static <T> Dynamic<T> a(Typed<?> var0, Dynamic<T> var1) {
      DynamicOps<T> var2 = var1.getOps();
      Dynamic<T> var3 = ((Dynamic)var0.get(DSL.remainderFinder())).convert(var2);
      DataResult<T> var4 = var2.getMap(var1.getValue()).flatMap(var2x -> var2.mergeToMap(var3.getValue(), var2x));
      return (Dynamic<T>)var4.result().map(var1x -> new Dynamic(var2, var1x)).orElse((T)var1);
   }
}
