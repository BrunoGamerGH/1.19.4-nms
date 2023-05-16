package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.core.Vector3f;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class CraftArmorStand extends CraftLivingEntity implements ArmorStand {
   public CraftArmorStand(CraftServer server, EntityArmorStand entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftArmorStand";
   }

   @Override
   public EntityType getType() {
      return EntityType.ARMOR_STAND;
   }

   public EntityArmorStand getHandle() {
      return (EntityArmorStand)super.getHandle();
   }

   public ItemStack getItemInHand() {
      return this.getEquipment().getItemInHand();
   }

   public void setItemInHand(ItemStack item) {
      this.getEquipment().setItemInHand(item);
   }

   public ItemStack getBoots() {
      return this.getEquipment().getBoots();
   }

   public void setBoots(ItemStack item) {
      this.getEquipment().setBoots(item);
   }

   public ItemStack getLeggings() {
      return this.getEquipment().getLeggings();
   }

   public void setLeggings(ItemStack item) {
      this.getEquipment().setLeggings(item);
   }

   public ItemStack getChestplate() {
      return this.getEquipment().getChestplate();
   }

   public void setChestplate(ItemStack item) {
      this.getEquipment().setChestplate(item);
   }

   public ItemStack getHelmet() {
      return this.getEquipment().getHelmet();
   }

   public void setHelmet(ItemStack item) {
      this.getEquipment().setHelmet(item);
   }

   public EulerAngle getBodyPose() {
      return fromNMS(this.getHandle().cc);
   }

   public void setBodyPose(EulerAngle pose) {
      this.getHandle().b(toNMS(pose));
   }

   public EulerAngle getLeftArmPose() {
      return fromNMS(this.getHandle().cd);
   }

   public void setLeftArmPose(EulerAngle pose) {
      this.getHandle().c(toNMS(pose));
   }

   public EulerAngle getRightArmPose() {
      return fromNMS(this.getHandle().ce);
   }

   public void setRightArmPose(EulerAngle pose) {
      this.getHandle().d(toNMS(pose));
   }

   public EulerAngle getLeftLegPose() {
      return fromNMS(this.getHandle().cf);
   }

   public void setLeftLegPose(EulerAngle pose) {
      this.getHandle().e(toNMS(pose));
   }

   public EulerAngle getRightLegPose() {
      return fromNMS(this.getHandle().cg);
   }

   public void setRightLegPose(EulerAngle pose) {
      this.getHandle().f(toNMS(pose));
   }

   public EulerAngle getHeadPose() {
      return fromNMS(this.getHandle().cb);
   }

   public void setHeadPose(EulerAngle pose) {
      this.getHandle().a(toNMS(pose));
   }

   public boolean hasBasePlate() {
      return !this.getHandle().s();
   }

   public void setBasePlate(boolean basePlate) {
      this.getHandle().s(!basePlate);
   }

   @Override
   public void setGravity(boolean gravity) {
      super.setGravity(gravity);
      this.getHandle().ae = !gravity;
   }

   public boolean isVisible() {
      return !this.getHandle().ca();
   }

   public void setVisible(boolean visible) {
      this.getHandle().j(!visible);
   }

   public boolean hasArms() {
      return this.getHandle().r();
   }

   public void setArms(boolean arms) {
      this.getHandle().a(arms);
   }

   public boolean isSmall() {
      return this.getHandle().q();
   }

   public void setSmall(boolean small) {
      this.getHandle().t(small);
   }

   private static EulerAngle fromNMS(Vector3f old) {
      return new EulerAngle(Math.toRadians((double)old.b()), Math.toRadians((double)old.c()), Math.toRadians((double)old.d()));
   }

   private static Vector3f toNMS(EulerAngle old) {
      return new Vector3f((float)Math.toDegrees(old.getX()), (float)Math.toDegrees(old.getY()), (float)Math.toDegrees(old.getZ()));
   }

   public boolean isMarker() {
      return this.getHandle().w();
   }

   public void setMarker(boolean marker) {
      this.getHandle().u(marker);
   }

   public void addEquipmentLock(EquipmentSlot equipmentSlot, LockType lockType) {
      EntityArmorStand var10000 = this.getHandle();
      var10000.ca |= 1 << CraftEquipmentSlot.getNMS(equipmentSlot).c() + lockType.ordinal() * 8;
   }

   public void removeEquipmentLock(EquipmentSlot equipmentSlot, LockType lockType) {
      EntityArmorStand var10000 = this.getHandle();
      var10000.ca &= ~(1 << CraftEquipmentSlot.getNMS(equipmentSlot).c() + lockType.ordinal() * 8);
   }

   public boolean hasEquipmentLock(EquipmentSlot equipmentSlot, LockType lockType) {
      return (this.getHandle().ca & 1 << CraftEquipmentSlot.getNMS(equipmentSlot).c() + lockType.ordinal() * 8) != 0;
   }
}
