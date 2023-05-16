package net.minecraft.network.chat;

public class ThrowingComponent extends Exception {
   private final IChatBaseComponent a;

   public ThrowingComponent(IChatBaseComponent var0) {
      super(var0.getString());
      this.a = var0;
   }

   public ThrowingComponent(IChatBaseComponent var0, Throwable var1) {
      super(var0.getString(), var1);
      this.a = var0;
   }

   public IChatBaseComponent b() {
      return this.a;
   }
}
