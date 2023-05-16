package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.item.EntityFallingBlock;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;

public class CraftFallingBlock extends CraftEntity implements FallingBlock {
   public CraftFallingBlock(CraftServer server, EntityFallingBlock entity) {
      super(server, entity);
   }

   public EntityFallingBlock getHandle() {
      return (EntityFallingBlock)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFallingBlock";
   }

   public EntityType getType() {
      return EntityType.FALLING_BLOCK;
   }

   public Material getMaterial() {
      return this.getBlockData().getMaterial();
   }

   public BlockData getBlockData() {
      return CraftBlockData.fromData(this.getHandle().k());
   }

   public boolean getDropItem() {
      return this.getHandle().c;
   }

   public void setDropItem(boolean drop) {
      this.getHandle().c = drop;
   }

   public boolean canHurtEntities() {
      return this.getHandle().i;
   }

   public void setHurtEntities(boolean hurtEntities) {
      this.getHandle().i = hurtEntities;
   }

   @Override
   public void setTicksLived(int value) {
      super.setTicksLived(value);
      this.getHandle().b = value;
   }
}
