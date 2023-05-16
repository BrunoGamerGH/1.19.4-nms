package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.server.level.EntityPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CraftInventoryPlayer extends CraftInventory implements PlayerInventory, EntityEquipment {
   public CraftInventoryPlayer(net.minecraft.world.entity.player.PlayerInventory inventory) {
      super(inventory);
   }

   public net.minecraft.world.entity.player.PlayerInventory getInventory() {
      return (net.minecraft.world.entity.player.PlayerInventory)this.inventory;
   }

   @Override
   public ItemStack[] getStorageContents() {
      return this.asCraftMirror(this.getInventory().i);
   }

   public ItemStack getItemInMainHand() {
      return CraftItemStack.asCraftMirror(this.getInventory().f());
   }

   public void setItemInMainHand(ItemStack item) {
      this.setItem(this.getHeldItemSlot(), item);
   }

   public void setItemInMainHand(ItemStack item, boolean silent) {
      this.setItemInMainHand(item);
   }

   public ItemStack getItemInOffHand() {
      return CraftItemStack.asCraftMirror(this.getInventory().k.get(0));
   }

   public void setItemInOffHand(ItemStack item) {
      ItemStack[] extra = this.getExtraContents();
      extra[0] = item;
      this.setExtraContents(extra);
   }

   public void setItemInOffHand(ItemStack item, boolean silent) {
      this.setItemInOffHand(item);
   }

   public ItemStack getItemInHand() {
      return this.getItemInMainHand();
   }

   public void setItemInHand(ItemStack stack) {
      this.setItemInMainHand(stack);
   }

   @Override
   public void setItem(int index, ItemStack item) {
      super.setItem(index, item);
      if (this.getHolder() != null) {
         EntityPlayer player = ((CraftPlayer)this.getHolder()).getHandle();
         if (player.b != null) {
            if (index < net.minecraft.world.entity.player.PlayerInventory.g()) {
               index += 36;
            } else if (index > 39) {
               index += 5;
            } else if (index > 35) {
               index = 8 - (index - 36);
            }

            player.b.a(new PacketPlayOutSetSlot(player.bO.j, player.bO.k(), index, CraftItemStack.asNMSCopy(item)));
         }
      }
   }

   public void setItem(EquipmentSlot slot, ItemStack item) {
      Preconditions.checkArgument(slot != null, "slot must not be null");
      switch(slot) {
         case HAND:
            this.setItemInMainHand(item);
            break;
         case OFF_HAND:
            this.setItemInOffHand(item);
            break;
         case FEET:
            this.setBoots(item);
            break;
         case LEGS:
            this.setLeggings(item);
            break;
         case CHEST:
            this.setChestplate(item);
            break;
         case HEAD:
            this.setHelmet(item);
            break;
         default:
            throw new IllegalArgumentException("Not implemented. This is a bug");
      }
   }

   public void setItem(EquipmentSlot slot, ItemStack item, boolean silent) {
      this.setItem(slot, item);
   }

   public ItemStack getItem(EquipmentSlot slot) {
      Preconditions.checkArgument(slot != null, "slot must not be null");
      switch(slot) {
         case HAND:
            return this.getItemInMainHand();
         case OFF_HAND:
            return this.getItemInOffHand();
         case FEET:
            return this.getBoots();
         case LEGS:
            return this.getLeggings();
         case CHEST:
            return this.getChestplate();
         case HEAD:
            return this.getHelmet();
         default:
            throw new IllegalArgumentException("Not implemented. This is a bug");
      }
   }

   public int getHeldItemSlot() {
      return this.getInventory().l;
   }

   public void setHeldItemSlot(int slot) {
      Validate.isTrue(slot >= 0 && slot < net.minecraft.world.entity.player.PlayerInventory.g(), "Slot is not between 0 and 8 inclusive");
      this.getInventory().l = slot;
      ((CraftPlayer)this.getHolder()).getHandle().b.a(new PacketPlayOutHeldItemSlot(slot));
   }

   public ItemStack getHelmet() {
      return this.getItem(this.getSize() - 2);
   }

   public ItemStack getChestplate() {
      return this.getItem(this.getSize() - 3);
   }

   public ItemStack getLeggings() {
      return this.getItem(this.getSize() - 4);
   }

   public ItemStack getBoots() {
      return this.getItem(this.getSize() - 5);
   }

   public void setHelmet(ItemStack helmet) {
      this.setItem(this.getSize() - 2, helmet);
   }

   public void setHelmet(ItemStack helmet, boolean silent) {
      this.setHelmet(helmet);
   }

   public void setChestplate(ItemStack chestplate) {
      this.setItem(this.getSize() - 3, chestplate);
   }

   public void setChestplate(ItemStack chestplate, boolean silent) {
      this.setChestplate(chestplate);
   }

   public void setLeggings(ItemStack leggings) {
      this.setItem(this.getSize() - 4, leggings);
   }

   public void setLeggings(ItemStack leggings, boolean silent) {
      this.setLeggings(leggings);
   }

   public void setBoots(ItemStack boots) {
      this.setItem(this.getSize() - 5, boots);
   }

   public void setBoots(ItemStack boots, boolean silent) {
      this.setBoots(boots);
   }

   public ItemStack[] getArmorContents() {
      return this.asCraftMirror(this.getInventory().j);
   }

   private void setSlots(ItemStack[] items, int baseSlot, int length) {
      if (items == null) {
         items = new ItemStack[length];
      }

      Preconditions.checkArgument(items.length <= length, "items.length must be < %s", length);

      for(int i = 0; i < length; ++i) {
         if (i >= items.length) {
            this.setItem(baseSlot + i, null);
         } else {
            this.setItem(baseSlot + i, items[i]);
         }
      }
   }

   @Override
   public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
      this.setSlots(items, 0, this.getInventory().i.size());
   }

   public void setArmorContents(ItemStack[] items) {
      this.setSlots(items, this.getInventory().i.size(), this.getInventory().j.size());
   }

   public ItemStack[] getExtraContents() {
      return this.asCraftMirror(this.getInventory().k);
   }

   public void setExtraContents(ItemStack[] items) {
      this.setSlots(items, this.getInventory().i.size() + this.getInventory().j.size(), this.getInventory().k.size());
   }

   public HumanEntity getHolder() {
      return (HumanEntity)this.inventory.getOwner();
   }

   public float getItemInHandDropChance() {
      return this.getItemInMainHandDropChance();
   }

   public void setItemInHandDropChance(float chance) {
      this.setItemInMainHandDropChance(chance);
   }

   public float getItemInMainHandDropChance() {
      return 1.0F;
   }

   public void setItemInMainHandDropChance(float chance) {
      throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
   }

   public float getItemInOffHandDropChance() {
      return 1.0F;
   }

   public void setItemInOffHandDropChance(float chance) {
      throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
   }

   public float getHelmetDropChance() {
      return 1.0F;
   }

   public void setHelmetDropChance(float chance) {
      throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
   }

   public float getChestplateDropChance() {
      return 1.0F;
   }

   public void setChestplateDropChance(float chance) {
      throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
   }

   public float getLeggingsDropChance() {
      return 1.0F;
   }

   public void setLeggingsDropChance(float chance) {
      throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
   }

   public float getBootsDropChance() {
      return 1.0F;
   }

   public void setBootsDropChance(float chance) {
      throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
   }
}
