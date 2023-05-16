package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.EntityMinecartCommandBlock;
import net.minecraft.world.level.CommandBlockListenerAbstract;
import net.minecraft.world.level.World;

public class PacketPlayInSetCommandMinecart implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final String b;
   private final boolean c;

   public PacketPlayInSetCommandMinecart(int var0, String var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayInSetCommandMinecart(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.s();
      this.c = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
      var0.writeBoolean(this.c);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   @Nullable
   public CommandBlockListenerAbstract a(World var0) {
      Entity var1 = var0.a(this.a);
      return var1 instanceof EntityMinecartCommandBlock ? ((EntityMinecartCommandBlock)var1).z() : null;
   }

   public String a() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }
}
