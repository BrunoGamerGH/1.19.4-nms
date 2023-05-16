package net.minecraft.network.protocol.game;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3D;

public class PacketPlayInUseEntity implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final PacketPlayInUseEntity.EnumEntityUseAction b;
   private final boolean c;
   static final PacketPlayInUseEntity.EnumEntityUseAction d = new PacketPlayInUseEntity.EnumEntityUseAction() {
      @Override
      public PacketPlayInUseEntity.b a() {
         return PacketPlayInUseEntity.b.b;
      }

      @Override
      public void a(PacketPlayInUseEntity.c var0) {
         var0.a();
      }

      @Override
      public void a(PacketDataSerializer var0) {
      }
   };

   private PacketPlayInUseEntity(int var0, boolean var1, PacketPlayInUseEntity.EnumEntityUseAction var2) {
      this.a = var0;
      this.b = var2;
      this.c = var1;
   }

   public static PacketPlayInUseEntity a(Entity var0, boolean var1) {
      return new PacketPlayInUseEntity(var0.af(), var1, d);
   }

   public static PacketPlayInUseEntity a(Entity var0, boolean var1, EnumHand var2) {
      return new PacketPlayInUseEntity(var0.af(), var1, new PacketPlayInUseEntity.d(var2));
   }

   public static PacketPlayInUseEntity a(Entity var0, boolean var1, EnumHand var2, Vec3D var3) {
      return new PacketPlayInUseEntity(var0.af(), var1, new PacketPlayInUseEntity.e(var2, var3));
   }

   public PacketPlayInUseEntity(PacketDataSerializer var0) {
      this.a = var0.m();
      PacketPlayInUseEntity.b var1 = var0.b(PacketPlayInUseEntity.b.class);
      this.b = var1.d.apply(var0);
      this.c = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b.a());
      this.b.a(var0);
      var0.writeBoolean(this.c);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   @Nullable
   public Entity a(WorldServer var0) {
      return var0.b(this.a);
   }

   public boolean a() {
      return this.c;
   }

   public void a(PacketPlayInUseEntity.c var0) {
      this.b.a(var0);
   }

   interface EnumEntityUseAction {
      PacketPlayInUseEntity.b a();

      void a(PacketPlayInUseEntity.c var1);

      void a(PacketDataSerializer var1);
   }

   static enum b {
      a(PacketPlayInUseEntity.d::new),
      b(var0 -> PacketPlayInUseEntity.d),
      c(PacketPlayInUseEntity.e::new);

      final Function<PacketDataSerializer, PacketPlayInUseEntity.EnumEntityUseAction> d;

      private b(Function var2) {
         this.d = var2;
      }
   }

   public interface c {
      void a(EnumHand var1);

      void a(EnumHand var1, Vec3D var2);

      void a();
   }

   static class d implements PacketPlayInUseEntity.EnumEntityUseAction {
      private final EnumHand a;

      d(EnumHand var0) {
         this.a = var0;
      }

      private d(PacketDataSerializer var0) {
         this.a = var0.b(EnumHand.class);
      }

      @Override
      public PacketPlayInUseEntity.b a() {
         return PacketPlayInUseEntity.b.a;
      }

      @Override
      public void a(PacketPlayInUseEntity.c var0) {
         var0.a(this.a);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
      }
   }

   static class e implements PacketPlayInUseEntity.EnumEntityUseAction {
      private final EnumHand a;
      private final Vec3D b;

      e(EnumHand var0, Vec3D var1) {
         this.a = var0;
         this.b = var1;
      }

      private e(PacketDataSerializer var0) {
         this.b = new Vec3D((double)var0.readFloat(), (double)var0.readFloat(), (double)var0.readFloat());
         this.a = var0.b(EnumHand.class);
      }

      @Override
      public PacketPlayInUseEntity.b a() {
         return PacketPlayInUseEntity.b.c;
      }

      @Override
      public void a(PacketPlayInUseEntity.c var0) {
         var0.a(this.a, this.b);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeFloat((float)this.b.c);
         var0.writeFloat((float)this.b.d);
         var0.writeFloat((float)this.b.e);
         var0.a(this.a);
      }
   }
}
