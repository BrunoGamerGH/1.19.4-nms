package net.minecraft.network.chat;

public record LastSeenTrackedEntry(MessageSignature signature, boolean pending) {
   private final MessageSignature a;
   private final boolean b;

   public LastSeenTrackedEntry(MessageSignature var0, boolean var1) {
      this.a = var0;
      this.b = var1;
   }

   public LastSeenTrackedEntry a() {
      return this.b ? new LastSeenTrackedEntry(this.a, false) : this;
   }

   public MessageSignature b() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }
}
