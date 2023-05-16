package net.minecraft.network.protocol.game;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public record ClientboundDamageEventPacket(int entityId, int sourceTypeId, int sourceCauseId, int sourceDirectId, Optional<Vec3D> sourcePosition)
   implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final int c;
   private final int d;
   private final Optional<Vec3D> e;

   public ClientboundDamageEventPacket(Entity var0, DamageSource var1) {
      this(
         var0.af(),
         var0.Y().u_().d(Registries.o).a(var1.j()),
         var1.d() != null ? var1.d().af() : -1,
         var1.c() != null ? var1.c().af() : -1,
         Optional.ofNullable(var1.i())
      );
   }

   public ClientboundDamageEventPacket(PacketDataSerializer var0) {
      this(var0.m(), var0.m(), b(var0), b(var0), var0.b(var0x -> new Vec3D(var0x.readDouble(), var0x.readDouble(), var0x.readDouble())));
   }

   public ClientboundDamageEventPacket(int var0, int var1, int var2, int var3, Optional<Vec3D> var4) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   private static void a(PacketDataSerializer var0, int var1) {
      var0.d(var1 + 1);
   }

   private static int b(PacketDataSerializer var0) {
      return var0.m() - 1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.d(this.b);
      a(var0, this.c);
      a(var0, this.d);
      var0.a(this.e, (var0x, var1x) -> {
         var0x.writeDouble(var1x.a());
         var0x.writeDouble(var1x.b());
         var0x.writeDouble(var1x.c());
      });
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public DamageSource a(World var0) {
      Holder<DamageType> var1 = var0.u_().d(Registries.o).c(this.b).get();
      if (this.e.isPresent()) {
         return new DamageSource(var1, this.e.get());
      } else {
         Entity var2 = var0.a(this.c);
         Entity var3 = var0.a(this.d);
         return new DamageSource(var1, var3, var2);
      }
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public Optional<Vec3D> f() {
      return this.e;
   }
}
