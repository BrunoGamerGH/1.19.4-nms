package net.minecraft.world.level.block;

import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.state.BlockBase;

public class BlockCarpet extends CarpetBlock {
   private final EnumColor b;

   protected BlockCarpet(EnumColor var0, BlockBase.Info var1) {
      super(var1);
      this.b = var0;
   }

   public EnumColor b() {
      return this.b;
   }
}
