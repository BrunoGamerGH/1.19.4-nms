package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class DataConverterSchemaV1510 extends DataConverterSchemaNamed {
   public DataConverterSchemaV1510(int var0, Schema var1) {
      super(var0, var1);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var0) {
      Map<String, Supplier<TypeTemplate>> var1 = super.registerEntities(var0);
      var1.put("minecraft:command_block_minecart", var1.remove("minecraft:commandblock_minecart"));
      var1.put("minecraft:end_crystal", var1.remove("minecraft:ender_crystal"));
      var1.put("minecraft:snow_golem", var1.remove("minecraft:snowman"));
      var1.put("minecraft:evoker", var1.remove("minecraft:evocation_illager"));
      var1.put("minecraft:evoker_fangs", var1.remove("minecraft:evocation_fangs"));
      var1.put("minecraft:illusioner", var1.remove("minecraft:illusion_illager"));
      var1.put("minecraft:vindicator", var1.remove("minecraft:vindication_illager"));
      var1.put("minecraft:iron_golem", var1.remove("minecraft:villager_golem"));
      var1.put("minecraft:experience_orb", var1.remove("minecraft:xp_orb"));
      var1.put("minecraft:experience_bottle", var1.remove("minecraft:xp_bottle"));
      var1.put("minecraft:eye_of_ender", var1.remove("minecraft:eye_of_ender_signal"));
      var1.put("minecraft:firework_rocket", var1.remove("minecraft:fireworks_rocket"));
      return var1;
   }
}
