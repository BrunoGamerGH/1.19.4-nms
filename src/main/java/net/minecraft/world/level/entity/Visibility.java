package net.minecraft.world.level.entity;

import net.minecraft.server.level.PlayerChunk;

public enum Visibility {
   a(false, false),
   b(true, false),
   c(true, true);

   private final boolean d;
   private final boolean e;

   private Visibility(boolean var2, boolean var3) {
      this.d = var2;
      this.e = var3;
   }

   public boolean a() {
      return this.e;
   }

   public boolean b() {
      return this.d;
   }

   public static Visibility a(PlayerChunk.State var0) {
      if (var0.a(PlayerChunk.State.d)) {
         return c;
      } else {
         return var0.a(PlayerChunk.State.b) ? b : a;
      }
   }
}
