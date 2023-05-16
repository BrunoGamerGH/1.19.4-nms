package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class DataConverterEntityRavagerRename extends DataConverterEntityRenameAbstract {
   public static final Map<String, String> a = ImmutableMap.builder().put("minecraft:illager_beast_spawn_egg", "minecraft:ravager_spawn_egg").build();

   public DataConverterEntityRavagerRename(Schema var0, boolean var1) {
      super("EntityRavagerRenameFix", var0, var1);
   }

   @Override
   protected String a(String var0) {
      return Objects.equals("minecraft:illager_beast", var0) ? "minecraft:ravager" : var0;
   }
}
