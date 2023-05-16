package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.entity.TileEntityCommand;

public class PacketPlayInSetCommandBlock implements Packet<PacketListenerPlayIn> {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 4;
   private final BlockPosition d;
   private final String e;
   private final boolean f;
   private final boolean g;
   private final boolean h;
   private final TileEntityCommand.Type i;

   public PacketPlayInSetCommandBlock(BlockPosition var0, String var1, TileEntityCommand.Type var2, boolean var3, boolean var4, boolean var5) {
      this.d = var0;
      this.e = var1;
      this.f = var3;
      this.g = var4;
      this.h = var5;
      this.i = var2;
   }

   public PacketPlayInSetCommandBlock(PacketDataSerializer var0) {
      this.d = var0.f();
      this.e = var0.s();
      this.i = var0.b(TileEntityCommand.Type.class);
      int var1 = var0.readByte();
      this.f = (var1 & 1) != 0;
      this.g = (var1 & 2) != 0;
      this.h = (var1 & 4) != 0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.d);
      var0.a(this.e);
      var0.a(this.i);
      int var1 = 0;
      if (this.f) {
         var1 |= 1;
      }

      if (this.g) {
         var1 |= 2;
      }

      if (this.h) {
         var1 |= 4;
      }

      var0.writeByte(var1);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.d;
   }

   public String c() {
      return this.e;
   }

   public boolean d() {
      return this.f;
   }

   public boolean e() {
      return this.g;
   }

   public boolean f() {
      return this.h;
   }

   public TileEntityCommand.Type g() {
      return this.i;
   }
}
