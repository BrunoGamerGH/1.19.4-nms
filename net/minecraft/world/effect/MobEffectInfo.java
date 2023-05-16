package net.minecraft.world.effect;

import net.minecraft.EnumChatFormat;

public enum MobEffectInfo {
   a(EnumChatFormat.j),
   b(EnumChatFormat.m),
   c(EnumChatFormat.j);

   private final EnumChatFormat d;

   private MobEffectInfo(EnumChatFormat var2) {
      this.d = var2;
   }

   public EnumChatFormat a() {
      return this.d;
   }
}
