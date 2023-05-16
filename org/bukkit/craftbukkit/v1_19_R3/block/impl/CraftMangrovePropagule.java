package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Hangable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.MangrovePropagule;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftMangrovePropagule extends CraftBlockData implements MangrovePropagule, Ageable, Hangable, Sapling, Waterlogged {
   private static final BlockStateInteger AGE = getInteger(MangrovePropaguleBlock.class, "age");
   private static final BlockStateBoolean HANGING = getBoolean(MangrovePropaguleBlock.class, "hanging");
   private static final BlockStateInteger STAGE = getInteger(MangrovePropaguleBlock.class, "stage");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(MangrovePropaguleBlock.class, "waterlogged");

   public CraftMangrovePropagule() {
   }

   public CraftMangrovePropagule(IBlockData state) {
      super(state);
   }

   public int getAge() {
      return this.get(AGE);
   }

   public void setAge(int age) {
      this.set(AGE, Integer.valueOf(age));
   }

   public int getMaximumAge() {
      return getMax(AGE);
   }

   public boolean isHanging() {
      return this.get(HANGING);
   }

   public void setHanging(boolean hanging) {
      this.set(HANGING, Boolean.valueOf(hanging));
   }

   public int getStage() {
      return this.get(STAGE);
   }

   public void setStage(int stage) {
      this.set(STAGE, Integer.valueOf(stage));
   }

   public int getMaximumStage() {
      return getMax(STAGE);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
