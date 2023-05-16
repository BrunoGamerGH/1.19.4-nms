package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.BlockBrewingStand;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBrewingStand extends CraftBlockData implements BrewingStand {
   private static final BlockStateBoolean[] HAS_BOTTLE = new BlockStateBoolean[]{
      getBoolean(BlockBrewingStand.class, "has_bottle_0"),
      getBoolean(BlockBrewingStand.class, "has_bottle_1"),
      getBoolean(BlockBrewingStand.class, "has_bottle_2")
   };

   public CraftBrewingStand() {
   }

   public CraftBrewingStand(IBlockData state) {
      super(state);
   }

   public boolean hasBottle(int bottle) {
      return this.get(HAS_BOTTLE[bottle]);
   }

   public void setBottle(int bottle, boolean has) {
      this.set(HAS_BOTTLE[bottle], Boolean.valueOf(has));
   }

   public Set<Integer> getBottles() {
      Builder<Integer> bottles = ImmutableSet.builder();

      for(int index = 0; index < this.getMaximumBottles(); ++index) {
         if (this.hasBottle(index)) {
            bottles.add(index);
         }
      }

      return bottles.build();
   }

   public int getMaximumBottles() {
      return HAS_BOTTLE.length;
   }
}
