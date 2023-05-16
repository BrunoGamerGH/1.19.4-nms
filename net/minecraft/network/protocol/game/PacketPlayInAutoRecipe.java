package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.IRecipe;

public class PacketPlayInAutoRecipe implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final MinecraftKey b;
   private final boolean c;

   public PacketPlayInAutoRecipe(int var0, IRecipe<?> var1, boolean var2) {
      this.a = var0;
      this.b = var1.e();
      this.c = var2;
   }

   public PacketPlayInAutoRecipe(PacketDataSerializer var0) {
      this.a = var0.readByte();
      this.b = var0.t();
      this.c = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.a(this.b);
      var0.writeBoolean(this.c);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public MinecraftKey c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
