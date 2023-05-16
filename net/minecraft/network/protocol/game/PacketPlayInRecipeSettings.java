package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.RecipeBookType;

public class PacketPlayInRecipeSettings implements Packet<PacketListenerPlayIn> {
   private final RecipeBookType a;
   private final boolean b;
   private final boolean c;

   public PacketPlayInRecipeSettings(RecipeBookType var0, boolean var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayInRecipeSettings(PacketDataSerializer var0) {
      this.a = var0.b(RecipeBookType.class);
      this.b = var0.readBoolean();
      this.c = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.writeBoolean(this.b);
      var0.writeBoolean(this.c);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public RecipeBookType a() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
