package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;

public abstract class PacketPlayOutEntity implements Packet<PacketListenerPlayOut> {
   protected final int a;
   protected final short b;
   protected final short c;
   protected final short d;
   protected final byte e;
   protected final byte f;
   protected final boolean g;
   protected final boolean h;
   protected final boolean i;

   protected PacketPlayOutEntity(int var0, short var1, short var2, short var3, byte var4, byte var5, boolean var6, boolean var7, boolean var8) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
      this.i = var8;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Override
   public String toString() {
      return "Entity_" + super.toString();
   }

   @Nullable
   public Entity a(World var0) {
      return var0.a(this.a);
   }

   public short a() {
      return this.b;
   }

   public short c() {
      return this.c;
   }

   public short d() {
      return this.d;
   }

   public byte e() {
      return this.e;
   }

   public byte f() {
      return this.f;
   }

   public boolean g() {
      return this.h;
   }

   public boolean h() {
      return this.i;
   }

   public boolean i() {
      return this.g;
   }

   public static class PacketPlayOutEntityLook extends PacketPlayOutEntity {
      public PacketPlayOutEntityLook(int var0, byte var1, byte var2, boolean var3) {
         super(var0, (short)0, (short)0, (short)0, var1, var2, var3, true, false);
      }

      public static PacketPlayOutEntity.PacketPlayOutEntityLook b(PacketDataSerializer var0) {
         int var1 = var0.m();
         byte var2 = var0.readByte();
         byte var3 = var0.readByte();
         boolean var4 = var0.readBoolean();
         return new PacketPlayOutEntity.PacketPlayOutEntityLook(var1, var2, var3, var4);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.d(this.a);
         var0.writeByte(this.e);
         var0.writeByte(this.f);
         var0.writeBoolean(this.g);
      }
   }

   public static class PacketPlayOutRelEntityMove extends PacketPlayOutEntity {
      public PacketPlayOutRelEntityMove(int var0, short var1, short var2, short var3, boolean var4) {
         super(var0, var1, var2, var3, (byte)0, (byte)0, var4, false, true);
      }

      public static PacketPlayOutEntity.PacketPlayOutRelEntityMove b(PacketDataSerializer var0) {
         int var1 = var0.m();
         short var2 = var0.readShort();
         short var3 = var0.readShort();
         short var4 = var0.readShort();
         boolean var5 = var0.readBoolean();
         return new PacketPlayOutEntity.PacketPlayOutRelEntityMove(var1, var2, var3, var4, var5);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.d(this.a);
         var0.writeShort(this.b);
         var0.writeShort(this.c);
         var0.writeShort(this.d);
         var0.writeBoolean(this.g);
      }
   }

   public static class PacketPlayOutRelEntityMoveLook extends PacketPlayOutEntity {
      public PacketPlayOutRelEntityMoveLook(int var0, short var1, short var2, short var3, byte var4, byte var5, boolean var6) {
         super(var0, var1, var2, var3, var4, var5, var6, true, true);
      }

      public static PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook b(PacketDataSerializer var0) {
         int var1 = var0.m();
         short var2 = var0.readShort();
         short var3 = var0.readShort();
         short var4 = var0.readShort();
         byte var5 = var0.readByte();
         byte var6 = var0.readByte();
         boolean var7 = var0.readBoolean();
         return new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(var1, var2, var3, var4, var5, var6, var7);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.d(this.a);
         var0.writeShort(this.b);
         var0.writeShort(this.c);
         var0.writeShort(this.d);
         var0.writeByte(this.e);
         var0.writeByte(this.f);
         var0.writeBoolean(this.g);
      }
   }
}
