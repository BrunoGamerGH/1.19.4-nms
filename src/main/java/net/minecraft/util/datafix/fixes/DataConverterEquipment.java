package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DataConverterEquipment extends DataFix {
   public DataConverterEquipment(Schema var0, boolean var1) {
      super(var0, var1);
   }

   public TypeRewriteRule makeRule() {
      return this.a(this.getInputSchema().getTypeRaw(DataConverterTypes.m));
   }

   private <IS> TypeRewriteRule a(Type<IS> var0) {
      Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> var1 = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(var0))), DSL.remainderType());
      Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> var2 = DSL.and(
         DSL.optional(DSL.field("ArmorItems", DSL.list(var0))), DSL.optional(DSL.field("HandItems", DSL.list(var0))), DSL.remainderType()
      );
      OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> var3 = DSL.typeFinder(var1);
      OpticFinder<List<IS>> var4 = DSL.fieldFinder("Equipment", DSL.list(var0));
      return this.fixTypeEverywhereTyped(
         "EntityEquipmentToArmorAndHandFix",
         this.getInputSchema().getType(DataConverterTypes.q),
         this.getOutputSchema().getType(DataConverterTypes.q),
         var4x -> {
            Either<List<IS>, Unit> var5x = Either.right(DSL.unit());
            Either<List<IS>, Unit> var6 = Either.right(DSL.unit());
            Dynamic<?> var7 = (Dynamic)var4x.getOrCreate(DSL.remainderFinder());
            Optional<List<IS>> var8 = var4x.getOptional(var4);
            if (var8.isPresent()) {
               List<IS> var9 = var8.get();
               IS var10 = (IS)((Pair)var0.read(var7.emptyMap())
                     .result()
                     .orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack.")))
                  .getFirst();
               if (!var9.isEmpty()) {
                  var5x = Either.left(Lists.newArrayList(new Object[]{var9.get(0), var10}));
               }
   
               if (var9.size() > 1) {
                  List<IS> var11 = Lists.newArrayList(new Object[]{var10, var10, var10, var10});
   
                  for(int var12 = 1; var12 < Math.min(var9.size(), 5); ++var12) {
                     var11.set(var12 - 1, var9.get(var12));
                  }
   
                  var6 = Either.left(var11);
               }
            }
   
            Dynamic<?> var9 = var7;
            Optional<? extends Stream<? extends Dynamic<?>>> var10 = var7.get("DropChances").asStreamOpt().result();
            if (var10.isPresent()) {
               Iterator<? extends Dynamic<?>> var11 = Stream.concat(var10.get(), Stream.generate(() -> var9.createInt(0))).iterator();
               float var12 = ((Dynamic)var11.next()).asFloat(0.0F);
               if (!var7.get("HandDropChances").result().isPresent()) {
                  Dynamic<?> var13 = var7.createList(Stream.of(var12, 0.0F).map(var7::createFloat));
                  var7 = var7.set("HandDropChances", var13);
               }
   
               if (!var7.get("ArmorDropChances").result().isPresent()) {
                  Dynamic<?> var13 = var7.createList(
                     Stream.of(
                           ((Dynamic)var11.next()).asFloat(0.0F),
                           ((Dynamic)var11.next()).asFloat(0.0F),
                           ((Dynamic)var11.next()).asFloat(0.0F),
                           ((Dynamic)var11.next()).asFloat(0.0F)
                        )
                        .map(var7::createFloat)
                  );
                  var7 = var7.set("ArmorDropChances", var13);
               }
   
               var7 = var7.remove("DropChances");
            }
   
            return var4x.set(var3, var2, Pair.of(var5x, Pair.of(var6, var7)));
         }
      );
   }
}
