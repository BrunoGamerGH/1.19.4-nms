package net.minecraft.network.protocol.game;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.DataWatcher;

public record PacketPlayOutEntityMetadata(int id, List<DataWatcher.b<?>> packedItems) implements Packet<PacketListenerPlayOut> {
   private final int b;
   private final List<DataWatcher.b<?>> c;
   public static final int a = 255;

   public PacketPlayOutEntityMetadata(PacketDataSerializer var0) {
      this(var0.m(), b(var0));
   }

   public PacketPlayOutEntityMetadata(int var0, List<DataWatcher.b<?>> var1) {
      this.b = var0;
      this.c = var1;
   }

   private static void a(List<DataWatcher.b<?>> var0, PacketDataSerializer var1) {
      for(DataWatcher.b<?> var3 : var0) {
         var3.a(var1);
      }

      var1.writeByte(255);
   }

   private static List<DataWatcher.b<?>> b(PacketDataSerializer var0) {
      List<DataWatcher.b<?>> var1 = new ArrayList<>();

      int var2;
      while((var2 = var0.readUnsignedByte()) != 255) {
         var1.add(DataWatcher.b.a(var0, var2));
      }

      return var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.b);
      a(this.c, var0);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.b;
   }
}
