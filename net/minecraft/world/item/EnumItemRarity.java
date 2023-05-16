package net.minecraft.world.item;

import net.minecraft.EnumChatFormat;

public enum EnumItemRarity {
   a(EnumChatFormat.p),
   b(EnumChatFormat.o),
   c(EnumChatFormat.l),
   d(EnumChatFormat.n);

   public final EnumChatFormat e;

   private EnumItemRarity(EnumChatFormat var2) {
      this.e = var2;
   }
}
