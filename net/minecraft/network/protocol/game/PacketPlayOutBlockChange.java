package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class PacketPlayOutBlockChange implements Packet<PacketListenerPlayOut> {
   private final BlockPosition a;
   public final IBlockData b;

   public PacketPlayOutBlockChange(BlockPosition var0, IBlockData var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutBlockChange(IBlockAccess var0, BlockPosition var1) {
      this(var1, var0.a_(var1));
   }

   public PacketPlayOutBlockChange(PacketDataSerializer var0) {
      this.a = var0.f();
      this.b = var0.a(Block.o);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(Block.o, this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public IBlockData a() {
      return this.b;
   }

   public BlockPosition c() {
      return this.a;
   }
}
