package org.bukkit.craftbukkit.v1_19_R3.attribute;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;

public class CraftAttributeInstance implements AttributeInstance {
   private final AttributeModifiable handle;
   private final Attribute attribute;

   public CraftAttributeInstance(AttributeModifiable handle, Attribute attribute) {
      this.handle = handle;
      this.attribute = attribute;
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public double getBaseValue() {
      return this.handle.b();
   }

   public void setBaseValue(double d) {
      this.handle.a(d);
   }

   public Collection<AttributeModifier> getModifiers() {
      List<AttributeModifier> result = new ArrayList();

      for(net.minecraft.world.entity.ai.attributes.AttributeModifier nms : this.handle.c()) {
         result.add(convert(nms));
      }

      return result;
   }

   public void addModifier(AttributeModifier modifier) {
      Preconditions.checkArgument(modifier != null, "modifier");
      this.handle.c(convert(modifier));
   }

   public void removeModifier(AttributeModifier modifier) {
      Preconditions.checkArgument(modifier != null, "modifier");
      this.handle.d(convert(modifier));
   }

   public double getValue() {
      return this.handle.f();
   }

   public double getDefaultValue() {
      return this.handle.a().a();
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeModifier convert(AttributeModifier bukkit) {
      return new net.minecraft.world.entity.ai.attributes.AttributeModifier(
         bukkit.getUniqueId(),
         bukkit.getName(),
         bukkit.getAmount(),
         net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.values()[bukkit.getOperation().ordinal()]
      );
   }

   public static AttributeModifier convert(net.minecraft.world.entity.ai.attributes.AttributeModifier nms) {
      return new AttributeModifier(nms.a(), nms.b(), nms.d(), Operation.values()[nms.c().ordinal()]);
   }

   public static AttributeModifier convert(net.minecraft.world.entity.ai.attributes.AttributeModifier nms, EquipmentSlot slot) {
      return new AttributeModifier(nms.a(), nms.b(), nms.d(), Operation.values()[nms.c().ordinal()], slot);
   }
}
