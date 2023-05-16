package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketPlayInAdvancements implements Packet<PacketListenerPlayIn> {
   private final PacketPlayInAdvancements.Status a;
   @Nullable
   private final MinecraftKey b;

   public PacketPlayInAdvancements(PacketPlayInAdvancements.Status var0, @Nullable MinecraftKey var1) {
      this.a = var0;
      this.b = var1;
   }

   public static PacketPlayInAdvancements a(Advancement var0) {
      return new PacketPlayInAdvancements(PacketPlayInAdvancements.Status.a, var0.i());
   }

   public static PacketPlayInAdvancements a() {
      return new PacketPlayInAdvancements(PacketPlayInAdvancements.Status.b, null);
   }

   public PacketPlayInAdvancements(PacketDataSerializer var0) {
      this.a = var0.b(PacketPlayInAdvancements.Status.class);
      if (this.a == PacketPlayInAdvancements.Status.a) {
         this.b = var0.t();
      } else {
         this.b = null;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      if (this.a == PacketPlayInAdvancements.Status.a) {
         var0.a(this.b);
      }
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInAdvancements.Status c() {
      return this.a;
   }

   @Nullable
   public MinecraftKey d() {
      return this.b;
   }

   public static enum Status {
      a,
      b;
   }
}
