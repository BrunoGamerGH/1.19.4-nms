package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public abstract class BlockRenameFixWithJigsaw extends DataConverterBlockRename {
   private final String a;

   public BlockRenameFixWithJigsaw(Schema var0, String var1) {
      super(var0, var1);
      this.a = var1;
   }

   @Override
   public TypeRewriteRule makeRule() {
      TypeReference var0 = DataConverterTypes.l;
      String var1 = "minecraft:jigsaw";
      OpticFinder<?> var2 = DSL.namedChoice("minecraft:jigsaw", this.getInputSchema().getChoiceType(var0, "minecraft:jigsaw"));
      TypeRewriteRule var3 = this.fixTypeEverywhereTyped(
         this.a + " for jigsaw state",
         this.getInputSchema().getType(var0),
         this.getOutputSchema().getType(var0),
         var2x -> var2x.updateTyped(
               var2,
               this.getOutputSchema().getChoiceType(var0, "minecraft:jigsaw"),
               var0xx -> var0xx.update(
                     DSL.remainderFinder(),
                     var0xxx -> var0xxx.update("final_state", var1xx -> (Dynamic)DataFixUtils.orElse(var1xx.asString().result().map(var0xxxxx -> {
                              int var1xxx = var0xxxxx.indexOf(91);
                              int var2xx = var0xxxxx.indexOf(123);
                              int var3x = var0xxxxx.length();
                              if (var1xxx > 0) {
                                 var3x = Math.min(var3x, var1xxx);
                              }
         
                              if (var2xx > 0) {
                                 var3x = Math.min(var3x, var2xx);
                              }
         
                              String var4x = var0xxxxx.substring(0, var3x);
                              String var5 = this.a(var4x);
                              return var5 + var0xxxxx.substring(var3x);
                           }).map(var0xxx::createString), var1xx))
                  )
            )
      );
      return TypeRewriteRule.seq(super.makeRule(), var3);
   }

   public static DataFix b(Schema var0, String var1, final Function<String, String> var2) {
      return new BlockRenameFixWithJigsaw(var0, var1) {
         @Override
         protected String a(String var0) {
            return var2.apply(var0);
         }
      };
   }
}
