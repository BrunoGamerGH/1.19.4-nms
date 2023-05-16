package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class DataConverterOminousBannerRename extends ItemStackTagFix {
   public DataConverterOminousBannerRename(Schema var0) {
      super(var0, "OminousBannerRenameFix", var0x -> var0x.equals("minecraft:white_banner"));
   }

   @Override
   protected <T> Dynamic<T> a(Dynamic<T> var0) {
      Optional<? extends Dynamic<?>> var1 = var0.get("display").result();
      if (var1.isPresent()) {
         Dynamic<?> var2 = (Dynamic)var1.get();
         Optional<String> var3 = var2.get("Name").asString().result();
         if (var3.isPresent()) {
            String var4 = var3.get();
            var4 = var4.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
            var2 = var2.set("Name", var2.createString(var4));
         }

         return var0.set("display", var2);
      } else {
         return var0;
      }
   }
}
