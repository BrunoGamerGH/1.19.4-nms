package org.bukkit.craftbukkit.v1_19_R3;

import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EnumItemSlot;
import org.bukkit.inventory.EquipmentSlot;

public class CraftEquipmentSlot {
   private static final EnumItemSlot[] slots = new EnumItemSlot[EquipmentSlot.values().length];
   private static final EquipmentSlot[] enums = new EquipmentSlot[EnumItemSlot.values().length];

   static {
      set(EquipmentSlot.HAND, EnumItemSlot.a);
      set(EquipmentSlot.OFF_HAND, EnumItemSlot.b);
      set(EquipmentSlot.FEET, EnumItemSlot.c);
      set(EquipmentSlot.LEGS, EnumItemSlot.d);
      set(EquipmentSlot.CHEST, EnumItemSlot.e);
      set(EquipmentSlot.HEAD, EnumItemSlot.f);
   }

   private static void set(EquipmentSlot type, EnumItemSlot value) {
      slots[type.ordinal()] = value;
      enums[value.ordinal()] = type;
   }

   public static EquipmentSlot getSlot(EnumItemSlot nms) {
      return enums[nms.ordinal()];
   }

   public static EnumItemSlot getNMS(EquipmentSlot slot) {
      return slots[slot.ordinal()];
   }

   public static EquipmentSlot getHand(EnumHand enumhand) {
      return enumhand == EnumHand.a ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
   }

   public static EnumHand getHand(EquipmentSlot hand) {
      if (hand == EquipmentSlot.HAND) {
         return EnumHand.a;
      } else if (hand == EquipmentSlot.OFF_HAND) {
         return EnumHand.b;
      } else {
         throw new IllegalArgumentException("EquipmentSlot." + hand + " is not a hand");
      }
   }
}
