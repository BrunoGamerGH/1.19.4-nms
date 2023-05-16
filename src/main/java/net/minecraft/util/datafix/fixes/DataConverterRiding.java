package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DataConverterRiding extends DataFix {
   public DataConverterRiding(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      Schema var1 = this.getOutputSchema();
      Type<?> var2 = var0.getTypeRaw(DataConverterTypes.p);
      Type<?> var3 = var1.getTypeRaw(DataConverterTypes.p);
      Type<?> var4 = var0.getTypeRaw(DataConverterTypes.q);
      return this.a(var0, var1, var2, var3, var4);
   }

   private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule a(
      Schema var0, Schema var1, Type<OldEntityTree> var2, Type<NewEntityTree> var3, Type<Entity> var4
   ) {
      Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> var5 = DSL.named(
         DataConverterTypes.p.typeName(), DSL.and(DSL.optional(DSL.field("Riding", var2)), var4)
      );
      Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> var6 = DSL.named(
         DataConverterTypes.p.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(var3))), var4)
      );
      Type<?> var7 = var0.getType(DataConverterTypes.p);
      Type<?> var8 = var1.getType(DataConverterTypes.p);
      if (!Objects.equals(var7, var5)) {
         throw new IllegalStateException("Old entity type is not what was expected.");
      } else if (!var8.equals(var6, true, true)) {
         throw new IllegalStateException("New entity type is not what was expected.");
      } else {
         OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> var9 = DSL.typeFinder(var5);
         OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> var10 = DSL.typeFinder(var6);
         OpticFinder<NewEntityTree> var11 = DSL.typeFinder(var3);
         Type<?> var12 = var0.getType(DataConverterTypes.b);
         Type<?> var13 = var1.getType(DataConverterTypes.b);
         return TypeRewriteRule.seq(
            this.fixTypeEverywhere(
               "EntityRidingToPassengerFix",
               var5,
               var6,
               var5x -> var6x -> {
                     Optional<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> var7x = Optional.empty();
                     Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>> var8x = var6x;
      
                     while(true) {
                        Either<List<NewEntityTree>, Unit> var9x = (Either)DataFixUtils.orElse(
                           var7x.map(
                              var4xxx -> {
                                 Typed<NewEntityTree> var5xxx = (Typed)var3.pointTyped(var5x)
                                    .orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                                 NewEntityTree var6xx = var5xxx.set(var10, var4xxx)
                                    .getOptional(var11)
                                    .orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                                 return Either.left(ImmutableList.of(var6xx));
                              }
                           ),
                           Either.right(DSL.unit())
                        );
                        var7x = Optional.of(Pair.of(DataConverterTypes.p.typeName(), Pair.of(var9x, ((Pair)var8x.getSecond()).getSecond())));
                        Optional<OldEntityTree> var10x = ((Either)((Pair)var8x.getSecond()).getFirst()).left();
                        if (!var10x.isPresent()) {
                           return (Pair)var7x.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                        }
      
                        var8x = (Pair)new Typed(var2, var5x, var10x.get())
                           .getOptional(var9)
                           .orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
                     }
                  }
            ),
            this.writeAndRead("player RootVehicle injecter", var12, var13)
         );
      }
   }
}
