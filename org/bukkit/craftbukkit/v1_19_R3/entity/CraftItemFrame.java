package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.decoration.EntityHanging;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang.Validate;
import org.bukkit.Rotation;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

public class CraftItemFrame extends CraftHanging implements ItemFrame {
   public CraftItemFrame(CraftServer server, EntityItemFrame entity) {
      super(server, entity);
   }

   @Override
   public boolean setFacingDirection(BlockFace face, boolean force) {
      EntityHanging hanging = this.getHandle();
      EnumDirection oldDir = hanging.cA();
      EnumDirection newDir = CraftBlock.blockFaceToNotch(face);
      Preconditions.checkArgument(newDir != null, "%s is not a valid facing direction", face);
      this.getHandle().a(newDir);
      if (!force && !this.getHandle().generation && !hanging.s()) {
         hanging.a(oldDir);
         return false;
      } else {
         this.update();
         return true;
      }
   }

   @Override
   protected void update() {
      super.update();
      this.getHandle().aj().markDirty(EntityItemFrame.g);
      this.getHandle().aj().markDirty(EntityItemFrame.h);
      if (!this.getHandle().generation) {
         this.getHandle().Y().c(this.getHandle().c, Blocks.a);
      }
   }

   public void setItem(ItemStack item) {
      this.setItem(item, true);
   }

   public void setItem(ItemStack item, boolean playSound) {
      this.getHandle().setItem(CraftItemStack.asNMSCopy(item), !this.getHandle().generation, !this.getHandle().generation && playSound);
   }

   public ItemStack getItem() {
      return CraftItemStack.asBukkitCopy(this.getHandle().y());
   }

   public float getItemDropChance() {
      return this.getHandle().i;
   }

   public void setItemDropChance(float chance) {
      Preconditions.checkArgument(0.0 <= (double)chance && (double)chance <= 1.0, "Chance outside range [0, 1]");
      this.getHandle().i = chance;
   }

   public Rotation getRotation() {
      return this.toBukkitRotation(this.getHandle().C());
   }

   Rotation toBukkitRotation(int value) {
      switch(value) {
         case 0:
            return Rotation.NONE;
         case 1:
            return Rotation.CLOCKWISE_45;
         case 2:
            return Rotation.CLOCKWISE;
         case 3:
            return Rotation.CLOCKWISE_135;
         case 4:
            return Rotation.FLIPPED;
         case 5:
            return Rotation.FLIPPED_45;
         case 6:
            return Rotation.COUNTER_CLOCKWISE;
         case 7:
            return Rotation.COUNTER_CLOCKWISE_45;
         default:
            throw new AssertionError("Unknown rotation " + value + " for " + this.getHandle());
      }
   }

   public void setRotation(Rotation rotation) {
      Validate.notNull(rotation, "Rotation cannot be null");
      this.getHandle().b(toInteger(rotation));
   }

   static int toInteger(Rotation rotation) {
      switch(rotation) {
         case NONE:
            return 0;
         case CLOCKWISE_45:
            return 1;
         case CLOCKWISE:
            return 2;
         case CLOCKWISE_135:
            return 3;
         case FLIPPED:
            return 4;
         case FLIPPED_45:
            return 5;
         case COUNTER_CLOCKWISE:
            return 6;
         case COUNTER_CLOCKWISE_45:
            return 7;
         default:
            throw new IllegalArgumentException(rotation + " is not applicable to an ItemFrame");
      }
   }

   public boolean isVisible() {
      return !this.getHandle().ca();
   }

   public void setVisible(boolean visible) {
      this.getHandle().j(!visible);
   }

   public boolean isFixed() {
      return this.getHandle().j;
   }

   public void setFixed(boolean fixed) {
      this.getHandle().j = fixed;
   }

   public EntityItemFrame getHandle() {
      return (EntityItemFrame)this.entity;
   }

   @Override
   public String toString() {
      return "CraftItemFrame{item=" + this.getItem() + ", rotation=" + this.getRotation() + "}";
   }

   @Override
   public EntityType getType() {
      return EntityType.ITEM_FRAME;
   }
}
