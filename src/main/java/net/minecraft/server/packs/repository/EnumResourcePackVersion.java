package net.minecraft.server.packs.repository;

import net.minecraft.EnumChatFormat;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.packs.EnumResourcePackType;

public enum EnumResourcePackVersion {
   a("old"),
   b("new"),
   c("compatible");

   private final IChatBaseComponent d;
   private final IChatBaseComponent e;

   private EnumResourcePackVersion(String var2) {
      this.d = IChatBaseComponent.c("pack.incompatible." + var2).a(EnumChatFormat.h);
      this.e = IChatBaseComponent.c("pack.incompatible.confirm." + var2);
   }

   public boolean a() {
      return this == c;
   }

   public static EnumResourcePackVersion a(int var0, EnumResourcePackType var1) {
      int var2 = SharedConstants.b().a(var1);
      if (var0 < var2) {
         return a;
      } else {
         return var0 > var2 ? b : c;
      }
   }

   public IChatBaseComponent b() {
      return this.d;
   }

   public IChatBaseComponent c() {
      return this.e;
   }
}
