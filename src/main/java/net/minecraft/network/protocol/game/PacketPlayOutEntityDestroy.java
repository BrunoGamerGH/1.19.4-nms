package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutEntityDestroy implements Packet<PacketListenerPlayOut> {
   private final IntList a;

   public PacketPlayOutEntityDestroy(IntList var0) {
      this.a = new IntArrayList(var0);
   }

   public PacketPlayOutEntityDestroy(int... var0) {
      this.a = new IntArrayList(var0);
   }

   public PacketPlayOutEntityDestroy(PacketDataSerializer var0) {
      this.a = var0.a();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public IntList a() {
      return this.a;
   }
}
