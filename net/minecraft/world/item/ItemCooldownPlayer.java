package net.minecraft.world.item;

import net.minecraft.network.protocol.game.PacketPlayOutSetCooldown;
import net.minecraft.server.level.EntityPlayer;

public class ItemCooldownPlayer extends ItemCooldown {
   private final EntityPlayer a;

   public ItemCooldownPlayer(EntityPlayer var0) {
      this.a = var0;
   }

   @Override
   protected void b(Item var0, int var1) {
      super.b(var0, var1);
      this.a.b.a(new PacketPlayOutSetCooldown(var0, var1));
   }

   @Override
   protected void c(Item var0) {
      super.c(var0);
      this.a.b.a(new PacketPlayOutSetCooldown(var0, 0));
   }
}
