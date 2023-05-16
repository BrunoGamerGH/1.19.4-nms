package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CraftEntityEquipment implements EntityEquipment {
   private final CraftLivingEntity entity;

   public CraftEntityEquipment(CraftLivingEntity entity) {
      this.entity = entity;
   }

   public void setItem(EquipmentSlot slot, ItemStack item) {
      this.setItem(slot, item, false);
   }

   public void setItem(EquipmentSlot slot, ItemStack item, boolean silent) {
      Preconditions.checkArgument(slot != null, "slot must not be null");
      EnumItemSlot nmsSlot = CraftEquipmentSlot.getNMS(slot);
      this.setEquipment(nmsSlot, item, silent);
   }

   public ItemStack getItem(EquipmentSlot slot) {
      Preconditions.checkArgument(slot != null, "slot must not be null");
      EnumItemSlot nmsSlot = CraftEquipmentSlot.getNMS(slot);
      return this.getEquipment(nmsSlot);
   }

   public ItemStack getItemInMainHand() {
      return this.getEquipment(EnumItemSlot.a);
   }

   public void setItemInMainHand(ItemStack item) {
      this.setItemInMainHand(item, false);
   }

   public void setItemInMainHand(ItemStack item, boolean silent) {
      this.setEquipment(EnumItemSlot.a, item, silent);
   }

   public ItemStack getItemInOffHand() {
      return this.getEquipment(EnumItemSlot.b);
   }

   public void setItemInOffHand(ItemStack item) {
      this.setItemInOffHand(item, false);
   }

   public void setItemInOffHand(ItemStack item, boolean silent) {
      this.setEquipment(EnumItemSlot.b, item, silent);
   }

   public ItemStack getItemInHand() {
      return this.getItemInMainHand();
   }

   public void setItemInHand(ItemStack stack) {
      this.setItemInMainHand(stack);
   }

   public ItemStack getHelmet() {
      return this.getEquipment(EnumItemSlot.f);
   }

   public void setHelmet(ItemStack helmet) {
      this.setHelmet(helmet, false);
   }

   public void setHelmet(ItemStack helmet, boolean silent) {
      this.setEquipment(EnumItemSlot.f, helmet, silent);
   }

   public ItemStack getChestplate() {
      return this.getEquipment(EnumItemSlot.e);
   }

   public void setChestplate(ItemStack chestplate) {
      this.setChestplate(chestplate, false);
   }

   public void setChestplate(ItemStack chestplate, boolean silent) {
      this.setEquipment(EnumItemSlot.e, chestplate, silent);
   }

   public ItemStack getLeggings() {
      return this.getEquipment(EnumItemSlot.d);
   }

   public void setLeggings(ItemStack leggings) {
      this.setLeggings(leggings, false);
   }

   public void setLeggings(ItemStack leggings, boolean silent) {
      this.setEquipment(EnumItemSlot.d, leggings, silent);
   }

   public ItemStack getBoots() {
      return this.getEquipment(EnumItemSlot.c);
   }

   public void setBoots(ItemStack boots) {
      this.setBoots(boots, false);
   }

   public void setBoots(ItemStack boots, boolean silent) {
      this.setEquipment(EnumItemSlot.c, boots, silent);
   }

   public ItemStack[] getArmorContents() {
      return new ItemStack[]{
         this.getEquipment(EnumItemSlot.c), this.getEquipment(EnumItemSlot.d), this.getEquipment(EnumItemSlot.e), this.getEquipment(EnumItemSlot.f)
      };
   }

   public void setArmorContents(ItemStack[] items) {
      this.setEquipment(EnumItemSlot.c, items.length >= 1 ? items[0] : null, false);
      this.setEquipment(EnumItemSlot.d, items.length >= 2 ? items[1] : null, false);
      this.setEquipment(EnumItemSlot.e, items.length >= 3 ? items[2] : null, false);
      this.setEquipment(EnumItemSlot.f, items.length >= 4 ? items[3] : null, false);
   }

   private ItemStack getEquipment(EnumItemSlot slot) {
      return CraftItemStack.asBukkitCopy(this.entity.getHandle().c(slot));
   }

   private void setEquipment(EnumItemSlot slot, ItemStack stack, boolean silent) {
      this.entity.getHandle().setItemSlot(slot, CraftItemStack.asNMSCopy(stack), silent);
   }

   public void clear() {
      EnumItemSlot[] var4;
      for(EnumItemSlot slot : var4 = EnumItemSlot.values()) {
         this.setEquipment(slot, null, false);
      }
   }

   public Entity getHolder() {
      return this.entity;
   }

   public float getItemInHandDropChance() {
      return this.getItemInMainHandDropChance();
   }

   public void setItemInHandDropChance(float chance) {
      this.setItemInMainHandDropChance(chance);
   }

   public float getItemInMainHandDropChance() {
      return this.getDropChance(EnumItemSlot.a);
   }

   public void setItemInMainHandDropChance(float chance) {
      this.setDropChance(EnumItemSlot.a, chance);
   }

   public float getItemInOffHandDropChance() {
      return this.getDropChance(EnumItemSlot.b);
   }

   public void setItemInOffHandDropChance(float chance) {
      this.setDropChance(EnumItemSlot.b, chance);
   }

   public float getHelmetDropChance() {
      return this.getDropChance(EnumItemSlot.f);
   }

   public void setHelmetDropChance(float chance) {
      this.setDropChance(EnumItemSlot.f, chance);
   }

   public float getChestplateDropChance() {
      return this.getDropChance(EnumItemSlot.e);
   }

   public void setChestplateDropChance(float chance) {
      this.setDropChance(EnumItemSlot.e, chance);
   }

   public float getLeggingsDropChance() {
      return this.getDropChance(EnumItemSlot.d);
   }

   public void setLeggingsDropChance(float chance) {
      this.setDropChance(EnumItemSlot.d, chance);
   }

   public float getBootsDropChance() {
      return this.getDropChance(EnumItemSlot.c);
   }

   public void setBootsDropChance(float chance) {
      this.setDropChance(EnumItemSlot.c, chance);
   }

   private void setDropChance(EnumItemSlot slot, float chance) {
      Preconditions.checkArgument(this.entity.getHandle() instanceof EntityInsentient, "Cannot set drop chance for non-Mob entity");
      if (slot != EnumItemSlot.a && slot != EnumItemSlot.b) {
         ((EntityInsentient)this.entity.getHandle()).bQ[slot.b()] = chance;
      } else {
         ((EntityInsentient)this.entity.getHandle()).bP[slot.b()] = chance;
      }
   }

   private float getDropChance(EnumItemSlot slot) {
      if (!(this.entity.getHandle() instanceof EntityInsentient)) {
         return 1.0F;
      } else {
         return slot != EnumItemSlot.a && slot != EnumItemSlot.b
            ? ((EntityInsentient)this.entity.getHandle()).bQ[slot.b()]
            : ((EntityInsentient)this.entity.getHandle()).bP[slot.b()];
      }
   }
}
