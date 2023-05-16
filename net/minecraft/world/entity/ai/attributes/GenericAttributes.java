package net.minecraft.world.entity.ai.attributes;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spigotmc.SpigotConfig;

public class GenericAttributes {
   public static final AttributeBase a = a(
      "generic.max_health", new AttributeRanged("attribute.name.generic.max_health", 20.0, 1.0, SpigotConfig.maxHealth).a(true)
   );
   public static final AttributeBase b = a("generic.follow_range", new AttributeRanged("attribute.name.generic.follow_range", 32.0, 0.0, 2048.0));
   public static final AttributeBase c = a("generic.knockback_resistance", new AttributeRanged("attribute.name.generic.knockback_resistance", 0.0, 0.0, 1.0));
   public static final AttributeBase d = a(
      "generic.movement_speed", new AttributeRanged("attribute.name.generic.movement_speed", 0.7F, 0.0, SpigotConfig.movementSpeed).a(true)
   );
   public static final AttributeBase e = a("generic.flying_speed", new AttributeRanged("attribute.name.generic.flying_speed", 0.4F, 0.0, 1024.0).a(true));
   public static final AttributeBase f = a(
      "generic.attack_damage", new AttributeRanged("attribute.name.generic.attack_damage", 2.0, 0.0, SpigotConfig.attackDamage)
   );
   public static final AttributeBase g = a("generic.attack_knockback", new AttributeRanged("attribute.name.generic.attack_knockback", 0.0, 0.0, 5.0));
   public static final AttributeBase h = a("generic.attack_speed", new AttributeRanged("attribute.name.generic.attack_speed", 4.0, 0.0, 1024.0).a(true));
   public static final AttributeBase i = a("generic.armor", new AttributeRanged("attribute.name.generic.armor", 0.0, 0.0, 30.0).a(true));
   public static final AttributeBase j = a("generic.armor_toughness", new AttributeRanged("attribute.name.generic.armor_toughness", 0.0, 0.0, 20.0).a(true));
   public static final AttributeBase k = a("generic.luck", new AttributeRanged("attribute.name.generic.luck", 0.0, -1024.0, 1024.0).a(true));
   public static final AttributeBase l = a("zombie.spawn_reinforcements", new AttributeRanged("attribute.name.zombie.spawn_reinforcements", 0.0, 0.0, 1.0));
   public static final AttributeBase m = a("horse.jump_strength", new AttributeRanged("attribute.name.horse.jump_strength", 0.7, 0.0, 2.0).a(true));

   private static AttributeBase a(String s, AttributeBase attributebase) {
      return IRegistry.a(BuiltInRegistries.u, s, attributebase);
   }
}
