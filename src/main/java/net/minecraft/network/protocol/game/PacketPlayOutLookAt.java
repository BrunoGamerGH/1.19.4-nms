package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class PacketPlayOutLookAt implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final double c;
   private final int d;
   private final ArgumentAnchor.Anchor e;
   private final ArgumentAnchor.Anchor f;
   private final boolean g;

   public PacketPlayOutLookAt(ArgumentAnchor.Anchor var0, double var1, double var3, double var5) {
      this.e = var0;
      this.a = var1;
      this.b = var3;
      this.c = var5;
      this.d = 0;
      this.g = false;
      this.f = null;
   }

   public PacketPlayOutLookAt(ArgumentAnchor.Anchor var0, Entity var1, ArgumentAnchor.Anchor var2) {
      this.e = var0;
      this.d = var1.af();
      this.f = var2;
      Vec3D var3 = var2.a(var1);
      this.a = var3.c;
      this.b = var3.d;
      this.c = var3.e;
      this.g = true;
   }

   public PacketPlayOutLookAt(PacketDataSerializer var0) {
      this.e = var0.b(ArgumentAnchor.Anchor.class);
      this.a = var0.readDouble();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.g = var0.readBoolean();
      if (this.g) {
         this.d = var0.m();
         this.f = var0.b(ArgumentAnchor.Anchor.class);
      } else {
         this.d = 0;
         this.f = null;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.e);
      var0.writeDouble(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeBoolean(this.g);
      if (this.g) {
         var0.d(this.d);
         var0.a(this.f);
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public ArgumentAnchor.Anchor a() {
      return this.e;
   }

   @Nullable
   public Vec3D a(World var0) {
      if (this.g) {
         Entity var1 = var0.a(this.d);
         return var1 == null ? new Vec3D(this.a, this.b, this.c) : this.f.a(var1);
      } else {
         return new Vec3D(this.a, this.b, this.c);
      }
   }
}
