package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class DataConverterBlockEntityUUID extends DataConverterUUIDBase {
   public DataConverterBlockEntityUUID(Schema var0) {
      super(var0, DataConverterTypes.l);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.a), var0 -> {
         var0 = this.a(var0, "minecraft:conduit", this::c);
         return this.a(var0, "minecraft:skull", this::b);
      });
   }

   private Dynamic<?> b(Dynamic<?> var0) {
      return (Dynamic<?>)var0.get("Owner")
         .get()
         .map(var0x -> (Dynamic)a(var0x, "Id", "Id").orElse(var0x))
         .map(var1x -> var0.remove("Owner").set("SkullOwner", var1x))
         .result()
         .orElse(var0);
   }

   private Dynamic<?> c(Dynamic<?> var0) {
      return (Dynamic<?>)b(var0, "target_uuid", "Target").orElse(var0);
   }
}
