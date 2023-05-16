package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

public class CraftEnderman extends CraftMonster implements Enderman {
   public CraftEnderman(CraftServer server, EntityEnderman entity) {
      super(server, entity);
   }

   public MaterialData getCarriedMaterial() {
      IBlockData blockData = this.getHandle().fS();
      return blockData == null ? Material.AIR.getNewData((byte)0) : CraftMagicNumbers.getMaterial(blockData);
   }

   public BlockData getCarriedBlock() {
      IBlockData blockData = this.getHandle().fS();
      return blockData == null ? null : CraftBlockData.fromData(blockData);
   }

   public void setCarriedMaterial(MaterialData data) {
      this.getHandle().c(CraftMagicNumbers.getBlock(data));
   }

   public void setCarriedBlock(BlockData blockData) {
      this.getHandle().c(blockData == null ? null : ((CraftBlockData)blockData).getState());
   }

   public EntityEnderman getHandle() {
      return (EntityEnderman)this.entity;
   }

   @Override
   public String toString() {
      return "CraftEnderman";
   }

   @Override
   public EntityType getType() {
      return EntityType.ENDERMAN;
   }
}
