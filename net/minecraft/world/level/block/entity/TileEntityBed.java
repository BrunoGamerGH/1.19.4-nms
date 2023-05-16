package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityBed extends TileEntity {
   public EnumColor a;

   public TileEntityBed(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.y, var0, var1);
      this.a = ((BlockBed)var1.b()).b();
   }

   public TileEntityBed(BlockPosition var0, IBlockData var1, EnumColor var2) {
      super(TileEntityTypes.y, var0, var1);
      this.a = var2;
   }

   public PacketPlayOutTileEntityData c() {
      return PacketPlayOutTileEntityData.a(this);
   }

   public EnumColor d() {
      return this.a;
   }

   public void a(EnumColor var0) {
      this.a = var0;
   }
}
