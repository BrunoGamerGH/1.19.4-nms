package org.bukkit.craftbukkit.v1_19_R3.tag;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;

public class CraftEntityTag extends CraftTag<EntityTypes<?>, EntityType> {
   public CraftEntityTag(IRegistry<EntityTypes<?>> registry, TagKey<EntityTypes<?>> tag) {
      super(registry, tag);
   }

   public boolean isTagged(EntityType entity) {
      return this.registry.f(ResourceKey.a(Registries.r, CraftNamespacedKey.toMinecraft(entity.getKey()))).a(this.tag);
   }

   public Set<EntityType> getValues() {
      return this.getHandle()
         .a()
         .map(nms -> (EntityType)Registry.ENTITY_TYPE.get(CraftNamespacedKey.fromMinecraft(EntityTypes.a((EntityTypes<?>)nms.a()))))
         .filter(Objects::nonNull)
         .collect(Collectors.toUnmodifiableSet());
   }
}
