package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSculkShrieker extends CraftBlockData implements SculkShrieker, Waterlogged {
   private static final BlockStateBoolean CAN_SUMMON = getBoolean(SculkShriekerBlock.class, "can_summon");
   private static final BlockStateBoolean SHRIEKING = getBoolean(SculkShriekerBlock.class, "shrieking");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(SculkShriekerBlock.class, "waterlogged");

   public CraftSculkShrieker() {
   }

   public CraftSculkShrieker(IBlockData state) {
      super(state);
   }

   public boolean isCanSummon() {
      return this.get(CAN_SUMMON);
   }

   public void setCanSummon(boolean can_summon) {
      this.set(CAN_SUMMON, Boolean.valueOf(can_summon));
   }

   public boolean isShrieking() {
      return this.get(SHRIEKING);
   }

   public void setShrieking(boolean shrieking) {
      this.set(SHRIEKING, Boolean.valueOf(shrieking));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
