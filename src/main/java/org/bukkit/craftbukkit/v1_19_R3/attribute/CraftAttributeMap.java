package org.bukkit.craftbukkit.v1_19_R3.attribute;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import org.bukkit.Registry;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

public class CraftAttributeMap implements Attributable {
   private final AttributeMapBase handle;

   public CraftAttributeMap(AttributeMapBase handle) {
      this.handle = handle;
   }

   public AttributeInstance getAttribute(Attribute attribute) {
      Preconditions.checkArgument(attribute != null, "attribute");
      AttributeModifiable nms = this.handle.a(toMinecraft(attribute));
      return nms == null ? null : new CraftAttributeInstance(nms, attribute);
   }

   public static AttributeBase toMinecraft(Attribute attribute) {
      return BuiltInRegistries.u.a(CraftNamespacedKey.toMinecraft(attribute.getKey()));
   }

   public static Attribute fromMinecraft(String nms) {
      return (Attribute)Registry.ATTRIBUTE.get(CraftNamespacedKey.fromString(nms));
   }
}
