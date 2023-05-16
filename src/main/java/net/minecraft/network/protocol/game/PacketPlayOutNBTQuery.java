package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutNBTQuery implements Packet<PacketListenerPlayOut> {
   private final int a;
   @Nullable
   private final NBTTagCompound b;

   public PacketPlayOutNBTQuery(int var0, @Nullable NBTTagCompound var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutNBTQuery(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.p();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   @Nullable
   public NBTTagCompound c() {
      return this.b;
   }

   @Override
   public boolean b() {
      return true;
   }
}
