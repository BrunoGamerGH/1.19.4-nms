package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityTypes;

public final class TagsEntity {
   public static final TagKey<EntityTypes<?>> a = a("skeletons");
   public static final TagKey<EntityTypes<?>> b = a("raiders");
   public static final TagKey<EntityTypes<?>> c = a("beehive_inhabitors");
   public static final TagKey<EntityTypes<?>> d = a("arrows");
   public static final TagKey<EntityTypes<?>> e = a("impact_projectiles");
   public static final TagKey<EntityTypes<?>> f = a("powder_snow_walkable_mobs");
   public static final TagKey<EntityTypes<?>> g = a("axolotl_always_hostiles");
   public static final TagKey<EntityTypes<?>> h = a("axolotl_hunt_targets");
   public static final TagKey<EntityTypes<?>> i = a("freeze_immune_entity_types");
   public static final TagKey<EntityTypes<?>> j = a("freeze_hurts_extra_types");
   public static final TagKey<EntityTypes<?>> k = a("frog_food");
   public static final TagKey<EntityTypes<?>> l = a("fall_damage_immune");
   public static final TagKey<EntityTypes<?>> m = a("dismounts_underwater");

   private TagsEntity() {
   }

   private static TagKey<EntityTypes<?>> a(String var0) {
      return TagKey.a(Registries.r, new MinecraftKey(var0));
   }
}
