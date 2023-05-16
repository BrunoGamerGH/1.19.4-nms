package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutGameStateChange implements Packet<PacketListenerPlayOut> {
   public static final PacketPlayOutGameStateChange.a a = new PacketPlayOutGameStateChange.a(0);
   public static final PacketPlayOutGameStateChange.a b = new PacketPlayOutGameStateChange.a(1);
   public static final PacketPlayOutGameStateChange.a c = new PacketPlayOutGameStateChange.a(2);
   public static final PacketPlayOutGameStateChange.a d = new PacketPlayOutGameStateChange.a(3);
   public static final PacketPlayOutGameStateChange.a e = new PacketPlayOutGameStateChange.a(4);
   public static final PacketPlayOutGameStateChange.a f = new PacketPlayOutGameStateChange.a(5);
   public static final PacketPlayOutGameStateChange.a g = new PacketPlayOutGameStateChange.a(6);
   public static final PacketPlayOutGameStateChange.a h = new PacketPlayOutGameStateChange.a(7);
   public static final PacketPlayOutGameStateChange.a i = new PacketPlayOutGameStateChange.a(8);
   public static final PacketPlayOutGameStateChange.a j = new PacketPlayOutGameStateChange.a(9);
   public static final PacketPlayOutGameStateChange.a k = new PacketPlayOutGameStateChange.a(10);
   public static final PacketPlayOutGameStateChange.a l = new PacketPlayOutGameStateChange.a(11);
   public static final int m = 0;
   public static final int n = 101;
   public static final int o = 102;
   public static final int p = 103;
   public static final int q = 104;
   private final PacketPlayOutGameStateChange.a r;
   private final float s;

   public PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.a var0, float var1) {
      this.r = var0;
      this.s = var1;
   }

   public PacketPlayOutGameStateChange(PacketDataSerializer var0) {
      this.r = (PacketPlayOutGameStateChange.a)PacketPlayOutGameStateChange.a.a.get(var0.readUnsignedByte());
      this.s = var0.readFloat();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.r.b);
      var0.writeFloat(this.s);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public PacketPlayOutGameStateChange.a a() {
      return this.r;
   }

   public float c() {
      return this.s;
   }

   public static class a {
      static final Int2ObjectMap<PacketPlayOutGameStateChange.a> a = new Int2ObjectOpenHashMap();
      final int b;

      public a(int var0) {
         this.b = var0;
         a.put(var0, this);
      }
   }
}
