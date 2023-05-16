package net.minecraft.world.level.block;

import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.state.BlockBase;

public class BlockStainedGlass extends BlockGlassAbstract implements IBeaconBeam {
   private final EnumColor a;

   public BlockStainedGlass(EnumColor var0, BlockBase.Info var1) {
      super(var1);
      this.a = var0;
   }

   @Override
   public EnumColor a() {
      return this.a;
   }
}
