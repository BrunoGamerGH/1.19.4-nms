package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public class DataConverterSchemaV102 extends Schema {
   public DataConverterSchemaV102(int var0, Schema var1) {
      super(var0, var1);
   }

   public void registerTypes(Schema var0, Map<String, Supplier<TypeTemplate>> var1, Map<String, Supplier<TypeTemplate>> var2) {
      super.registerTypes(var0, var1, var2);
      var0.registerType(
         true,
         DataConverterTypes.m,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DataConverterTypes.s.in(var0),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     DataConverterTypes.p.in(var0),
                     "BlockEntityTag",
                     DataConverterTypes.l.in(var0),
                     "CanDestroy",
                     DSL.list(DataConverterTypes.r.in(var0)),
                     "CanPlaceOn",
                     DSL.list(DataConverterTypes.r.in(var0)),
                     "Items",
                     DSL.list(DataConverterTypes.m.in(var0))
                  )
               ),
               DataConverterSchemaV99.a,
               HookFunction.IDENTITY
            )
      );
   }
}
