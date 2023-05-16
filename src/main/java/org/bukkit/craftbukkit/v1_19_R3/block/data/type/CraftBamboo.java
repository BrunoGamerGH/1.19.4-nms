package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Bamboo.Leaves;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftBamboo extends CraftBlockData implements Bamboo {
   private static final BlockStateEnum<?> LEAVES = getEnum("leaves");

   public Leaves getLeaves() {
      return this.get(LEAVES, Leaves.class);
   }

   public void setLeaves(Leaves leaves) {
      this.set(LEAVES, leaves);
   }
}
