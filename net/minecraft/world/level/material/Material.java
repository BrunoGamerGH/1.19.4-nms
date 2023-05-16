package net.minecraft.world.level.material;

public final class Material {
   public static final Material a = new Material.a(MaterialMapColor.a).c().i().b().e().h();
   public static final Material b = new Material.a(MaterialMapColor.a).c().i().b().e().h();
   public static final Material c = new Material.a(MaterialMapColor.a).c().i().b().g().h();
   public static final Material d = new Material.a(MaterialMapColor.d).c().i().b().d().h();
   public static final Material e = new Material.a(MaterialMapColor.h).c().i().b().f().h();
   public static final Material f = new Material.a(MaterialMapColor.m).c().i().b().f().h();
   public static final Material g = new Material.a(MaterialMapColor.h).c().i().b().f().e().d().h();
   public static final Material h = new Material.a(MaterialMapColor.h).c().i().b().f().e().h();
   public static final Material i = new Material.a(MaterialMapColor.m).c().i().b().f().e().h();
   public static final Material j = new Material.a(MaterialMapColor.m).c().i().b().f().e().a().h();
   public static final Material k = new Material.a(MaterialMapColor.m).c().i().b().f().e().a().h();
   public static final Material l = new Material.a(MaterialMapColor.e).c().i().b().f().e().a().h();
   public static final Material m = new Material.a(MaterialMapColor.i).c().i().b().f().e().h();
   public static final Material n = new Material.a(MaterialMapColor.a).c().i().b().f().e().h();
   public static final Material o = new Material.a(MaterialMapColor.a).c().i().b().f().h();
   public static final Material p = new Material.a(MaterialMapColor.d).c().i().f().h();
   public static final Material q = new Material.a(MaterialMapColor.D).h();
   public static final Material r = new Material.a(MaterialMapColor.a).h();
   public static final Material s = new Material.a(MaterialMapColor.j).h();
   public static final Material t = new Material.a(MaterialMapColor.k).h();
   public static final Material u = new Material.a(MaterialMapColor.b).h();
   public static final Material v = new Material.a(MaterialMapColor.f).h();
   public static final Material w = new Material.a(MaterialMapColor.c).h();
   public static final Material x = new Material.a(MaterialMapColor.s).h();
   public static final Material y = new Material.a(MaterialMapColor.y).h();
   public static final Material z = new Material.a(MaterialMapColor.n).d().h();
   public static final Material A = new Material.a(MaterialMapColor.n).h();
   public static final Material B = new Material.a(MaterialMapColor.n).d().f().c().h();
   public static final Material C = new Material.a(MaterialMapColor.n).d().f().h();
   public static final Material D = new Material.a(MaterialMapColor.d).d().h();
   public static final Material E = new Material.a(MaterialMapColor.e).d().i().h();
   public static final Material F = new Material.a(MaterialMapColor.h).d().i().f().h();
   public static final Material G = new Material.a(MaterialMapColor.a).i().h();
   public static final Material H = new Material.a(MaterialMapColor.f).i().h();
   public static final Material I = new Material.a(MaterialMapColor.h).i().f().h();
   public static final Material J = new Material.a(MaterialMapColor.l).h();
   public static final Material K = new Material.a(MaterialMapColor.g).h();
   public static final Material L = new Material.a(MaterialMapColor.i).h();
   public static final Material M = new Material.a(MaterialMapColor.g).g().h();
   public static final Material N = new Material.a(MaterialMapColor.a).g().h();
   public static final Material O = new Material.a(MaterialMapColor.l).g().h();
   public static final Material P = new Material.a(MaterialMapColor.h).f().h();
   public static final Material Q = new Material.a(MaterialMapColor.h).f().h();
   public static final Material R = new Material.a(MaterialMapColor.h).f().h();
   public static final Material S = new Material.a(MaterialMapColor.a).f().h();
   public static final Material T = new Material.a(MaterialMapColor.y).h();
   public static final Material U = new Material.a(MaterialMapColor.i).b().c().h();
   public static final Material V = new Material.a(MaterialMapColor.m).c().i().b().f().h();
   public static final Material W = new Material.a(MaterialMapColor.a).h();
   public static final Material X = new Material.a(MaterialMapColor.Y).f().h();
   private final MaterialMapColor Y;
   private final EnumPistonReaction Z;
   private final boolean aa;
   private final boolean ab;
   private final boolean ac;
   private final boolean ad;
   private final boolean ae;
   private final boolean af;

   public Material(MaterialMapColor var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, EnumPistonReaction var7) {
      this.Y = var0;
      this.ac = var1;
      this.af = var2;
      this.aa = var3;
      this.ad = var4;
      this.ab = var5;
      this.ae = var6;
      this.Z = var7;
   }

   public boolean a() {
      return this.ac;
   }

   public boolean b() {
      return this.af;
   }

   public boolean c() {
      return this.aa;
   }

   public boolean d() {
      return this.ab;
   }

   public boolean e() {
      return this.ae;
   }

   public boolean f() {
      return this.ad;
   }

   public EnumPistonReaction g() {
      return this.Z;
   }

   public MaterialMapColor h() {
      return this.Y;
   }

   public static class a {
      private EnumPistonReaction a;
      private boolean b;
      private boolean c;
      private boolean d;
      private boolean e;
      private boolean f;
      private final MaterialMapColor g;
      private boolean h;

      public a(MaterialMapColor var0) {
         this.a = EnumPistonReaction.a;
         this.b = true;
         this.f = true;
         this.h = true;
         this.g = var0;
      }

      public Material.a a() {
         this.d = true;
         return this;
      }

      public Material.a b() {
         this.f = false;
         return this;
      }

      public Material.a c() {
         this.b = false;
         return this;
      }

      Material.a i() {
         this.h = false;
         return this;
      }

      protected Material.a d() {
         this.c = true;
         return this;
      }

      public Material.a e() {
         this.e = true;
         return this;
      }

      protected Material.a f() {
         this.a = EnumPistonReaction.b;
         return this;
      }

      protected Material.a g() {
         this.a = EnumPistonReaction.c;
         return this;
      }

      public Material h() {
         return new Material(this.g, this.d, this.f, this.b, this.h, this.c, this.e, this.a);
      }
   }
}
