package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class PacketPlayInEntityAction implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final PacketPlayInEntityAction.EnumPlayerAction b;
   private final int c;

   public PacketPlayInEntityAction(Entity var0, PacketPlayInEntityAction.EnumPlayerAction var1) {
      this(var0, var1, 0);
   }

   public PacketPlayInEntityAction(Entity var0, PacketPlayInEntityAction.EnumPlayerAction var1, int var2) {
      this.a = var0.af();
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayInEntityAction(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.b(PacketPlayInEntityAction.EnumPlayerAction.class);
      this.c = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
      var0.d(this.c);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public PacketPlayInEntityAction.EnumPlayerAction c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public static enum EnumPlayerAction {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h,
      i;
   }
}
