package net.minecraft.world.entity.player;

import net.minecraft.network.chat.IChatBaseComponent;

public enum PlayerModelPart {
   a(0, "cape"),
   b(1, "jacket"),
   c(2, "left_sleeve"),
   d(3, "right_sleeve"),
   e(4, "left_pants_leg"),
   f(5, "right_pants_leg"),
   g(6, "hat");

   private final int h;
   private final int i;
   private final String j;
   private final IChatBaseComponent k;

   private PlayerModelPart(int var2, String var3) {
      this.h = var2;
      this.i = 1 << var2;
      this.j = var3;
      this.k = IChatBaseComponent.c("options.modelPart." + var3);
   }

   public int a() {
      return this.i;
   }

   public int b() {
      return this.h;
   }

   public String c() {
      return this.j;
   }

   public IChatBaseComponent d() {
      return this.k;
   }
}
