package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class PacketPlayOutMount implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int[] b;

   public PacketPlayOutMount(Entity var0) {
      this.a = var0.af();
      List<Entity> var1 = var0.cM();
      this.b = new int[var1.size()];

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         this.b[var2] = var1.get(var2).af();
      }
   }

   public PacketPlayOutMount(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.c();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int[] a() {
      return this.b;
   }

   public int c() {
      return this.a;
   }
}
