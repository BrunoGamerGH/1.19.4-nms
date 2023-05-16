package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.MinecraftKey;

public class DataConverterPainting extends DataConverterNamedEntity {
   private static final Map<String, String> a = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("donkeykong", "donkey_kong");
      var0.put("burningskull", "burning_skull");
      var0.put("skullandroses", "skull_and_roses");
   });

   public DataConverterPainting(Schema var0, boolean var1) {
      super(var0, var1, "EntityPaintingMotiveFix", DataConverterTypes.q, "minecraft:painting");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      Optional<String> var1 = var0.get("Motive").asString().result();
      if (var1.isPresent()) {
         String var2 = var1.get().toLowerCase(Locale.ROOT);
         return var0.set("Motive", var0.createString(new MinecraftKey(a.getOrDefault(var2, var2)).toString()));
      } else {
         return var0;
      }
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
