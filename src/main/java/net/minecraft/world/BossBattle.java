package net.minecraft.world;

import java.util.UUID;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;

public abstract class BossBattle {
   private final UUID h;
   public IChatBaseComponent a;
   protected float b;
   public BossBattle.BarColor c;
   public BossBattle.BarStyle d;
   protected boolean e;
   protected boolean f;
   protected boolean g;

   public BossBattle(UUID var0, IChatBaseComponent var1, BossBattle.BarColor var2, BossBattle.BarStyle var3) {
      this.h = var0;
      this.a = var1;
      this.c = var2;
      this.d = var3;
      this.b = 1.0F;
   }

   public UUID i() {
      return this.h;
   }

   public IChatBaseComponent j() {
      return this.a;
   }

   public void a(IChatBaseComponent var0) {
      this.a = var0;
   }

   public float k() {
      return this.b;
   }

   public void a(float var0) {
      this.b = var0;
   }

   public BossBattle.BarColor l() {
      return this.c;
   }

   public void a(BossBattle.BarColor var0) {
      this.c = var0;
   }

   public BossBattle.BarStyle m() {
      return this.d;
   }

   public void a(BossBattle.BarStyle var0) {
      this.d = var0;
   }

   public boolean n() {
      return this.e;
   }

   public BossBattle a(boolean var0) {
      this.e = var0;
      return this;
   }

   public boolean o() {
      return this.f;
   }

   public BossBattle b(boolean var0) {
      this.f = var0;
      return this;
   }

   public BossBattle c(boolean var0) {
      this.g = var0;
      return this;
   }

   public boolean p() {
      return this.g;
   }

   public static enum BarColor {
      a("pink", EnumChatFormat.m),
      b("blue", EnumChatFormat.j),
      c("red", EnumChatFormat.e),
      d("green", EnumChatFormat.k),
      e("yellow", EnumChatFormat.o),
      f("purple", EnumChatFormat.b),
      g("white", EnumChatFormat.p);

      private final String h;
      private final EnumChatFormat i;

      private BarColor(String var2, EnumChatFormat var3) {
         this.h = var2;
         this.i = var3;
      }

      public EnumChatFormat a() {
         return this.i;
      }

      public String b() {
         return this.h;
      }

      public static BossBattle.BarColor a(String var0) {
         for(BossBattle.BarColor var4 : values()) {
            if (var4.h.equals(var0)) {
               return var4;
            }
         }

         return g;
      }
   }

   public static enum BarStyle {
      a("progress"),
      b("notched_6"),
      c("notched_10"),
      d("notched_12"),
      e("notched_20");

      private final String f;

      private BarStyle(String var2) {
         this.f = var2;
      }

      public String a() {
         return this.f;
      }

      public static BossBattle.BarStyle a(String var0) {
         for(BossBattle.BarStyle var4 : values()) {
            if (var4.f.equals(var0)) {
               return var4;
            }
         }

         return a;
      }
   }
}
