package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.Block;

public class PacketPlayOutBlockAction implements Packet<PacketListenerPlayOut> {
   private final BlockPosition a;
   private final int b;
   private final int c;
   private final Block d;

   public PacketPlayOutBlockAction(BlockPosition var0, Block var1, int var2, int var3) {
      this.a = var0;
      this.d = var1;
      this.b = var2;
      this.c = var3;
   }

   public PacketPlayOutBlockAction(PacketDataSerializer var0) {
      this.a = var0.f();
      this.b = var0.readUnsignedByte();
      this.c = var0.readUnsignedByte();
      this.d = var0.a(BuiltInRegistries.f);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.writeByte(this.b);
      var0.writeByte(this.c);
      var0.a(BuiltInRegistries.f, this.d);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public Block e() {
      return this.d;
   }
}
