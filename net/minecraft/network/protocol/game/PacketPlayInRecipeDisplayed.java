package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.IRecipe;

public class PacketPlayInRecipeDisplayed implements Packet<PacketListenerPlayIn> {
   private final MinecraftKey a;

   public PacketPlayInRecipeDisplayed(IRecipe<?> var0) {
      this.a = var0.e();
   }

   public PacketPlayInRecipeDisplayed(PacketDataSerializer var0) {
      this.a = var0.t();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public MinecraftKey a() {
      return this.a;
   }
}
