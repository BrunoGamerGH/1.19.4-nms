package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.util.datafix.schemas.DataConverterSchemaNamed;

public class DataConverterMissingDimension extends DataFix {
   public DataConverterMissingDimension(Schema var0, boolean var1) {
      super(var0, var1);
   }

   protected static <A> Type<Pair<A, Dynamic<?>>> a(String var0, Type<A> var1) {
      return DSL.and(DSL.field(var0, var1), DSL.remainderType());
   }

   protected static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> b(String var0, Type<A> var1) {
      return DSL.and(DSL.optional(DSL.field(var0, var1)), DSL.remainderType());
   }

   protected static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> a(String var0, Type<A1> var1, String var2, Type<A2> var3) {
      return DSL.and(DSL.optional(DSL.field(var0, var1)), DSL.optional(DSL.field(var2, var3)), DSL.remainderType());
   }

   protected TypeRewriteRule makeRule() {
      Schema var0 = this.getInputSchema();
      Type<?> var1 = DSL.taggedChoiceType(
         "type",
         DSL.string(),
         ImmutableMap.of(
            "minecraft:debug",
            DSL.remainderType(),
            "minecraft:flat",
            a(var0),
            "minecraft:noise",
            a(
               "biome_source",
               DSL.taggedChoiceType(
                  "type",
                  DSL.string(),
                  ImmutableMap.of(
                     "minecraft:fixed",
                     a("biome", var0.getType(DataConverterTypes.z)),
                     "minecraft:multi_noise",
                     DSL.list(a("biome", var0.getType(DataConverterTypes.z))),
                     "minecraft:checkerboard",
                     a("biomes", DSL.list(var0.getType(DataConverterTypes.z))),
                     "minecraft:vanilla_layered",
                     DSL.remainderType(),
                     "minecraft:the_end",
                     DSL.remainderType()
                  )
               ),
               "settings",
               DSL.or(DSL.string(), a("default_block", var0.getType(DataConverterTypes.r), "default_fluid", var0.getType(DataConverterTypes.r)))
            )
         )
      );
      CompoundListType<String, ?> var2 = DSL.compoundList(DataConverterSchemaNamed.a(), a("generator", var1));
      Type<?> var3 = DSL.and(var2, DSL.remainderType());
      Type<?> var4 = var0.getType(DataConverterTypes.A);
      FieldFinder<?> var5 = new FieldFinder("dimensions", var3);
      if (!var4.findFieldType("dimensions").equals(var3)) {
         throw new IllegalStateException();
      } else {
         OpticFinder<? extends List<? extends Pair<String, ?>>> var6 = var2.finder();
         return this.fixTypeEverywhereTyped("MissingDimensionFix", var4, var3x -> var3x.updateTyped(var5, var3xx -> var3xx.updateTyped(var6, var2xxx -> {
                  if (!(var2xxx.getValue() instanceof List)) {
                     throw new IllegalStateException("List exptected");
                  } else if (((List)var2xxx.getValue()).isEmpty()) {
                     Dynamic<?> var3xxx = (Dynamic)var3x.get(DSL.remainderFinder());
                     Dynamic<?> var4x = this.a(var3xxx);
                     return (Typed)DataFixUtils.orElse(var2.readTyped(var4x).result().map(Pair::getFirst), var2xxx);
                  } else {
                     return var2xxx;
                  }
               })));
      }
   }

   protected static Type<? extends Pair<? extends Either<? extends Pair<? extends Either<?, Unit>, ? extends Pair<? extends Either<? extends List<? extends Pair<? extends Either<?, Unit>, Dynamic<?>>>, Unit>, Dynamic<?>>>, Unit>, Dynamic<?>>> a(
      Schema var0
   ) {
      return b("settings", a("biome", var0.getType(DataConverterTypes.z), "layers", DSL.list(b("block", var0.getType(DataConverterTypes.r)))));
   }

   private <T> Dynamic<T> a(Dynamic<T> var0) {
      long var1 = var0.get("seed").asLong(0L);
      return new Dynamic(var0.getOps(), DataConverterWorldGenSettingsBuilding.a(var0, var1, DataConverterWorldGenSettingsBuilding.a(var0, var1), false));
   }
}
