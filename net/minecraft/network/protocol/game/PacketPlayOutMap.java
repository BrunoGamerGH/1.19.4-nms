package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;

public class PacketPlayOutMap implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final byte b;
   private final boolean c;
   @Nullable
   private final List<MapIcon> d;
   @Nullable
   private final WorldMap.b e;

   public PacketPlayOutMap(int var0, byte var1, boolean var2, @Nullable Collection<MapIcon> var3, @Nullable WorldMap.b var4) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3 != null ? Lists.newArrayList(var3) : null;
      this.e = var4;
   }

   public PacketPlayOutMap(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.readByte();
      this.c = var0.readBoolean();
      this.d = var0.c(var0x -> var0x.a((PacketDataSerializer.a)(var0xx -> {
            MapIcon.Type var1x = var0xx.b(MapIcon.Type.class);
            byte var2x = var0xx.readByte();
            byte var3x = var0xx.readByte();
            byte var4x = (byte)(var0xx.readByte() & 15);
            IChatBaseComponent var5x = var0xx.c(PacketDataSerializer::l);
            return new MapIcon(var1x, var2x, var3x, var4x, var5x);
         })));
      int var1 = var0.readUnsignedByte();
      if (var1 > 0) {
         int var2 = var0.readUnsignedByte();
         int var3 = var0.readUnsignedByte();
         int var4 = var0.readUnsignedByte();
         byte[] var5 = var0.b();
         this.e = new WorldMap.b(var3, var4, var1, var2, var5);
      } else {
         this.e = null;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeByte(this.b);
      var0.writeBoolean(this.c);
      var0.a(this.d, (var0x, var1x) -> var0x.a(var1x, (var0xx, var1xx) -> {
            var0xx.a(var1xx.b());
            var0xx.writeByte(var1xx.c());
            var0xx.writeByte(var1xx.d());
            var0xx.writeByte(var1xx.e() & 15);
            var0xx.a(var1xx.g(), PacketDataSerializer::a);
         }));
      if (this.e != null) {
         var0.writeByte(this.e.c);
         var0.writeByte(this.e.d);
         var0.writeByte(this.e.a);
         var0.writeByte(this.e.b);
         var0.a(this.e.e);
      } else {
         var0.writeByte(0);
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public void a(WorldMap var0) {
      if (this.d != null) {
         var0.a(this.d);
      }

      if (this.e != null) {
         this.e.a(var0);
      }
   }

   public byte c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
