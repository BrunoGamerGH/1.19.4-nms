package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV1451_6 extends DataConverterSchemaNamed {
   public static final String b = "_special";
   protected static final HookFunction c = new HookFunction() {
      public <T> T apply(DynamicOps<T> var0, T var1) {
         Dynamic<T> var2 = new Dynamic(var0, var1);
         return (T)((Dynamic)DataFixUtils.orElse(
               var2.get("CriteriaName")
                  .asString()
                  .get()
                  .left()
                  .map(var0x -> {
                     int var1x = var0x.indexOf(58);
                     if (var1x < 0) {
                        return Pair.of("_special", var0x);
                     } else {
                        try {
                           MinecraftKey var2x = MinecraftKey.a(var0x.substring(0, var1x), '.');
                           MinecraftKey var3x = MinecraftKey.a(var0x.substring(var1x + 1), '.');
                           return Pair.of(var2x.toString(), var3x.toString());
                        } catch (Exception var4) {
                           return Pair.of("_special", var0x);
                        }
                     }
                  })
                  .map(
                     var1x -> var2.set(
                           "CriteriaType",
                           var2.createMap(
                              ImmutableMap.of(
                                 var2.createString("type"),
                                 var2.createString((String)var1x.getFirst()),
                                 var2.createString("id"),
                                 var2.createString((String)var1x.getSecond())
                              )
                           )
                        )
                  ),
               var2
            ))
            .getValue();
      }
   };
   protected static final HookFunction d = new HookFunction() {
      private String a(String var0) {
         MinecraftKey var1 = MinecraftKey.a(var0);
         return var1 != null ? var1.b() + "." + var1.a() : var0;
      }

      public <T> T apply(DynamicOps<T> var0, T var1) {
         Dynamic<T> var2 = new Dynamic(var0, var1);
         Optional<Dynamic<T>> var3 = var2.get("CriteriaType")
            .get()
            .get()
            .left()
            .flatMap(
               var1x -> {
                  Optional<String> var2x = var1x.get("type").asString().get().left();
                  Optional<String> var3x = var1x.get("id").asString().get().left();
                  if (var2x.isPresent() && var3x.isPresent()) {
                     String var4x = (String)var2x.get();
                     return var4x.equals("_special")
                        ? Optional.of((T)var2.createString((String)var3x.get()))
                        : Optional.of((T)var1x.createString(this.a(var4x) + ":" + this.a((String)var3x.get())));
                  } else {
                     return Optional.empty();
                  }
               }
            );
         return (T)((Dynamic)DataFixUtils.orElse(var3.map(var1x -> var2.set("CriteriaName", var1x).remove("CriteriaType")), var2)).getValue();
      }
   };

   public DataConverterSchemaV1451_6(int var0, Schema var1) {
      super(var0, var1);
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      Supplier<TypeTemplate> var3 = () -> DSL.compoundList(DataConverterTypes.s.in(var0), DSL.constType(DSL.intType()));
      var0.registerType(
         false,
         DataConverterTypes.g,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(DataConverterTypes.r.in(var0), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  (TypeTemplate)var3.get(),
                  "minecraft:used",
                  (TypeTemplate)var3.get(),
                  "minecraft:broken",
                  (TypeTemplate)var3.get(),
                  "minecraft:picked_up",
                  (TypeTemplate)var3.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     (TypeTemplate)var3.get(),
                     "minecraft:killed",
                     DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(DataConverterTypes.o.in(var0), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(a()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
      Map<String, Supplier<TypeTemplate>> var4 = a(var0);
      var0.registerType(
         false, DataConverterTypes.w, () -> DSL.hook(DSL.optionalFields("CriteriaType", DSL.taggedChoiceLazy("type", DSL.string(), var4)), c, d)
      );
   }

   protected static Map<String, Supplier<TypeTemplate>> a(Schema var0) {
      Supplier<TypeTemplate> var1 = () -> DSL.optionalFields("id", DataConverterTypes.s.in(var0));
      Supplier<TypeTemplate> var2 = () -> DSL.optionalFields("id", DataConverterTypes.r.in(var0));
      Supplier<TypeTemplate> var3 = () -> DSL.optionalFields("id", DataConverterTypes.o.in(var0));
      Map<String, Supplier<TypeTemplate>> var4 = Maps.newHashMap();
      var4.put("minecraft:mined", var2);
      var4.put("minecraft:crafted", var1);
      var4.put("minecraft:used", var1);
      var4.put("minecraft:broken", var1);
      var4.put("minecraft:picked_up", var1);
      var4.put("minecraft:dropped", var1);
      var4.put("minecraft:killed", var3);
      var4.put("minecraft:killed_by", var3);
      var4.put("minecraft:custom", () -> DSL.optionalFields("id", DSL.constType(a())));
      var4.put("_special", () -> DSL.optionalFields("id", DSL.constType(DSL.string())));
      return var4;
   }
}
