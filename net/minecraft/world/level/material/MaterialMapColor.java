package net.minecraft.world.level.material;

import com.google.common.base.Preconditions;

public class MaterialMapColor {
   public static final MaterialMapColor[] am = new MaterialMapColor[64];
   public static final MaterialMapColor a = new MaterialMapColor(0, 0);
   public static final MaterialMapColor b = new MaterialMapColor(1, 8368696);
   public static final MaterialMapColor c = new MaterialMapColor(2, 16247203);
   public static final MaterialMapColor d = new MaterialMapColor(3, 13092807);
   public static final MaterialMapColor e = new MaterialMapColor(4, 16711680);
   public static final MaterialMapColor f = new MaterialMapColor(5, 10526975);
   public static final MaterialMapColor g = new MaterialMapColor(6, 10987431);
   public static final MaterialMapColor h = new MaterialMapColor(7, 31744);
   public static final MaterialMapColor i = new MaterialMapColor(8, 16777215);
   public static final MaterialMapColor j = new MaterialMapColor(9, 10791096);
   public static final MaterialMapColor k = new MaterialMapColor(10, 9923917);
   public static final MaterialMapColor l = new MaterialMapColor(11, 7368816);
   public static final MaterialMapColor m = new MaterialMapColor(12, 4210943);
   public static final MaterialMapColor n = new MaterialMapColor(13, 9402184);
   public static final MaterialMapColor o = new MaterialMapColor(14, 16776437);
   public static final MaterialMapColor p = new MaterialMapColor(15, 14188339);
   public static final MaterialMapColor q = new MaterialMapColor(16, 11685080);
   public static final MaterialMapColor r = new MaterialMapColor(17, 6724056);
   public static final MaterialMapColor s = new MaterialMapColor(18, 15066419);
   public static final MaterialMapColor t = new MaterialMapColor(19, 8375321);
   public static final MaterialMapColor u = new MaterialMapColor(20, 15892389);
   public static final MaterialMapColor v = new MaterialMapColor(21, 5000268);
   public static final MaterialMapColor w = new MaterialMapColor(22, 10066329);
   public static final MaterialMapColor x = new MaterialMapColor(23, 5013401);
   public static final MaterialMapColor y = new MaterialMapColor(24, 8339378);
   public static final MaterialMapColor z = new MaterialMapColor(25, 3361970);
   public static final MaterialMapColor A = new MaterialMapColor(26, 6704179);
   public static final MaterialMapColor B = new MaterialMapColor(27, 6717235);
   public static final MaterialMapColor C = new MaterialMapColor(28, 10040115);
   public static final MaterialMapColor D = new MaterialMapColor(29, 1644825);
   public static final MaterialMapColor E = new MaterialMapColor(30, 16445005);
   public static final MaterialMapColor F = new MaterialMapColor(31, 6085589);
   public static final MaterialMapColor G = new MaterialMapColor(32, 4882687);
   public static final MaterialMapColor H = new MaterialMapColor(33, 55610);
   public static final MaterialMapColor I = new MaterialMapColor(34, 8476209);
   public static final MaterialMapColor J = new MaterialMapColor(35, 7340544);
   public static final MaterialMapColor K = new MaterialMapColor(36, 13742497);
   public static final MaterialMapColor L = new MaterialMapColor(37, 10441252);
   public static final MaterialMapColor M = new MaterialMapColor(38, 9787244);
   public static final MaterialMapColor N = new MaterialMapColor(39, 7367818);
   public static final MaterialMapColor O = new MaterialMapColor(40, 12223780);
   public static final MaterialMapColor P = new MaterialMapColor(41, 6780213);
   public static final MaterialMapColor Q = new MaterialMapColor(42, 10505550);
   public static final MaterialMapColor R = new MaterialMapColor(43, 3746083);
   public static final MaterialMapColor S = new MaterialMapColor(44, 8874850);
   public static final MaterialMapColor T = new MaterialMapColor(45, 5725276);
   public static final MaterialMapColor U = new MaterialMapColor(46, 8014168);
   public static final MaterialMapColor V = new MaterialMapColor(47, 4996700);
   public static final MaterialMapColor W = new MaterialMapColor(48, 4993571);
   public static final MaterialMapColor X = new MaterialMapColor(49, 5001770);
   public static final MaterialMapColor Y = new MaterialMapColor(50, 9321518);
   public static final MaterialMapColor Z = new MaterialMapColor(51, 2430480);
   public static final MaterialMapColor aa = new MaterialMapColor(52, 12398641);
   public static final MaterialMapColor ab = new MaterialMapColor(53, 9715553);
   public static final MaterialMapColor ac = new MaterialMapColor(54, 6035741);
   public static final MaterialMapColor ad = new MaterialMapColor(55, 1474182);
   public static final MaterialMapColor ae = new MaterialMapColor(56, 3837580);
   public static final MaterialMapColor af = new MaterialMapColor(57, 5647422);
   public static final MaterialMapColor ag = new MaterialMapColor(58, 1356933);
   public static final MaterialMapColor ah = new MaterialMapColor(59, 6579300);
   public static final MaterialMapColor ai = new MaterialMapColor(60, 14200723);
   public static final MaterialMapColor aj = new MaterialMapColor(61, 8365974);
   public final int ak;
   public final int al;

   private MaterialMapColor(int var0, int var1) {
      if (var0 >= 0 && var0 <= 63) {
         this.al = var0;
         this.ak = var1;
         am[var0] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int a(MaterialMapColor.a var0) {
      if (this == a) {
         return 0;
      } else {
         int var1 = var0.f;
         int var2 = (this.ak >> 16 & 0xFF) * var1 / 255;
         int var3 = (this.ak >> 8 & 0xFF) * var1 / 255;
         int var4 = (this.ak & 0xFF) * var1 / 255;
         return 0xFF000000 | var4 << 16 | var3 << 8 | var2;
      }
   }

   public static MaterialMapColor a(int var0) {
      Preconditions.checkPositionIndex(var0, am.length, "material id");
      return c(var0);
   }

   private static MaterialMapColor c(int var0) {
      MaterialMapColor var1 = am[var0];
      return var1 != null ? var1 : a;
   }

   public static int b(int var0) {
      int var1 = var0 & 0xFF;
      return c(var1 >> 2).a(MaterialMapColor.a.b(var1 & 3));
   }

   public byte b(MaterialMapColor.a var0) {
      return (byte)(this.al << 2 | var0.e & 3);
   }

   public static enum a {
      a(0, 180),
      b(1, 220),
      c(2, 255),
      d(3, 135);

      private static final MaterialMapColor.a[] g = new MaterialMapColor.a[]{a, b, c, d};
      public final int e;
      public final int f;

      private a(int var2, int var3) {
         this.e = var2;
         this.f = var3;
      }

      public static MaterialMapColor.a a(int var0) {
         Preconditions.checkPositionIndex(var0, g.length, "brightness id");
         return b(var0);
      }

      static MaterialMapColor.a b(int var0) {
         return g[var0];
      }
   }
}
