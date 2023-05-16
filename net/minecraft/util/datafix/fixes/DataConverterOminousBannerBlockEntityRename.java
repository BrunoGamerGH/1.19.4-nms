package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class DataConverterOminousBannerBlockEntityRename extends DataConverterNamedEntity {
   public DataConverterOminousBannerBlockEntityRename(Schema var0, boolean var1) {
      super(var0, var1, "OminousBannerBlockEntityRenameFix", DataConverterTypes.l, "minecraft:banner");
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }

   private Dynamic<?> a(Dynamic<?> var0) {
      Optional<String> var1 = var0.get("CustomName").asString().result();
      if (var1.isPresent()) {
         String var2 = var1.get();
         var2 = var2.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
         return var0.set("CustomName", var0.createString(var2));
      } else {
         return var0;
      }
   }
}
