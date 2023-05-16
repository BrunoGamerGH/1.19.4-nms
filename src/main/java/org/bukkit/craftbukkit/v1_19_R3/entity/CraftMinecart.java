package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Minecart;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public abstract class CraftMinecart extends CraftVehicle implements Minecart {
   public CraftMinecart(CraftServer server, EntityMinecartAbstract entity) {
      super(server, entity);
   }

   public void setDamage(double damage) {
      this.getHandle().a((float)damage);
   }

   public double getDamage() {
      return (double)this.getHandle().p();
   }

   public double getMaxSpeed() {
      return this.getHandle().maxSpeed;
   }

   public void setMaxSpeed(double speed) {
      if (speed >= 0.0) {
         this.getHandle().maxSpeed = speed;
      }
   }

   public boolean isSlowWhenEmpty() {
      return this.getHandle().slowWhenEmpty;
   }

   public void setSlowWhenEmpty(boolean slow) {
      this.getHandle().slowWhenEmpty = slow;
   }

   public Vector getFlyingVelocityMod() {
      return this.getHandle().getFlyingVelocityMod();
   }

   public void setFlyingVelocityMod(Vector flying) {
      this.getHandle().setFlyingVelocityMod(flying);
   }

   public Vector getDerailedVelocityMod() {
      return this.getHandle().getDerailedVelocityMod();
   }

   public void setDerailedVelocityMod(Vector derailed) {
      this.getHandle().setDerailedVelocityMod(derailed);
   }

   public EntityMinecartAbstract getHandle() {
      return (EntityMinecartAbstract)this.entity;
   }

   public void setDisplayBlock(MaterialData material) {
      if (material != null) {
         IBlockData block = CraftMagicNumbers.getBlock(material);
         this.getHandle().b(block);
      } else {
         this.getHandle().b(Blocks.a.o());
         this.getHandle().a(false);
      }
   }

   public void setDisplayBlockData(BlockData blockData) {
      if (blockData != null) {
         IBlockData block = ((CraftBlockData)blockData).getState();
         this.getHandle().b(block);
      } else {
         this.getHandle().b(Blocks.a.o());
         this.getHandle().a(false);
      }
   }

   public MaterialData getDisplayBlock() {
      IBlockData blockData = this.getHandle().t();
      return CraftMagicNumbers.getMaterial(blockData);
   }

   public BlockData getDisplayBlockData() {
      IBlockData blockData = this.getHandle().t();
      return CraftBlockData.fromData(blockData);
   }

   public void setDisplayBlockOffset(int offset) {
      this.getHandle().l(offset);
   }

   public int getDisplayBlockOffset() {
      return this.getHandle().w();
   }
}
