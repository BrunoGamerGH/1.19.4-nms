package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class DataConverterEntityPufferfish extends DataConverterEntityRenameAbstract {
   public static final Map<String, String> a = ImmutableMap.builder().put("minecraft:puffer_fish_spawn_egg", "minecraft:pufferfish_spawn_egg").build();

   public DataConverterEntityPufferfish(Schema var0, boolean var1) {
      super("EntityPufferfishRenameFix", var0, var1);
   }

   @Override
   protected String a(String var0) {
      return Objects.equals("minecraft:puffer_fish", var0) ? "minecraft:pufferfish" : var0;
   }
}
