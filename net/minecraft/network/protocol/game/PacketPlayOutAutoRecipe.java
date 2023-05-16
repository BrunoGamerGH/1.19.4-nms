package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.IRecipe;

public class PacketPlayOutAutoRecipe implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final MinecraftKey b;

   public PacketPlayOutAutoRecipe(int var0, IRecipe<?> var1) {
      this.a = var0;
      this.b = var1.e();
   }

   public PacketPlayOutAutoRecipe(PacketDataSerializer var0) {
      this.a = var0.readByte();
      this.b = var0.t();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.a(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public MinecraftKey a() {
      return this.b;
   }

   public int c() {
      return this.a;
   }
}
