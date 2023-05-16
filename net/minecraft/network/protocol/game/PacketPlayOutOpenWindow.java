package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.Containers;

public class PacketPlayOutOpenWindow implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final Containers<?> b;
   private final IChatBaseComponent c;

   public PacketPlayOutOpenWindow(int var0, Containers<?> var1, IChatBaseComponent var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayOutOpenWindow(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.a(BuiltInRegistries.r);
      this.c = var0.l();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(BuiltInRegistries.r, this.b);
      var0.a(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   @Nullable
   public Containers<?> c() {
      return this.b;
   }

   public IChatBaseComponent d() {
      return this.c;
   }
}
