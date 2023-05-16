package net.minecraft.world.level.saveddata.maps;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;

public class MapIcon {
   private final MapIcon.Type a;
   private final byte b;
   private final byte c;
   private final byte d;
   @Nullable
   private final IChatBaseComponent e;

   public MapIcon(MapIcon.Type var0, byte var1, byte var2, byte var3, @Nullable IChatBaseComponent var4) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   public byte a() {
      return this.a.a();
   }

   public MapIcon.Type b() {
      return this.a;
   }

   public byte c() {
      return this.b;
   }

   public byte d() {
      return this.c;
   }

   public byte e() {
      return this.d;
   }

   public boolean f() {
      return this.a.b();
   }

   @Nullable
   public IChatBaseComponent g() {
      return this.e;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof MapIcon)) {
         return false;
      } else {
         MapIcon var1 = (MapIcon)var0;
         return this.a == var1.a && this.d == var1.d && this.b == var1.b && this.c == var1.c && Objects.equals(this.e, var1.e);
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.a.a();
      var0 = 31 * var0 + this.b;
      var0 = 31 * var0 + this.c;
      var0 = 31 * var0 + this.d;
      return 31 * var0 + Objects.hashCode(this.e);
   }

   public static enum Type {
      a(false, true),
      b(true, true),
      c(false, true),
      d(false, true),
      e(true, false),
      f(true, false),
      g(false, true),
      h(false, true),
      i(true, 5393476, false),
      j(true, 3830373, false),
      k(true, true),
      l(true, true),
      m(true, true),
      n(true, true),
      o(true, true),
      p(true, true),
      q(true, true),
      r(true, true),
      s(true, true),
      t(true, true),
      u(true, true),
      v(true, true),
      w(true, true),
      x(true, true),
      y(true, true),
      z(true, true),
      A(true, false);

      private final byte B;
      private final boolean C;
      private final int D;
      private final boolean E;

      private Type(boolean var2, boolean var3) {
         this(var2, -1, var3);
      }

      private Type(boolean var2, int var3, boolean var4) {
         this.E = var4;
         this.B = (byte)this.ordinal();
         this.C = var2;
         this.D = var3;
      }

      public byte a() {
         return this.B;
      }

      public boolean b() {
         return this.C;
      }

      public boolean c() {
         return this.D >= 0;
      }

      public int d() {
         return this.D;
      }

      public static MapIcon.Type a(byte var0) {
         return values()[MathHelper.a(var0, 0, values().length - 1)];
      }

      public boolean e() {
         return this.E;
      }
   }
}
