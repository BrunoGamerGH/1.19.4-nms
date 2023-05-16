package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.world.phys.Vec3D;

public class PacketPlayOutExplosion implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final double c;
   private final float d;
   private final List<BlockPosition> e;
   private final float f;
   private final float g;
   private final float h;

   public PacketPlayOutExplosion(double var0, double var2, double var4, float var6, List<BlockPosition> var7, @Nullable Vec3D var8) {
      this.a = var0;
      this.b = var2;
      this.c = var4;
      this.d = var6;
      this.e = Lists.newArrayList(var7);
      if (var8 != null) {
         this.f = (float)var8.c;
         this.g = (float)var8.d;
         this.h = (float)var8.e;
      } else {
         this.f = 0.0F;
         this.g = 0.0F;
         this.h = 0.0F;
      }
   }

   public PacketPlayOutExplosion(PacketDataSerializer var0) {
      this.a = var0.readDouble();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.d = var0.readFloat();
      int var1 = MathHelper.a(this.a);
      int var2 = MathHelper.a(this.b);
      int var3 = MathHelper.a(this.c);
      this.e = var0.a((PacketDataSerializer.a<BlockPosition>)(var3x -> {
         int var4x = var3x.readByte() + var1;
         int var5 = var3x.readByte() + var2;
         int var6 = var3x.readByte() + var3;
         return new BlockPosition(var4x, var5, var6);
      }));
      this.f = var0.readFloat();
      this.g = var0.readFloat();
      this.h = var0.readFloat();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeDouble(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeFloat(this.d);
      int var1 = MathHelper.a(this.a);
      int var2 = MathHelper.a(this.b);
      int var3 = MathHelper.a(this.c);
      var0.a(this.e, (var3x, var4x) -> {
         int var5 = var4x.u() - var1;
         int var6 = var4x.v() - var2;
         int var7 = var4x.w() - var3;
         var3x.writeByte(var5);
         var3x.writeByte(var6);
         var3x.writeByte(var7);
      });
      var0.writeFloat(this.f);
      var0.writeFloat(this.g);
      var0.writeFloat(this.h);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public float a() {
      return this.f;
   }

   public float c() {
      return this.g;
   }

   public float d() {
      return this.h;
   }

   public double e() {
      return this.a;
   }

   public double f() {
      return this.b;
   }

   public double g() {
      return this.c;
   }

   public float h() {
      return this.d;
   }

   public List<BlockPosition> i() {
      return this.e;
   }
}
