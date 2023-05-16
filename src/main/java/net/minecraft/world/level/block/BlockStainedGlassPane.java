package net.minecraft.world.level.block;

import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.state.BlockBase;

public class BlockStainedGlassPane extends BlockIronBars implements IBeaconBeam {
   private final EnumColor i;

   public BlockStainedGlassPane(EnumColor var0, BlockBase.Info var1) {
      super(var1);
      this.i = var0;
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
   }

   @Override
   public EnumColor a() {
      return this.i;
   }
}
