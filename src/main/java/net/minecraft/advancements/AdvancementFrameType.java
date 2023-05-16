package net.minecraft.advancements;

import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;

public enum AdvancementFrameType {
   a("task", 0, EnumChatFormat.k),
   b("challenge", 26, EnumChatFormat.f),
   c("goal", 52, EnumChatFormat.k);

   private final String d;
   private final int e;
   private final EnumChatFormat f;
   private final IChatBaseComponent g;

   private AdvancementFrameType(String var2, int var3, EnumChatFormat var4) {
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = IChatBaseComponent.c("advancements.toast." + var2);
   }

   public String a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public static AdvancementFrameType a(String var0) {
      for(AdvancementFrameType var4 : values()) {
         if (var4.d.equals(var0)) {
            return var4;
         }
      }

      throw new IllegalArgumentException("Unknown frame type '" + var0 + "'");
   }

   public EnumChatFormat c() {
      return this.f;
   }

   public IChatBaseComponent d() {
      return this.g;
   }
}
