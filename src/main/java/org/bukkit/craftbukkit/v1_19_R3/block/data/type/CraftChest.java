package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftChest extends CraftBlockData implements Chest {
   private static final BlockStateEnum<?> TYPE = getEnum("type");

   public Type getType() {
      return this.get(TYPE, Type.class);
   }

   public void setType(Type type) {
      this.set(TYPE, type);
   }
}
